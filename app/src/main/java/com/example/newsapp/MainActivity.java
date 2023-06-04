package com.example.newsapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.adapter.ListViewAdapter;
import com.example.newsapp.domain.News;
import com.example.newsapp.util.NewsOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentDetail.MyListener {


    private ListView listview;
    private List<News> listitem;
    //定义newsOpenHelper，用于与数据库连接
    private NewsOpenHelper helper;

    private static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //从数据库获取资源
        //1.创建/打开数据库，数据库名为NewsTable.db
        helper = NewsOpenHelper.getInstance(this);
        helper.openReadLink();
        helper.openWriteLink();
        //加载SharedPreferences
        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        //加载数据库资源
        Resource();
        // 存在部分电脑可能存在无法检测到横屏竖屏切换，可在MainActivity的OnCreate方法中加上如下语句，放在setContentView语句前：
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        int ori = MainActivity.this.getResources().getConfiguration().orientation;
        /*
          横屏模式配置
         */
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(MainActivity.this, "现在是横屏", Toast.LENGTH_LONG).show();
            setContentView(R.layout.fragment_main);
            //列表页面需要显示的Fragment
            listview = findViewById(R.id.list_container);
            //把横屏信息加入到Adapter
            ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, R.layout.news_item, listitem, Configuration.ORIENTATION_LANDSCAPE);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener((parent, view, position, id) -> {
                FragmentDetail fragmentDetail = new FragmentDetail();
                Bundle bundle = new Bundle();
                bundle.putSerializable("news", listitem.get(position));
                fragmentDetail.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, fragmentDetail).commit();
            });
        }
        /*
          竖屏模式配置
         */
        else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(MainActivity.this, "现在是竖屏", Toast.LENGTH_LONG).show();
            setContentView(R.layout.acticity_main);

            listview = findViewById(R.id.list_view);
            ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, R.layout.news_item, listitem, Configuration.ORIENTATION_PORTRAIT);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, DetailActivity.class);
                    intent.putExtra("news", listitem.get(position));
                    startActivityForResult(intent, 1);
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //从数据库获取资源
        //1.创建/打开数据库，数据库名为NewsTable.db
        if (helper == null) {
            helper = NewsOpenHelper.getInstance(this);
            helper.openReadLink();
            helper.openWriteLink();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //关闭数据库连接
        helper.closeLink();
    }


    private void reload() {
        listitem = helper.queryAll();
    }

    //当通过startActivityForResult函数启动其他的安卓组件
    //该组件发生返回动作时，就会调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.v("reStart", "requestCode" + requestCode + "resultCode" + resultCode);
        //判断是否匹配成功
        //requestCoded的值与resultCode的值不需要相等，
        //但requestCoded的值需要与函数参数的int requestCode值相等
        //resultCode的值需要与函数参数的int resultCode值相等
        if (requestCode == resultCode) {
            if (intent.getBooleanExtra("isOverTime", false)) {
                Log.v("", "OverTime");
                Bundle extras = intent.getExtras();
                News newsBack = (News) extras.get("news");
                Toast.makeText(this, "已阅读新闻:" + newsBack.getTitle(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < listitem.size(); i++) {
                    System.out.println("循环" + i + "     id:" + listitem.get(i).getId() + "title:" + listitem.get(i).getTitle());
                    if (listitem.get(i).getId().equals(newsBack.getId())) {
                        listitem.remove(listitem.get(i));
                        //改颜色
                        int grey = getResources().getColor(R.color.md_blue_grey_100);
                        newsBack.setIsRead(grey);
                        listitem.add(newsBack);
                        //SharedPreferences保存已看过的新闻ID
                        //id  -->  颜色
                        String readId = sharedPreferences.getString("readId", "");
                        System.out.println("readId:" + readId);
                        HashSet<String> array = new HashSet<>();
                        if (!readId.equals("")) {
                            for (String s : readId.split(" ")) {
                                array.add(s);
                            }
                        }
                        array.add(String.valueOf(newsBack.getId()));
                        StringBuilder sb = new StringBuilder();
                        Iterator<String> iterator = array.iterator();
                        while (iterator.hasNext()) {
                            sb.append(iterator.next()).append(" ");
                        }
                        array.add(String.valueOf(newsBack.getId()));
                        String writeId = sb.toString();
                        System.out.println("writeId:" + writeId);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString("readId", writeId);
                        edit.commit();
                        break;
                    }
                }
                Collections.sort(listitem);
                ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, R.layout.news_item, listitem, Configuration.ORIENTATION_LANDSCAPE);
                listview.setAdapter(adapter);
            }
        }
//        reload();
    }

    //当fragment有新闻阅读时间超过30s，并切换了fragment时传回数据
    //该组件传回时，就会调用此函数改变颜色
    @Override
    public void sendData(News data) {
        Toast.makeText(this, "已阅读新闻:" + data.getTitle(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < listitem.size(); i++) {
            if (listitem.get(i).getId().equals(data.getId())) {
                listitem.remove(listitem.get(i));
                //改颜色
                int grey = getResources().getColor(R.color.md_blue_grey_100);
                data.setIsRead(grey);
                listitem.add(data);
                //SharedPreferences保存已看过的新闻ID
                //id  -->  颜色
                String readId = sharedPreferences.getString("readId", "");
                System.out.println("readId:" + readId);
                HashSet<String> array = new HashSet<>();
                if (!readId.equals("")) {
                    for (String s : readId.split(" ")) {
                        array.add(s);
                    }
                }
                array.add(String.valueOf(data.getId()));
                StringBuilder sb = new StringBuilder();
                Iterator<String> iterator = array.iterator();
                while (iterator.hasNext()) {
                    sb.append(iterator.next()).append(" ");
                }
                array.add(String.valueOf(data.getId()));
                String writeId = sb.toString();
                System.out.println("writeId:" + writeId);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("readId", writeId);
                edit.commit();
                break;
            }
        }
        Collections.sort(listitem);
    }


    public void Resource() {
        //如果数据库为空，则加载资源
        if (helper.queryAll().isEmpty()) {

            // 设置适配器的图片资源
            int[] imageId = new int[]{R.drawable.new1, R.drawable.black, R.drawable.hanjian};
            // 设置标题
            String[] title = new String[]{"关注永雏塔菲", "小黑子露出鸡脚了", "骂谁罕见啊？"};
            String[] context = new String[]{"taffy的名字“永雏塔菲”中的“永雏”来源于其王牌发明家的身份。扑克牌中“王牌”的英文是Ace[8]。将Ace以日语外来语方式表记时，以片假名写作「エイス」。再将「エイス」利用借字表示时，「エイ」可表示为「永」，「ス」可表示为「鶵」[注 2]。简体字即为“永雏”。所以姓氏是写作永雏读作Ace。[6]\n" +
                    "\n" +
                    "而名字中的“塔菲”是从Taffy音译而来，并无特殊含义。[6]\n" +
                    "\n" +
                    "taffy出生于1868年8月12日的威尔士，于1885年通过时光机穿越到现在。taffy自称17岁[注 3]，而且是永远的17岁（察觉），而不是154岁的老阿姨。[6]\n" +
                    "\n" +
                    "在taffy15岁时（1883年）[注 4]继承了侦探事务所。当时，夏洛克·福尔摩斯解决了“斑点案子的带件”[注 5]并声名大噪。taffy认为都是因为夏洛克·福尔摩斯的出现导致了侦探业的内卷，所以才接不到委托；因此将夏洛克·福尔摩斯视为死对头。虽然一直接不到委托，但是taffy称未来还是会等待着委托并继续经营着侦探事务所。taffy在接不到侦探委托、无所事事地情况下开始宅在事务所里搞发明，一不小心就简简单单成为了王牌发明家。[6]\n" +
                    "\n" +
                    "taffy称“说到最自傲的、最厉害的、最强的、最得意的发明果然还是时光机吧，也称航时机吧！”。一个人发明了时光机，一个人来到了现代，结果发现在这个时代福尔摩斯还是这么有名。[6]\n" +
                    "\n" +
                    "时光机可以载人（废话）。载重量>129.3㎏。规定为129.3㎏的原因是taffy想邂逅一只猫型机器人，而129.3㎏是猫型机器人的重量。[7]\n" +
                    "taffy会晕车，因此在时光机上看书的话也会晕。所以在使用时光机的时候什么都不能做。[7]\n" +
                    "时光机不会绕路，是直达的。但是会迟到，会晚36年。[7]\n" +
                    "taffy能够达到的最远的时间就是现在。[7]\n" +
                    "taffy还曾在本人不知道的情况下发明了一只机器猫耳娘[注 6]。提取了猫耳娘的DNA，将小猫改造成猫耳娘。taffy说到这里语焉不详，最后称“taffy现在还只会造人，不会造机器人。但是造人的话taffy可以（强调）。”[6]\n" +
                    "\n" +
                    "taffy的吉祥物小球球是毛绒玩具菲球。关于菲球的相关细节参见永雏塔菲#菲球章节。\n" +
                    "\n" +
                    "taffy的呆毛非常地硬、非常地坚挺、一柱擎天压不下去，不知道软下来是多长、是什么样子[6]。呆毛可以刺穿菲球并顶到菲球的下面放在头上[7]。\n" +
                    "\n" +
                    "taffy的身高大概是一米四八[注 7]（148cm），包含了呆毛的长度。但是不记得是否是穿着靴子测量的。[6]\n" +
                    "\n" +
                    "taffy的脚是奶酪味的。[7]\n" +
                    "\n" +
                    "taffy身上携带的化学试剂分别是炼铜药水、炼老婆药水和猫耳娘药水。taffy展示了试剂的超理学化学方程式并声称如果不能复现是因为现代的科技退步了，科研靠考古。[7]\n" +
                    "\n" +
                    "阅读更多：永雏塔菲（https://zh.moegirl.org.cn/%E6%B0%B8%E9%9B%8F%E5%A1%94%E8%8F%B2 ）\n" +
                    "本文引自萌娘百科(https://zh.moegirl.org.cn )，文字内容默认使用《知识共享 署名-非商业性使用-相同方式共享 3.0 中国大陆》协议。", "鸡你太美 baby 鸡你太美 baby\n" +
                    "\n" +
                    "鸡你实在是太美 baby 鸡你太美 baby\n" +
                    "\n" +
                    "迎面走来的你让我如此蠢蠢欲动\n" +
                    "\n" +
                    "这种感觉我从未有\n" +
                    "\n" +
                    "Cause I got a crush on you who you\n" +
                    "\n" +
                    "你是我的我是你的谁\n" +
                    "\n" +
                    "再多一眼看一眼就会爆炸\n" +
                    "\n" +
                    "再近一点靠近点快被融化\n" +
                    "\n" +
                    "想要把你占为己有baby bae\n" +
                    "\n" +
                    "不管走到哪里都会想起的人是你 you you\n" +
                    "\n" +
                    "我应该拿你怎样\n" +
                    "\n" +
                    "uh 所有人都在看着你\n" +
                    "\n" +
                    "我的心总是不安\n" +
                    "\n" +
                    "oh 我现在已病入膏肓\n" +
                    "\n" +
                    "eh eh 难道真的因为你而疯狂吗\n" +
                    "\n" +
                    "我本来不是这种人\n" +
                    "\n" +
                    "因你变成奇怪的人\n" +
                    "\n" +
                    "第一次呀变成这样的我\n" +
                    "\n" +
                    "不管我怎么去否认\n" +
                    "\n" +
                    "鸡你太美 baby 鸡你太美 baby\n" +
                    "\n" +
                    "鸡你实在是太美 baby 鸡你太美 baby\n" +
                    "\n" +
                    "oh eh oh 现在确认地告诉我\n" +
                    "\n" +
                    "oh eh oh 你到底属于谁\n" +
                    "\n" +
                    "oh eh oh 现在确认地告诉我\n" +
                    "\n" +
                    "oh eh oh 你到底属于谁 就是现在告诉我\n" +
                    "\n" +
                    "跟着这节奏 缓缓 make wave\n" +
                    "\n" +
                    "甜蜜的奶油 it\"s your birthday cake\n" +
                    "\n" +
                    "男人们的 game call me 你恋人\n" +
                    "\n" +
                    "别被欺骗愉快的 I wanna play\n" +
                    "\n" +
                    "我的脑海每分每秒只为你一人沉醉\n" +
                    "\n" +
                    "最迷人让我神魂颠倒是你身上香水\n" +
                    "\n" +
                    "oh right baby I\"m fall in love with you\n" +
                    "\n" +
                    "我的一切你都拿走只要有你就已足够\n" +
                    "\n" +
                    "我到底应该怎样\n" +
                    "\n" +
                    "uh 我心里一直很不安\n" +
                    "\n" +
                    "其他男人们的视线\n" +
                    "\n" +
                    "Oh 全都只看向你的脸\n" +
                    "\n" +
                    "Eh eh 难道真的因为你而疯狂吗\n" +
                    "\n" +
                    "我本来不是这种人\n" +
                    "\n" +
                    "因你变成奇怪的人\n" +
                    "\n" +
                    "第一次呀变成这样的我\n" +
                    "\n" +
                    "不管我怎么去否认\n" +
                    "\n" +
                    "鸡你太美 baby 鸡你太美 baby\n" +
                    "\n" +
                    "鸡你实在是太美 baby 鸡你太美 baby\n" +
                    "\n" +
                    "我愿意把我的全部都给你\n" +
                    "\n" +
                    "我每天在梦里都梦见你还有我闭着眼睛也能看到你\n" +
                    "\n" +
                    "现在开始我只准你看我\n" +
                    "\n" +
                    "I don’t wanna wake up in dream 我只想看你这是真心话\n" +
                    "\n" +
                    "鸡你太美 baby 鸡你太美 baby\n" +
                    "\n" +
                    "鸡你实在是太美 baby 鸡你太美 baby\n" +
                    "\n" +
                    "oh eh oh 现在确认的告诉我\n" +
                    "\n" +
                    "oh eh oh 你到底属于谁\n" +
                    "\n" +
                    "oh eh oh 现在确认的告诉我\n" +
                    "\n" +
                    "oh eh oh 你到底属于谁就是现在告诉我\n" +
                    "\n" +
                    "2人点赞\n" +
                    "随笔\n" +
                    "\n" +
                    "\n" +
                    "作者：a9f808df5f1a\n" +
                    "链接：https://www.jianshu.com/p/a3d480c4e0a7\n" +
                    "来源：简书\n" +
                    "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。", "一只有着人类的血脉的吸血鬼，不会吸血，甚至有点晕血。\n" +
                    "\n" +
                    "目前活跃在Bilibili和Youtube上，可熟练进行中、日、英三语直播。\n" +
                    "\n" +
                    "喜欢的颜色是蓝色和紫色。\n" +
                    "\n" +
                    "喜欢吃寿司，三文鱼和甜甜的东西，但不喜欢吃番茄（极度厌恶）。\n" +
                    "\n" +
                    "喜欢作词，睡觉，吃，洗澡和猫咪，讨厌受伤。\n" +
                    "\n" +
                    "喜欢看恐怖的东西，尽管自己不擅长应付。\n" +
                    "\n" +
                    "自称清楚、可爱、乖巧、文静的女孩子。\n" +
                    "\n" +
                    "家中有一只在地球被称为猫的使魔，名为ノル（男孩子）。\n" +
                    "\n" +
                    "头上的花是芍药花。\n" +
                    "\n" +
                    "阅读更多：东雪莲（https://zh.moegirl.org.cn/%E4%B8%9C%E9%9B%AA%E8%8E%B2 ）\n" +
                    "本文引自萌娘百科(https://zh.moegirl.org.cn )，文字内容默认使用《知识共享 署名-非商业性使用-相同方式共享 3.0 中国大陆》协议。"};

            String[] source = new String[]{"永雏塔菲", "坤哥", "東雪莲"};
            //时间
            String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            //黑色
            int blackColor = getResources().getColor(R.color.bg_black);
            // 将上述资源保存到表中
            for (int i = 0; i < title.length; i++) {
                //将数据插入到数据库对应的表中
                News news = new News(title[i], context[i], source[i], time, imageId[i], blackColor);
                if (helper.insert(news) > 0) {
                    Toast.makeText(this, "添加数据:" + news, Toast.LENGTH_SHORT).show();
                }
            }
        }
        // 查询所有
//        listitem = helper.queryAll();

        String readId = sharedPreferences.getString("readId", "");
        HashSet<String> array = new HashSet<>();
        if (!readId.equals("")) {
            for (String s : readId.split(" ")) {
                array.add(s);
            }
        }
        for (String s : array) {
            int grey = getResources().getColor(R.color.md_blue_grey_100);
            long id = Long.parseLong(s);
            News news = helper.queryById(id);
            news.setIsRead(grey);
            helper.updateById(news);
        }
        listitem = helper.queryAll();


    }


}
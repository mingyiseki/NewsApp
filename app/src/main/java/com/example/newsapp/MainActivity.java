package com.example.newsapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.adapter.ListViewAdapter;
import com.example.newsapp.domain.News;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ListView listview;
    private ArrayList<News> listitem;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_main);
//        ll_list1 = findViewById(R.id.ll_list1);
//        ll_list1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, DetailActivity.class);
//                startActivity(intent);
//            }
//        });


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
        String[] from = new String[]{"永雏塔菲", "坤哥", "東雪莲"};

        listitem = new ArrayList<>();

        // 将上述资源转化为list集合
        for (int i = 0; i < title.length; i++) {
            News news = new News();
            news.setId((long) i);
            news.setImage(imageId[i]);
            news.setTitle(title[i]);
            news.setContent(context[i]);
            news.setFrom(from[i]);
            news.setColor(getResources().getColor(R.color.bg_black));
            news.setTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            listitem.add(news);
        }
        listview = findViewById(R.id.list_view);
        ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, R.layout.news_item, listitem);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DetailActivity.class);
//                intent.putExtra("title", listitem.get(position).getTitle());
//                intent.putExtra("from",listitem.get(position).getFrom());
//                intent.putExtra("time",listitem.get(position).getTime());
//                intent.putExtra("content",listitem.get(position).getContent());
                intent.putExtra("news", listitem.get(position));
                startActivityForResult(intent, 1);
            }
        });
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
            Toast.makeText(this, "数据返还：" + "" + intent.getBooleanExtra("isOverTime", false), Toast.LENGTH_SHORT).show();
            if (intent.getBooleanExtra("isOverTime", false)) {
                Log.v("", "OverTime");
                Bundle extras = intent.getExtras();
                News newsBack = (News)extras.get("news");
                System.out.println(newsBack.getTitle());
                for (int i = 0; i < listitem.size(); i++) {
                    if (listitem.get(i).getId().equals(newsBack.getId())){
                        listitem.remove(listitem.get(i));
                        //改颜色
                        newsBack.setColor(getResources().getColor(R.color.md_blue_grey_100));
                        listitem.add(newsBack);
                    }
                }
                Collections.sort(listitem);
                ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, R.layout.news_item, listitem);
                listview.setAdapter(adapter);
                Log.v("", "good");
            }
        }
    }
}
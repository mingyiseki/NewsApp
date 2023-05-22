package com.example.newsapp;


import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements FragmentDetail.MyListener {


    private ListView listview;
    private ArrayList<News> listitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // 存在部分电脑可能存在无法检测到横屏竖屏切换，可在MainActivity的OnCreate方法中加上如下语句，放在setContentView语句前：
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        staticResource();
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
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, fragmentDetail)
                        .commit();
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
                    if (listitem.get(i).getId().equals(newsBack.getId())) {
                        listitem.remove(listitem.get(i));
                        //改颜色
                        newsBack.setColor(getResources().getColor(R.color.md_blue_grey_100));
                        listitem.add(newsBack);
                    }
                }
                Collections.sort(listitem);
                ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, R.layout.news_item, listitem, Configuration.ORIENTATION_LANDSCAPE);
                listview.setAdapter(adapter);
            }
        }
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
                data.setColor(getResources().getColor(R.color.md_blue_grey_100));
                listitem.add(data);
            }
        }
        Collections.sort(listitem);
    }

    public void staticResource() {
        // 设置适配器的图片资源
        int[] imageId = new int[]{R.drawable.new1, R.drawable.black, R.drawable.hanjian, R.drawable.sai,R.drawable.tkl};

        // 设置标题
        String[] title = new String[]{"关注永雏塔菲", "小黑子露出鸡脚了", "骂谁罕见啊？", "火遍全球的塞尔达，争当“弱智吧吧主”的网友给我笑傻了","阁下的《雪 distance》固然炸裂，但假如，我是说假如，我掏出一首《真没睡》阁下该如何应对呢"};
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
                "本文引自萌娘百科(https://zh.moegirl.org.cn )，文字内容默认使用《知识共享 署名-非商业性使用-相同方式共享 3.0 中国大陆》协议。",
                "百度弱智吧，一个弱智到让人思维打结的混沌之地。\n" +
                        "\n" +
                        "“我的电动车没电了，我就有个动车了。”\n" +
                        "\n" +
                        "“听说2012年是世界末日，那我们岂不是还有11年就完蛋了？”\n" +
                        "\n" +
                        "诸如此类的段子充满全吧，让你多看一眼，都觉得自己的智商在不停地-1-1-1。\n" +
                        "\n" +
                        "不过，如果你这几天都在 《塞尔达传说：王国之泪》里挑战物理法则的话，就不用担心智商问题了。\n" +
                        "\n" +
                        "你会发现：\n" +
                        "\n" +
                        "我不是来嘲笑这个吧的，我是来加入这个吧的。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "在身边稍加观察，你可能会发现，这几天里，同事上班的时候顶着黑眼圈精神不振，下了班后根本找不到人。\n" +
                        "\n" +
                        "无他，他们可能都去玩塞尔达了。\n" +
                        "\n" +
                        "游戏界有句话叫 “塞尔达是天”，说的就是塞尔达系列有多么吸引人。\n" +
                        "\n" +
                        "作为这一系列的正统续作，《王国之泪》受到了极大的关注，多少人早早地就请好了假，只为能在游戏解锁的第一时间进海拉鲁大陆畅游。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "《王国之泪》的前作《旷野之息》，讲述了一个勇士救公主的经典故事。\n" +
                        "\n" +
                        "虽然玩家们往往会抛下公主，不务正业地满大陆乱跑，但整活范围总体而言，还是和主线一样传统。\n" +
                        "\n" +
                        "比如砍砍树、造造冰，展开滑翔翼领略海拉鲁大陆风光。\n" +
                        "\n" +
                        "干过最过分的事情，可能就是在过场动画的时候穿上了最具个人审美的衣服，把勇士公主见面的唯美场面，变成了 “公主和她沉默的紫色兔头”。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "@吉尔莫德尔托罗的草稿\n" +
                        "\n" +
                        "但这一作里，任天堂加入了全新的拼装系统，允许玩家把木板、风扇、车轮等工具，随心所欲地组合成自己想要的样子。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "想过河，可以扎小木筏，也可以造大帆船。\n" +
                        "\n" +
                        "想坐车，可以拼最简单的四轮木板车，也可以组装一辆豪华玛莎拉蒂。\n" +
                        "\n" +
                        "简言之，只有你想不到的，没有游戏里造不出的。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "@卡计卡计 的豪华小车\n" +
                        "\n" +
                        "拿到这个能力，那些看着《艺术创想》长大的孩子们，立马就充分发挥自己的想象力与动手能力，发起了第一次塞尔达工业革命。\n" +
                        "\n" +
                        "把勇士救公主的中世纪传说，变成了充满科幻色彩的《赛博朋克2077》。\n" +
                        "\n" +
                        "一旦有了巨大的自由度，你就永远想不到玩家们能整出什么样的大活。\n" +
                        "\n" +
                        "在大部分人还拿着传统盾剑与怪物真刀实枪地血拼的时候，有些人已经迈入科技新纪元，让主角林克开起了高达。\n" +
                        "\n" +
                        "带火炮的那种。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "@SoulBanana蕉蕉\n" +
                        "\n" +
                        "足以让小怪见之奔逃，BOSS闻之色变。\n" +
                        "\n" +
                        "只希望这位朋友不要开着高达去见公主。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "也有朋友当场化身任天堂 斯蒂芬森，把风扇和小车拼成了火车。\n" +
                        "\n" +
                        "这小火车拖着风扇尾气缓缓上爬的时候，我都不知道我是在玩《塞尔达传说》，还是工业革命模拟器。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "@某某一米七\n" +
                        "\n" +
                        "也有人希望好上加好，快乐翻倍，在游戏里玩游戏。\n" +
                        "\n" +
                        "于是充分释放人类的游戏天性，建了一座王国之泪游乐园，里面有旋转秋千、大摆锤和海盗船。\n" +
                        "\n" +
                        "路过的林克看了都说晕。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "@失去梦想的大鸡腿_\n" +
                        "\n" +
                        "在任天堂官方发布的《王国之泪》开发人员访谈里，游戏总监藤林坦言，他们并不担心给玩家如此高的自由度，会使违背开发者意图的能力遭到滥用。\n" +
                        "\n" +
                        "相反，他们很期待玩家可以发展出意想不到的玩法。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "从以上几位大佬的作品可以看出来，制作组的愿望基本上实现了。\n" +
                        "\n" +
                        "玩家们充分发挥超高的技术力，把新能力玩得让人眼前一亮，或者眼前一黑。\n" +
                        "\n" +
                        "但是对更多人来说，在游戏里开高达只是看看就好。\n" +
                        "\n" +
                        "自己的归宿，可能还是弱智吧吧主。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "俗话说的好，学好数理化，走遍天下都不怕。\n" +
                        "\n" +
                        "但是拿着Switch在海拉鲁大陆畅游的玩家们，可能早就过了在物理课上画受力分图析的年纪。\n" +
                        "\n" +
                        "连抛物线都算不出来的玩家，带着自己瘸腿的数理化知识， 玩的可能不是《王国之泪》，而是中学物理课没好好听讲的悔恨之泪。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "前文那位造游乐园的朋友就表示，为了让林克转起来，他失败了n次，建造的时候不仅需要考虑受力，还要兼顾材料的密度和硬度。\n" +
                        "\n" +
                        "这对普通玩家而言还是太过极限流，对大众最友好的工具应该是朴实无华的 木板。\n" +
                        "\n" +
                        "但就是这块基础木板，也能让接下来发生的每一件事，都在自信满满的玩家意料之外。\n" +
                        "\n" +
                        "你面前有一段峡谷，你手边有两块木板，你的能力能把木板拼起来。\n" +
                        "\n" +
                        "听起来解法是不是呼之欲出？那就让我们来造桥吧！\n" +
                        "\n" +
                        "造伸向云端的桥，造不受想象力拘束的桥，造奔向大地的桥！\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "《跷跷板》 @Luna啦啦啦\n" +
                        "\n" +
                        "天上的重力太复杂，把握不住很正常。\n" +
                        "\n" +
                        "入门可以先脚踏实地，在陆地上造车。\n" +
                        "\n" +
                        "最基础的四轮车， 需要把四个轮子装在木板的四角。\n" +
                        "\n" +
                        "非常简单地就可以让小车跑起来，绝对不可能翻车。\n" +
                        "\n" +
                        "除非你把轮子粘死在了木板下面。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "《有形无神》 @草莓炖章鱼很好吃的\n" +
                        "\n" +
                        "实在不行，游戏里不是有风扇吗？\n" +
                        "\n" +
                        "木工结构解决不了的事情，加点外置动力，总能让自己跑起来吧。\n" +
                        "\n" +
                        "吧……？\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "《受力平衡》@AAA海拉鲁大师之剑批发商\n" +
                        "\n" +
                        "温馨提醒，装风扇的时候注意风扇朝向。\n" +
                        "\n" +
                        "建议这位朋友检查一下自家厨房的排气扇是不是装反了（不是）\n" +
                        "\n" +
                        "没关系，风扇要注意朝向，但是马不用。\n" +
                        "\n" +
                        "作为海拉鲁大陆的勇士，可以不会造车，但一定得会骑马。\n" +
                        "\n" +
                        "……但为了马的安全，还是建议大家好好学习造车。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "《直击码马头》 @HARUxWAxAGEPOYO\n" +
                        "\n" +
                        "虽然很残酷，但建了无数离谱的弱智载具之后，玩家们可能会发现， 让小车正常地动起来，可能只是勇者远征的第一步。\n" +
                        "\n" +
                        "在真实的物理引擎加持下，周围的任何一点风吹草动，都可能让一整天的努力前功尽弃。\n" +
                        "\n" +
                        "当把车轮正确地按在轴上后，小车就有了自己的想法。\n" +
                        "\n" +
                        "三角形是最稳定的结构，三个轮子也足够让小车脱离主人束缚远走高飞。\n" +
                        "\n" +
                        "祝小车的未来自由幸福。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "《你的轮子跑得比你快》@哈怪\n" +
                        "\n" +
                        "除了斜坡，另一个事故高发地是湖泊。\n" +
                        "\n" +
                        "甭管是木筏还是小船，风力驱动还是风扇驱动，只要下了水，它们都可能一个不注意飘悠悠到湖心。\n" +
                        "\n" +
                        "再不上船是真的来不及了。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "《再见了妈妈今晚我就要远航》@䟩烈\n" +
                        "\n" +
                        "在基础的水火木元素循环里，我们可以知道，木头可以浮在水上，水可以灭火， 火可以点燃木头。\n" +
                        "\n" +
                        "但木头与火的燃烧反应，已经超出了物理学范畴，需要回中学去找化学老师。\n" +
                        "\n" +
                        "多少人在造车的时候千算万算，好不容易算明白了力学结构，但没逃开化学的秘火暗算。\n" +
                        "\n" +
                        "还没走两步，坦克就成灰了。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "《内 燃 机》@晔烛Eterndle\n" +
                        "\n" +
                        "花一天一夜做的载具一不小心没了固然很惨，但起码没有对玩家造成什么实质性的伤害。\n" +
                        "\n" +
                        "藤林总监信誓旦旦地表示“期待玩家可以发展出意想不到的玩法”的时候，肯定对自家游戏的流程有足够的自信。\n" +
                        "\n" +
                        "自信无论玩家整什么大活，都不影响他们正常过关。\n" +
                        "\n" +
                        "只可惜，千算万算，算不过玩家的脑洞与游戏的物理引擎。\n" +
                        "\n" +
                        "有网友做了辆四驱小车帮自己淌过岩浆池子。\n" +
                        "\n" +
                        "车没有走不动道，没有远走高飞，没有中途被烧，一切都很完美。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "@zekaio\n" +
                        "\n" +
                        "但是当他把车从岩浆里拖出来的时候，发现小车直直地往他的方向冲了过来。\n" +
                        "\n" +
                        "前面，是用料扎实的四驱小车。\n" +
                        "\n" +
                        "后面，是灼热滚烫的岩浆。\n" +
                        "\n" +
                        "进退两难之际，他成功地被小车撞进了岩浆池。\n" +
                        "\n" +
                        "当他复活的时候发现，前面 还是小车，后面 还是岩浆。\n" +
                        "\n" +
                        "再复活的时候，还是如此。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "《任天堂：啊？》@zekaio\n" +
                        "\n" +
                        "自信满满的藤林总监，大概没想到有人真的会把自己玩进死亡的无限循环。\n" +
                        "\n" +
                        "要不这车还是散架了吧。\n" +
                        "\n" +
                        "除了环境问题，打败自己的往往可能是自己。\n" +
                        "\n" +
                        "在车修好之后，小心手上的任何一把武器。\n" +
                        "\n" +
                        "已知条件：风扇需要击打一下才能开启。\n" +
                        "\n" +
                        "木板受到击打会碎。\n" +
                        "\n" +
                        "木板和风扇可以组合。\n" +
                        "\n" +
                        "得出结论：你把你车打碎了。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "《欧·亨利式结尾》@初夏ChuXXia\n" +
                        "\n" +
                        "你问我为什么不笑？\n" +
                        "\n" +
                        "有的人在看乐子，有的人在照镜子。\n" +
                        "\n" +
                        "在网上看了半天的弱智吧精选，下班回家打开游戏才发现:\n" +
                        "\n" +
                        "吧主竟是我自己。\n" +
                        "\n" +
                        "看别人一个不留神放生木筏，自己玩的时候特地注意了先上船再开动力。\n" +
                        "\n" +
                        "结果却没算到自己手上是把斧子，木筏成功变成了木材。\n" +
                        "\n" +
                        "诸如此类的意外，防不胜防，数不胜数。\n" +
                        "\n" +
                        "更恐怖的是，弱智吧里不一定是真弱智， 但每一个把木板砸了烧了放生了的苦逼玩家，都是认真的。\n" +
                        "\n" +
                        "那些苦哈哈地追着小车跑的人，在建造的时候，脑子里一定有一张宏伟的蓝图。\n" +
                        "\n" +
                        "手边只有木板，心中却已在畅想开着坦克一路轰炸到公主面前，对公主骄傲地说出：“大人，时代变了。”\n" +
                        "\n" +
                        "最后却发现，自己的物理学知识并不足以支撑如此精密的工程，只想穿越回中学时代，摇醒曾经那个在物理课上睡大觉的自己。\n" +
                        "\n" +
                        "笑不出来，只感觉一般路过的林克被狠狠踢了一脚。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "无所谓，在被自己弱智晕的时候，可以去看看那些买卡带的玩家。\n" +
                        "\n" +
                        "别人都把高达造出来了，他们的游戏可能还没发货。\n" +
                        "\n" +
                        "等他们拿到游戏的时候，可能连弱智吧吧务都已经满员了。\n" +
                        "\n" +
                        "这样想一想，就会觉得自己还有机会在这里竞选吧主，简直是太酷啦！\n" +
                        "\n" +
                        "所以，与其纠结尔辈能不能究物理，不如看看别人的弱智行为大赏吧！",
                "作者：d中d\n" +
                        "链接：https://www.zhihu.com/question/354917087/answer/2856659193\n" +
                        "来源：知乎\n" +
                        "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。\n" +
                        "\n" +
                        "这歌应该有好几年了吧，没记错的话是疫情前一段时间出的歌，当时有听过几次这首歌，但是也没啥特别的热度吧，最近随着有人玩浪漫主义的梗之后 被人挖出来一起玩了。首先聊聊伴奏。就一首说唱音乐而言，伴奏其实很不错，旋律还挺抓耳，第二段副歌部分换配器，以及从808鼓进入比较欢快的类似舞曲的鼓点，总体而言（？或许）是一个偏商业风格的编曲。旋律上使用比较诙谐的音色，挺符合这首歌的主题风格。再聊下作词。其实这次玩梗很大一部分原因出在词上，有一说一，这词用一个字形容，确实是：俗。从思想上背离核心价值观的倾向，到内容上非常实在的口语话的铺叙，再到韵脚上有些滥用语气词的情况，很难说 ，这首《真没睡》是一首能登上大雅之堂的玩意儿。甚至在脏词频出的说唱音乐中，有种一句脏字不说却能脏了我的眼睛的感觉。但话说回来吧，这歌词水平也只是到俗的水平上了，这首歌词至少比“当我被束缚在”之类的要好很多的。姜云升写的词能够给听众一个画面感，第一遍听完，一个渣男形象就自然映入眼帘，这也是姜云升许多作品的共同特点，即用平白的词让你获得画面感。这或许也是他饱受争议的很多原因吧，有人觉得他俗，依托答辩，但也有人被他这些词给感动到，因此共情。就我而言，这首歌的词虽然俗，但是他有一种幽默感，词藻上重复地“狡辩”以及各种哄人技巧，真的让人在听的时候会会心一笑（或许我是渣男？），总体上是一个挺有意思的感觉，重复听确实不现实，但是图一乐还是可以的。至于flow之类的，我觉得不分析了，毕竟本身就不是炫技哥，算是比较平庸的一类吧。还是想聊一下姜云升的，自新世代后，和阿达娃（？不确定，记不清了）合作了一首《起点》之后，做了首《浪漫主义》，听的出来，他也在寻求突破，到现在一首乐堡啤酒的商业歌之后算是有所突破了，在音色本身并不讨喜的情况下，又抛弃原本已经深入人心的唱法去寻求与之前截然不同的声线，是一个很勇敢的决定。转变的阵痛加上综艺的爆红以及女粉丝的过度追捧，导致姜云升处于一种“德不配位”的地方，处于饭圈与嘻哈的交界处，也是挺难受的，也许也是姜云升自己不愿见到的。大家玩梗可以，笑笑就好了，也不要做太过格的事情，这首歌也没有到“答辩”的地步，还是要从多方面，以一个比较宽容的态度去看待。优雅大气是艺术家们共同的追求，雅俗共赏更是难如登天，但“俗”到“超凡脱俗”，也是一种别样的境界。"+"作者：电子音乐资讯Official\n" +
                        "链接：https://www.zhihu.com/question/354917087/answer/2802302701\n" +
                        "来源：知乎\n" +
                        "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。\n" +
                        "\n" +
                        "1.歌放在嘻哈里真的很拉很拉；2.唱的情歌放在流行乐里也很拉很拉；3.经常乱说话；4.最重要的一点，他的nc粉太恶毒了，很多人都是报复他的nc粉而已；5.无脑破坏演出市场；6.无脑破坏音乐市场；7.没有任何梦想，为了恰饭乱做流行歌、情歌就算了，居然还说自己现在做的东西才是摆脱资本、摆脱贫穷、不是为了热度而做、不是为了赚钱而做、是自己做大做强了后自己真正想做的东西？？？（不是原话，但是他的声明就是这个意思，大家觉得他在恰烂钱，他不仅不承认还要自洽说这是他的real，这么说起来反倒做嘻哈才是他恰烂钱的时候，虽然也很难听）；8.盗beat是中国rappers兵家常事，但是盗来之后水平比国内外每一个都要低，别人是同行映衬，他是映衬同行；9.极其自大，但并不是那种“有能力所以自大”，而是空无地自大。他的粉丝更加自大，也是这种令人讨厌的空无的、不存在的、自认为比别人更强的自大；10.渣男直播；11.渣男歌曲；12.任何一次洗白，公关都做得稀烂！！！；13.某些资本歌词非常恶心人；14.长得不仅丑，而且丑到让人不舒服，是那种外星人折磨人的丑。这也就算了，他的粉丝居然觉得他无敌帅……………………这就把一个小缺点变成了巨大的惊人事件；15.网上还有很多关于人品极差的事件，不知道真假，先不判断，我说的东西不是“人品”，是讨人嫌，这是两码事。讨人嫌和做人好坏不一样；16.演出水平很次；17.抄袭都抄不好；18.唱歌和说唱很难听就算了，说话也很难听"
        };
        String[] from = new String[]{"永雏塔菲", "坤哥", "東雪莲", "林克","吊毛"};

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
    }


}
package com.example.newsapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.newsapp.domain.News;
import com.example.newsapp.util.TimeUtil;

import java.util.Date;

public class FragmentDetail extends Fragment {
    private News news;
    private Date beginTime;
    private MyListener listener;

    public interface MyListener{
        void sendData(News data);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView title = view.findViewById(R.id.tv_fragment_detail_title);
        TextView from = view.findViewById(R.id.tv_fragment_detail_from);
        TextView time = view.findViewById(R.id.tv_fragment_detail_time);
        TextView content = view.findViewById(R.id.tv_fragment_detail_content);
        // 获取Serializable对象
        Bundle bundle = getArguments();
        if (bundle != null) {
            news = (News) bundle.getSerializable("news");
            title.setText(news.getTitle());
            from.setText(news.getSource());
            time.setText(news.getTime());
            content.setText(news.getContent());
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("", "start");
        beginTime = new Date();


//        Log.v("发送", "" + isOverTime + second);
    }

    @Override
    public void onPause() {
        super.onPause();
        boolean isOverTime = new TimeUtil().isOverTime(beginTime, 30);
        //如果超时，就把news传回去
        if (isOverTime){
            sendDataToActivity(news);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MyListener) {
            listener = (MyListener) context;
        }
    }
    
//    private void onDataChange(News data) {
//        if (listener != null) {
//            listener.sendData(data);
//        }
//    }
    
    private void sendDataToActivity(News data) {
        if (getActivity() instanceof MyListener) {
            ((MyListener) getActivity()).sendData(data);
        }
    }

}
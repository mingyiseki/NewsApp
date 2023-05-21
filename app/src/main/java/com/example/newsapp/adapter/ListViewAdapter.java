package com.example.newsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.domain.News;
import com.example.newsapp.R;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<News> {

    private Context context;
    private int resourceId;
    private List<News> listitem;

    public ListViewAdapter(Context context, int resourceId, List<News> listitem) {
        super(context, resourceId, listitem);
        this.context = context;
        this.resourceId = resourceId;
        this.listitem = listitem;
    }

    @Override
    public int getCount() {
        return listitem.size();
    }

    @Override
    public News getItem(int position) {
        return listitem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resourceId, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.iv_list1);
        TextView title = convertView.findViewById(R.id.tv_list1_title);
        TextView content = convertView.findViewById(R.id.tv_list1_content);
        TextView from = convertView.findViewById(R.id.tv_list1_from);
        TextView time = convertView.findViewById(R.id.tv_list1_time);
        News news = listitem.get(position);
        imageView.setImageResource(news.getImage());
        title.setText(news.getTitle());
        content.setText(news.getContent());
        from.setText(news.getFrom());
        time.setText(news.getTime());
        return convertView;
    }
}

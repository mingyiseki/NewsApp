package com.example.newsapp.domain;

import java.io.Serializable;

public class News implements Serializable,Comparable{
    private Long id;
    private String title;
    private String content;
    private String source;
    private String time;
    private int image;
    private int isRead;

    public News(String title, String content, String source, String time, int image, int isRead) {
        this.title = title;
        this.content = content;
        this.source = source;
        this.time = time;
        this.image = image;
        this.isRead = isRead;
    }

    public News() {
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int compareTo(Object o) {
        return (int) (this.id-((News)o).getId());
    }

    @Override
    public String toString() {
        return "新闻详情{\n" +
                "新闻id=" + id +
                ", \n新闻标题='" + title + '\'' +
                ", \n新闻内容='" + content + '\'' +
                ", \n新闻来源='" + source + '\'' +
                ", \n创建时间='" + time + '\'' +
                ", \n图片来源=" + image +
                ", \n是否已读=" + isRead +
                "\n}";
    }
}

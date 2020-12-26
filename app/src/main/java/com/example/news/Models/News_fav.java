package com.example.news.Models;

public class News_fav {
    private String user_id;
    private String news_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNew_id() {
        return news_id;
    }

    public void setNew_id(String new_id) {
        this.news_id = new_id;
    }

    public News_fav(String user_id, String new_id) {
        this.user_id = user_id;
        this.news_id = new_id;
    }
    public News_fav() {

    }
}

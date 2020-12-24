package com.example.news.Models;

public class News {
    private String title;
    private String imageUrl;
    private String pubDate;
    private String onSiteLink;

    public News(String title, String imageUrl, String pubDate, String onSiteLink) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.pubDate = pubDate;
        this.onSiteLink = onSiteLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getOnSiteLink() {
        return onSiteLink;
    }

    public void setOnSiteLink(String onSiteLink) {
        this.onSiteLink = onSiteLink;
    }
}

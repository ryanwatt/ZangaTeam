package com.zangateam.ryanwatt.zangateam;

/**
 * Created by nickzarate on 6/16/16.
 */
public class Event {
    String description;
    String date;
    String title;
    String imageUrl;

    public String getDescription() {
        return description;
    }
    public String getDate() {
        return date;
    }
    public String getTitle() {
        return title;
    }
    public String getImageUrl() { return imageUrl; }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}

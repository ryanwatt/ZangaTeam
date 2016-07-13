package com.zangateam.ryanwatt.zangateam;

/**
 * The Event class defines an event, including the description, time it occurs, and the title.
 *
 * @author  Nick Zarate
 * @version 1.0
 * @since   2016-06-29
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

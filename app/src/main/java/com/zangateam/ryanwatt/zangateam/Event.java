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
    String time;
    String title;
    // Insert photo here

    public String getDescription() {
        return description;
    }
    public String getTime() {
        return time;
    }
    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}

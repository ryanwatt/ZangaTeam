package com.zangateam.ryanwatt.zangateam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nickzarate on 6/16/16.
 */
public class Filter {
    List<String> categories = new ArrayList<String>();
    String date;
    ArrayList<String> keywords;

    public Filter() {
        categories.add("http://calendar.byui.edu/RSSFeeds.aspx?data=tq9cbc8b%2btuQeZGvCTEMSP%2bfv3SYIrjQ3VTAXA335bE0WtJCqYU4mp9MMtuSlz6MRZ4LbMUU%2fO4%3d");
        date = "";
    }

    public List<String> getCategories() {
        return categories;
    }
}

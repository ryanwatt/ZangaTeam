package com.zangateam.ryanwatt.zangateam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by nickzarate on 6/15/16.
 */
public class Feed extends AsyncTask<Void, Void, Void> {
    Context context;
    ProgressDialog progressDialog;
    URL url;
    Filter filter;
    List<Event> events = new ArrayList<Event>();
    ArrayAdapter<Event> adapter;
    Bundle bundle;

    public Feed(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        filter = new Filter();
        ArrayList<String> keywords = new ArrayList<String>();
        bundle.putString("category", "All");
        bundle.putStringArrayList("keywords", keywords);
        bundle.putString("startDate", "");
        bundle.putString("endDate", "");
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        populateListView();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String category = getCategoryUrl("All");//bundle.getString("category"));
        getEvents(getData(category));
//        List<String> addresses = filter.getCategories();
//        for (String address : addresses) {
//            getEvents(getData(address));
//        }
        return null;
    }

    /**
     * The getEvents method takes data (in xml format) and parses through the file, populating the
     * List of Events.
     *
     * @param data An XML document (corresponds to an RSS Feed)
     * @return void Nothing is returned, only the List of Events is populated.
     * @author Raleigh Wayland
     * @version 1.0
     * @since 2016-06-29
     */
    private void getEvents(Document data) {
        if (data != null) {
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(0);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node currentChild = items.item(i);
                if (currentChild.getNodeName().equalsIgnoreCase("item")) {
                    NodeList itemChildren = currentChild.getChildNodes();
                    Event event = new Event();

                    // Add the event only if these two booleans are true when the event is done
                    // being parsed.
                    boolean inDateRange = true;
                    boolean hasKeywords = false;
                    List<String> keywords = bundle.getStringArrayList("keywords");
                    if (keywords.size() == 0) {
                        hasKeywords = true;
                    }

                    // Create an empty words array ["0","0","0"] that corresponds to which
                    // keywords are found in the event. ["1","1","1"] means that all keywords
                    // are found.
                    List<String> words = new ArrayList<String>(keywords.size());
                    for (String val: keywords) words.add(new String(val));
                    for (int l = 0; l < words.size(); l++) {
                        words.set(l, "0");
                    }

                    for (int j = 0; j < itemChildren.getLength(); j++) {
                        Node current = itemChildren.item(j);
                        switch (current.getNodeName()) {
                            case "title":
                                for (String val: getWordList(current.getTextContent())) {
                                    for (int k = 0; k < keywords.size(); k++) {
                                        if (val.equals(keywords.get(k))) {
                                            words.set(k, "1");
                                        }
                                    }
                                }
                                event.setTitle(current.getTextContent());
                                break;
                            case "pubDate":
                                Calendar start = new GregorianCalendar();
                                Calendar end = new GregorianCalendar();
                                Calendar eventDate = new GregorianCalendar();
                                String startDate = bundle.getString("startDate");
                                String endDate = bundle.getString("endDate");
                                if (startDate.length() != 0) {
                                    int startMonth = Integer.parseInt(startDate.substring(0, 2));
                                    int startDay = Integer.parseInt(startDate.substring(3, 5));
                                    int startYear = Integer.parseInt(startDate.substring(6, 10));
                                    start.set(startYear, startMonth, startDay);
                                }
                                if (endDate.length() != 0) {
                                    int endMonth = Integer.parseInt(endDate.substring(0, 2));
                                    int endDay = Integer.parseInt(endDate.substring(3, 5));
                                    int endYear = Integer.parseInt(endDate.substring(6, 10));
                                    end.set(endYear, endMonth, endDay);
                                }

                                // Retrieve the day, month, year, and time of the event, and compare
                                // it to the time filter set by the user.
                                int day = Integer.parseInt(current.getTextContent().substring(5, 7));
                                int month = getMonthNumber(current.getTextContent().substring(8, 11));
                                int year = Integer.parseInt(current.getTextContent().substring(12, 16));
                                eventDate.set(year, month, day);
                                if ((startDate.length() != 0) && eventDate.before(start) ||
                                    (endDate.length() != 0) && eventDate.after(end))
                                {
                                    inDateRange = false;
                                    event.setTime(current.getTextContent());
                                }
                                break;
                            case "description":
                                for (String val : getWordList(current.getTextContent())) {
                                    for (int k = 0; k < keywords.size(); k++) {
                                        if (val.equals(keywords.get(k))) {
                                            words.set(k, "1");
                                        }
                                    }
                                }
                                event.setDescription(current.getTextContent());
                                break;
                        }
                    }
                    for (String val : words) {
                        if (val.equals("0")) {
                            hasKeywords = false;
                            break;
                        }
                        hasKeywords = true;
                    }
                    if (inDateRange && hasKeywords) {
                        events.add(event);
                    }
                }
            }
        }
        Log.d("TIME", "Finished parsing");
    }

    public Document getData(String address) {
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            return builder.parse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ListView populateListView() {
        View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        ListView listView = (ListView) rootView.findViewById(R.id.eventsList);

        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, events);
        listView.setAdapter(adapter);

        return listView;
    }

    public void setBundle(Bundle bundle) { this.bundle = bundle; }

    private List<String> getWordList(String string) {
        List<String> wordList = new ArrayList<String>();
        Matcher m = Pattern.compile("\\w{2,}")
                .matcher(string);
        while (m.find()) {
            wordList.add(m.group());
        }
        return wordList;
    }

    private String getCategoryUrl(String category) {
        String categoryUrl = "";
        switch (category) {
            case "Highlighted Events":
                categoryUrl = "http://calendar.byui.edu/RSSFeeds.aspx?data=hhAbVFpDFO7OxcTcYlM9Lk3inbX%2bJ%2baS3fubTE468TQI91kccS33vQ%3d%3d";
                break;
            case "Outdoor":
                categoryUrl = "http://calendar.byui.edu/RSSFeeds.aspx?data=Sgu4uQga6RfnSddKSZEdcHnrzRUjyU%2fMG2RV2DVd%2bbqv9%2fKARdEWgU4bpJLKgS96";
                break;
            case "Service":
                categoryUrl = "http://calendar.byui.edu/RSSFeeds.aspx?data=3hNFdwTLP7rNUiDZ9%2bYQbVIL4HrLzfuO2wc8XH%2bQUA7gCEcgw%2f%2bYJHzzU2SDzmAt";
                break;
            case "Social":
                categoryUrl = "http://calendar.byui.edu/RSSFeeds.aspx?data=htZIHfK1USmZM7EsUv8DxnyxFHM7hAJ3hAb6CJIOFG5hMfL%2fpbsqgXImWzD%2fGPk5";
                break;
            case "Sports":
                categoryUrl = "http://calendar.byui.edu/RSSFeeds.aspx?data=VjPAItjJsNprMFBCLNIyskvl5zivBGWKlHT5tTKuv9iS4mPdWINwckEm7JUuavIx";
                break;
            case "Talent":
                categoryUrl = "http://calendar.byui.edu/RSSFeeds.aspx?data=ZUr91HJbu8Ug4jSwsMCnWxydy0dS7XZQxR1Obnn0t0ekC2ZON2qgt2iGjf%2bxzloW";
                break;
            case "Wellness":
                categoryUrl = "http://calendar.byui.edu/RSSFeeds.aspx?data=kX%2b9GIsuaaLQBQxDPomBvGeGq%2feQxcNDKSvLot9L6CTvV7oVwsIseJkwoJq97TYw";
                break;
            case "Concerts":
                categoryUrl = "http://calendar.byui.edu/RSSFeeds.aspx?data=6N%2bM7u2dU1sLnA7F%2fIU6T7cdeKcTSrVEhd4ewWzOpYQ%3d";
                break;
            case "Music":
                categoryUrl = "http://calendar.byui.edu/RSSFeeds.aspx?data=LfzyaB9%2fCNYqE2NdD3FEzkqQ7jLjzKXfvjW9VSXvVkWTF3fTKiRZhQ%3d%3d";
                break;
            case "Theatre":
                categoryUrl = "http://calendar.byui.edu/RSSFeeds.aspx?data=sHXLSzmZxe8ymoAaTit4rQNQiPoGLRs0Hod%2fs8v8Rd0AT54azjRzHg%3d%3d";
                break;
            case "Devotional":
                categoryUrl = "http://calendar.byui.edu/RSSFeeds.aspx?data=3fdei69th52sh0U8OON0aSSRcC%2b6jf%2fPw5KcZ5V1ln0%3d";
                break;
            case "Get Connected":
                categoryUrl = "http://calendar.byui.edu/RSSFeeds.aspx?data=P5WRNEbs5SVzVdy3sp3sN82f409sYF1jXkTaNUYbPLXVWhFtl2glzQ%3d%3d";
                break;
            case "Graduation":
                categoryUrl = "http://calendar.byui.edu/RSSFeeds.aspx?data=qtalE2hWSqcsLfNfNoFG2ljCMgnURffnYP1yIHcEiV8%3d";
                break;
            default:
                categoryUrl = "http://calendar.byui.edu/RSSFeeds.aspx?data=tq9cbc8b%2btuQeZGvCTEMSP%2bfv3SYIrjQ3VTAXA335bE0WtJCqYU4mp9MMtuSlz6MRZ4LbMUU%2fO4%3d";
        }
        return categoryUrl;
    }

    private int getMonthNumber(String month) {
        switch (month) {
            case "Jan":
                return 1;
            case "Feb":
                return 2;
            case "Mar":
                return 3;
            case "Apr":
                return 4;
            case "May":
                return 5;
            case "Jun":
                return 6;
            case "Jul":
                return 7;
            case "Aug":
                return 8;
            case "Sep":
                return 9;
            case "Oct":
                return 10;
            case "Nov":
                return 11;
            case "Dec":
                return 12;
            default:
                return 7;
        }
    }
}

package com.zangateam.ryanwatt.zangateam;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Process;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpConnection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public Feed(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        filter = new Filter();
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
        List<String> addresses = filter.getCategories();
        for (String address : addresses) {
            getEvents(getData(address));
        }
        return null;
    }

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
                    for (int j = 0; j < itemChildren.getLength(); j++) {
                        Node current = itemChildren.item(j);
                        switch (current.getNodeName()) {
                            case "title":
                                event.setTitle(current.getTextContent());
                                break;
                            case "pubDate":
                                event.setTime(current.getTextContent());
                                break;
                            case "description":
                                event.setDescription(current.getTextContent());
                                break;
                        }
                    }
                    events.add(event);
                }
            }
        }
    }

    public Document getData(String address) {
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void populateListView() {
        for (Event event : events) {
            Log.d("title", event.getTitle());
            Log.d("time", event.getTime());
            Log.d("description", event.getDescription());
        }
    }
}
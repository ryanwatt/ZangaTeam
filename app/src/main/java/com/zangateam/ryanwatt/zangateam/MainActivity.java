package com.zangateam.ryanwatt.zangateam;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Feed feed;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Feed feed = new Feed(this);
        feed.execute();
    }

    public void loadEvents(View view) {

        List<Event> events = feed.getEvents();

        ListView listView = (ListView) findViewById(R.id.eventsList);

        List<String> titleList = new ArrayList<>();


        for (Event event : events) {
            titleList.add(event.getTitle());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titleList);
        listView.setAdapter(adapter);
    }
}

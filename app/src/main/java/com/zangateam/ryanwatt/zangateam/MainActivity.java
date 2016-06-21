package com.zangateam.ryanwatt.zangateam;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Feed feed;
    Context context;
    ArrayAdapter<Event> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Feed feed = new Feed(this);
        feed.execute();

        List<Event> events = feed.getEvents();
        ListView listView = (ListView) findViewById(R.id.eventsList);
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, events);
        listView.setAdapter(adapter);
    }
}

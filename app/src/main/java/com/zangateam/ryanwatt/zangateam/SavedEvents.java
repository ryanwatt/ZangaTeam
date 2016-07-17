package com.zangateam.ryanwatt.zangateam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SavedEvents extends AppCompatActivity {
    Feed feed;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);

//        feed = new Feed(this);
//        feed.execute();
//
//        listView = feed.populateListView();
        List<Event> events = new ArrayList<>();
        Event firstEvent = new Event();
        firstEvent.setTitle("Programming Late At Night");
        firstEvent.setTime("July 14 2016, Tonight...");
        firstEvent.setDescription("This event was initiated by Nick Zarate. He's crazy enough to think we can pull this app off.");

        Event secondEvent = new Event();
        secondEvent.setTitle("Getting Tired Programming");
        secondEvent.setTime("July 14 2016, 11:55pm...");
        secondEvent.setDescription("Raleigh is starting to wear out. Old man Raleigh who is used to going to bed no later than 10:30...");

        events.add(firstEvent);
        events.add(secondEvent);

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, events);

        listView = (ListView) findViewById(R.id.savedEventsList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {

                Intent intent = new Intent(SavedEvents.this, SavedEventDetails.class);

                Bundle bundle = new Bundle();
                bundle.putString("title", ((Event) listView.getItemAtPosition(position)).getTitle());
                bundle.putString("description", ((Event) listView.getItemAtPosition(position)).getDescription());
                bundle.putString("date", ((Event) listView.getItemAtPosition(position)).getTime());

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

    }
}

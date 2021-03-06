package com.zangateam.ryanwatt.zangateam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    Feed feed;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feed = new Feed(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            feed.setBundle(extras);
        }
        feed.execute();

        listView = feed.populateListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {

                Intent intent = new Intent(MainActivity.this, EventDetails.class);

                Bundle bundle = new Bundle();
                bundle.putString("title", ((Event) listView.getItemAtPosition(position)).getTitle());
                bundle.putString("description", ((Event) listView.getItemAtPosition(position)).getDescription());
                bundle.putString("date", ((Event) listView.getItemAtPosition(position)).getTime());

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    public void goToFilter(View view) {
        Intent intent = new Intent(MainActivity.this, FilterSelection.class);

        startActivity(intent);
    }

    public void goToSaved(View view) {
        Intent intent = new Intent(MainActivity.this, SavedEvents.class);

        startActivity(intent);
    }
}

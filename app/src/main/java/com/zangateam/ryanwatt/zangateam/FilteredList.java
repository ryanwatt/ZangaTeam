package com.zangateam.ryanwatt.zangateam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class FilteredList extends AppCompatActivity {
    Feed feed;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_list);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        feed.setBundle(bundle);
        feed.execute();
    }
}

package com.zangateam.ryanwatt.zangateam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetails extends AppCompatActivity {

    TextView titleView;
    TextView dateView;
    TextView descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        titleView = (TextView) findViewById(R.id.titleView);
        dateView = (TextView) findViewById(R.id.dateView);
        descriptionView = (TextView) findViewById(R.id.descriptionView);

        if (!bundle.isEmpty()) {
            titleView.setText(bundle.getString("title"));
            dateView.setText(bundle.getString("date"));
            descriptionView.setText(bundle.getString("description"));
        }
    }
}

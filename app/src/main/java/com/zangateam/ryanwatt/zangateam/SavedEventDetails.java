package com.zangateam.ryanwatt.zangateam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SavedEventDetails extends AppCompatActivity {

    TextView titleView;
    TextView dateView;
    TextView descriptionView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_event_details);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        titleView = (TextView) findViewById(R.id.savedTitleView);
        dateView = (TextView) findViewById(R.id.savedDateView);
        descriptionView = (TextView) findViewById(R.id.savedDescriptionView);

        if (!bundle.isEmpty()) {
            titleView.setText(bundle.getString("title"));
            dateView.setText(bundle.getString("date"));
            descriptionView.setText(bundle.getString("description"));
        }
    }
}

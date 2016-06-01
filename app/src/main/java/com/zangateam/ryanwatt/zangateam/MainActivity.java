package com.zangateam.ryanwatt.zangateam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void notATest() {
        String testString = "This IS a test"; //Raleigh's comment
        // another comment
    }
}

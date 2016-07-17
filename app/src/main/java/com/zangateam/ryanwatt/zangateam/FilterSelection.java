package com.zangateam.ryanwatt.zangateam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FilterSelection extends AppCompatActivity {
    private Spinner spinner;

    private EditText startDateInput;
    private EditText endDateInput;
    private List<String> keywordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_selection);

        spinner = (Spinner) findViewById(R.id.category_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        startDateInput = (EditText) findViewById(R.id.startDateInput);
        endDateInput = (EditText) findViewById(R.id.endDateInput);

    }

    public void addKeyword(View view) {
        EditText keywordInput = (EditText) findViewById(R.id.keywordInput);
        String keyWordResult = keywordInput.getText().toString();
        keywordList.add(keyWordResult);

        keywordInput.setText("");
    }

    public void collectFilterInput(View view) {
        String categoryResult = spinner.getSelectedItem().toString();

        String startDateResult = startDateInput.getText().toString();
        String endDateResult = endDateInput.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("category", categoryResult);
        bundle.putStringArrayList("keywords", (ArrayList<String>) keywordList);
        bundle.putString("startDate", startDateResult);
        bundle.putString("endDate", endDateResult);

        Intent intent = new Intent(FilterSelection.this, MainActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}

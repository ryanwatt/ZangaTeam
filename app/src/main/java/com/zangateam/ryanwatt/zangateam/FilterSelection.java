package com.zangateam.ryanwatt.zangateam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class FilterSelection extends AppCompatActivity {
    private Spinner spinner;
    private EditText keywordInput;
    private EditText startDateInput;
    private EditText endDateInput;

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

        keywordInput = (EditText) findViewById(R.id.keywordInput);
        startDateInput = (EditText) findViewById(R.id.startDateInput);
        endDateInput = (EditText) findViewById(R.id.endDateInput);

    }

    public void collectFilterInput(View view) {
        String categoryResult = spinner.getSelectedItem().toString();
        String keyWordResult = keywordInput.getText().toString();

        String startDateResult = startDateInput.getText().toString();
        String endDateResult = endDateInput.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("category", categoryResult);
        bundle.putString("keyword", keyWordResult);
        bundle.putString("startDate", startDateResult);
        bundle.putString("endDate", endDateResult);

        Intent intent = new Intent(FilterSelection.this, MainActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}

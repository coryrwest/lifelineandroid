package com.lifeline;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.lifeline.fragments.DatePickerFragment;

import java.util.HashMap;
import java.util.Map;

public class ManualDataPoint extends FragmentActivity {
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manualdatapoint);

        // Set the spinner choices
        Spinner spinner = (Spinner) findViewById(R.id.mdp_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void save(View view){
        if (view.getId() == R.id.save_btn) {
            Button btn = (Button) findViewById(R.id.save_btn);
            Spinner type = (Spinner) findViewById(R.id.mdp_spinner);
            String count = ((TextView) findViewById(R.id.count)).getText().toString();

            if (Integer.getInteger(count, 0) == 0) {
                Toast.makeText(getApplicationContext(), "Please enter a count greater than 0",
                        Toast.LENGTH_LONG).show();
            } else {
                btn.setEnabled(false);

                String apiType = type.getSelectedItem().toString().toLowerCase();

                Map<String, String> dataPoint = new HashMap<String, String>();
                dataPoint.put("date", date);
                dataPoint.put("value", "+" + count);
                String json = new GsonBuilder().create().toJson(dataPoint, Map.class);
                DAL dal = new DAL();
                boolean success = dal.PostToAPI(apiType, json);

                if (success) {
                    btn.setEnabled(true);
                    finish();
                } else {
                    btn.setEnabled(true);
                }
            }
        }
    }

    public void close(View view) {
        finish();
    }

    public void updateDate(int year, int month, int day) {
        date = buildDate(String.valueOf(year), String.valueOf(month + 1), String.valueOf(day));

        Button datePicker = (Button) findViewById(R.id.mdp_date);
        datePicker.setText(date);
    }

    public String formatSingleDigitDate(String date) {
        if (date.length() == 1) {
            return "0" + date;
        }
        return date;
    }

    public String buildDate(String year, String month, String day) {
        return formatSingleDigitDate(month) + "-" + formatSingleDigitDate(day) + "-" + year;
    }
}
package com.lifeline;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class Notes extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        EditText notesText = (EditText) findViewById(R.id.notes_text);

    }

    public void save(View view){
        if (view.getId() == R.id.save_btn) {
            Button btn = (Button) findViewById(R.id.save_btn);
            EditText notesText = (EditText) findViewById(R.id.notes_text);

            btn.setEnabled(false);
            String text = notesText.getText().toString();

            Map<String, String> comment = new HashMap<String, String>();
            comment.put("note", text);
            String json = new GsonBuilder().create().toJson(comment, Map.class);
            DAL dal = new DAL();
            boolean success = dal.PostToAPI("dailyNotes", json);

            if (success) {
                btn.setEnabled(true);
                finish();
            } else {
                btn.setEnabled(true);
            }
        }
    }

    public void close(View view) {
        finish();
    }
}

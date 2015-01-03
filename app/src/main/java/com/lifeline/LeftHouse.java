package com.lifeline;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class LeftHouse extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lefthouse);
    }

    public void save(View view){
        if (view.getId() == R.id.save_btn) {
            Button btn = (Button) findViewById(R.id.save_btn);
            EditText leftHouseText = (EditText) findViewById(R.id.lefthouse_text);

            btn.setEnabled(false);
            String text = leftHouseText.getText().toString();

            Map<String, String> comment = new HashMap<String, String>();
            comment.put("reason", text);
            String json = new GsonBuilder().create().toJson(comment, Map.class);
            DAL dal = new DAL();
            boolean success = dal.PostToAPI("left_house", json);

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
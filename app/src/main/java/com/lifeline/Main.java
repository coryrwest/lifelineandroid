package com.lifeline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UpdateUI();

        //DatabaseManager manager = new DatabaseManager();
    }

    private void UpdateUI() {
        // Disable buttons for once a day data points.
        DAL dal = new DAL();
        JSONObject sore = dal.GetFromAPI("sore");
        //JSONObject sick = dal.GetFromAPI("sick");
        JSONObject water_count = dal.GetFromAPI("water");
        if (sore != null) {
            Button btn = (Button) findViewById(R.id.sore_btn);
            btn.setEnabled(false);
        }
        if (water_count != null) {
//            try {
//                UpdateCounter(R.id.water_count, water_count.get("value").toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), Preferences.class);
            startActivity(i);
        } else if (id == R.id.action_lefthouse) {
            Intent i = new Intent(getApplicationContext(), LeftHouse.class);
            startActivity(i);
        } else if (id == R.id.action_mdp) {
            Intent i = new Intent(getApplicationContext(), ManualDataPoint.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void postWater(View view) {
        if (view.getId() == R.id.water_btn) {
            Intent i = new Intent(getApplicationContext(), Water.class);
            startActivity(i);
        }
    }

    public void postPick(View view) {
        if (view.getId() == R.id.pick_btn) {
            Button btn = (Button) findViewById(R.id.pick_btn);
            PostItem(btn, "pick", getString(R.string.pick));
        }
    }

    public void postSick(View view) {
        if (view.getId() == R.id.sick_btn) {
            Button btn = (Button) findViewById(R.id.sick_btn);
            PostItem(btn, "sick", getString(R.string.sick));
        }
    }

    public void postSore(View view) {
        if (view.getId() == R.id.sore_btn) {
            Button btn = (Button) findViewById(R.id.sore_btn);
            boolean success = PostItem(btn, "sore", getString(R.string.sore));
            if (success) {
                btn.setEnabled(false);
            }
        }
    }

    public void steps(View view) {
        if (view.getId() == R.id.steps_btn) {
            Button btn = (Button) findViewById(R.id.sore_btn);
            Intent i = new Intent(getApplicationContext(), Steps.class);
            startActivity(i);
        }
    }

    public void notes(View view) {
        if (view.getId() == R.id.notes_btn) {
            Button btn = (Button) findViewById(R.id.notes_btn);
            Intent i = new Intent(getApplicationContext(), Notes.class);
            startActivity(i);
        }
    }



    // ------------
    // Helpers
    protected void UpdateCounter(int id, String text) {
        TextView counter = (TextView) findViewById(id);
        counter.setText(text);
    }

    protected boolean PostItem(Button btn, String type, String btnText) {
        btn.setEnabled(false);
        String text = btnText;

        Map<String, String> comment = new HashMap<String, String>();
        comment.put("change", "+");
        String json = new GsonBuilder().create().toJson(comment, Map.class);
        DAL dal = new DAL();
        boolean success = dal.PostToAPI(type, json, true);

        if (success) {
            btn.setText("Success");
            btn.setEnabled(true);

            // Reset button text
            Timer timing = new Timer();
            timing.schedule(new Updater(btn, text), 2000);

            //UpdateUI();

            return true;
        } else {
            btn.setEnabled(true);
            return false;
        }
    }

    private static class Updater extends TimerTask {
        private final Button subject;
        private final String text;

        public Updater(Button subject, String text) {
            this.subject = subject;
            this.text = text;
        }

        @Override
        public void run() {
            subject.post(new Runnable() {

                public void run() {
                    subject.setText(text);
                }
            });
        }
    }
}

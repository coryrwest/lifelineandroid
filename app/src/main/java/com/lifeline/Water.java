package com.lifeline;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class Water extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

    }

    public void water20(View view) {
        handleBtn(view, R.id.water_20_btn, 20);
    }

    public void water16(View view) {
        handleBtn(view, R.id.water_16_btn, 16);
    }

    public void water12(View view) {
        handleBtn(view, R.id.water_12_btn, 12);
    }

    public void water8(View view) {
        handleBtn(view, R.id.water_8_btn, 8);
    }

    public void waterCustom(View view){
        if (view.getId() == R.id.save_btn) {
            Button btn = (Button) findViewById(R.id.save_btn);
            EditText customOz = (EditText) findViewById(R.id.custom_oz_text);

            btn.setEnabled(false);

            String oz = customOz.getText().toString();
            boolean success = saveWater(Integer.parseInt(oz));

            if (success) {
                btn.setEnabled(true);
                finish();
            } else {
                btn.setEnabled(true);
            }
        }
    }
	
	private void handleBtn(View view, int btnID, int water) {
	if (view.getId() == btnID) {
            Button btn = (Button) findViewById(btnID);

            btn.setEnabled(false);

            boolean success = saveWater(water);

            if (success) {
                btn.setEnabled(true);
                finish();
            } else {
                btn.setEnabled(true);
            }
        }
	}

    private boolean saveWater(int waterOz) {
        Map<String, String> comment = new HashMap<String, String>();
        comment.put("change", "+" + String.valueOf(waterOz));
        String json = new GsonBuilder().create().toJson(comment, Map.class);
        DAL dal = new DAL();
        return dal.PostToAPI("water", json, true);
    }

    public void close(View view) {
        finish();
    }
}

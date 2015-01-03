package com.lifeline.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lifeline.helpers.LifeLineDbHelper;
import com.lifeline.objects.Step;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get step count for today
        LifeLineDbHelper dbhelper = new LifeLineDbHelper(context);
        Step step = dbhelper.GetStepByDate(Calendar.getInstance().getTime());

        if (!step.isNew) {
//            Map<String, String> comment = new HashMap<String, String>();
//            comment.put("count", String.valueOf(step.count));
//            comment.put("date", step.getDate());
//            String json = new GsonBuilder().create().toJson(comment, Map.class);
//            DAL dal = new DAL();
//            boolean success = dal.PostToAPI("steps", json, true);
        }
    }
}

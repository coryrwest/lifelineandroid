package com.lifeline;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lifeline.helpers.LifeLine;
import com.lifeline.helpers.LifeLineDbHelper;
import com.lifeline.objects.Step;
import com.lifeline.services.StepService;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class Steps extends Activity {
    private TextView serviceStatus;
    private TextView stepCount;
    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        serviceStatus = (TextView) findViewById(R.id.steps_view);
        stepCount = (TextView) findViewById(R.id.steps_today);

        setText();
    }

    private void setText() {
        // Set service status
        if (LifeLine.isMyServiceRunning(getApplicationContext(), StepService.class)) {
            serviceStatus.setText("Service is running");
        } else{
            serviceStatus.setText("Service is not running");
        }

        LifeLineDbHelper dbHelper = new LifeLineDbHelper(getApplicationContext());
        // Get today's record
        Step step = dbHelper.GetStepByDate(Calendar.getInstance().getTime());

        if (!step.isNew) {
            stepCount.setText(String.valueOf(step.count));
        }
    }

    public void data(View view) {
        finish();
    }

    public void startService(View view) {
        Intent mServiceIntent = new Intent(getApplicationContext(), StepService.class);
        getApplicationContext().startService(mServiceIntent);
        setText();
    }

    public void stopService(View view) {
        Intent mServiceIntent = new Intent(getApplicationContext(), StepService.class);
        getApplicationContext().stopService(mServiceIntent);
        setText();
    }
}

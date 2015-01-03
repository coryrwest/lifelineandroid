package com.lifeline.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.lifeline.helpers.LifeLine;
import com.lifeline.helpers.LifeLineDbHelper;
import com.lifeline.objects.Step;

import java.util.Calendar;

public class StepService extends Service implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d("StepService", "There was a Step");

        // Only update if this is a step
        Sensor sensor = sensorEvent.sensor;
        float[] values = sensorEvent.values;
        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            // Save step to local DB
            LifeLineDbHelper dbHelper = new LifeLineDbHelper(getApplicationContext());

            // Get today's record
            Step step = dbHelper.GetStepByDate(Calendar.getInstance().getTime());
            step.count = step.count + 1;

            dbHelper.SaveStep(step);
        }

//        Sensor sensor = sensorEvent.sensor;
//        float[] values = sensorEvent.values;
//        int value = -1;
//
//        if (values.length > 0) {
//            value = (int) values[0];
//        }
//
//        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
//            // Log step somehow
//            //textView.setText("Step Counter Detected : " + value);
//        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("StepService", "onCreate");

        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (mStepCounterSensor != null) {
            mSensorManager.registerListener(this, mStepCounterSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        Log.d("StepService", "onDestroy");
    }
}

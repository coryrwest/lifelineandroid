package com.lifeline;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.helpers.LifeLine;
import com.lifeline.helpers.LifeLineDbHelper;
import com.lifeline.objects.Step;
import com.lifeline.services.AlarmReceiver;
import com.lifeline.services.StepService;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

    public void setSync(View view) {
        // time at which alarm will be scheduled here alarm is scheduled at 1 day from current time,
        // we fetch  the current time in milliseconds and added 1 day time
        // i.e. 24*60*60*1000= 86,400,000   milliseconds in a day
        Long time = new GregorianCalendar().getTimeInMillis()+(10*1000);

        // create an Intent and set the class which will execute when Alarm triggers, here we have
        // given AlarmReciever in the Intent, the onRecieve() method of this class will execute when
        // alarm triggers and
        //we will write the code to send SMS inside onRecieve() method pf Alarmreciever class
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);

        // create the object
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular time
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(this, "Alarm Scheduled for 10 seconds", Toast.LENGTH_LONG).show();
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

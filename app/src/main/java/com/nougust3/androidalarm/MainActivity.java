package com.nougust3.androidalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private RelativeLayout alarmEditor;

    private ImageButton addAlarmBtn;
    //private ToggleButton alarmToggleBtn;
    private TimePicker alarmTimePicker;

    public static MainActivity instance() {
        return instance;
    }

    @Override
    public void onStart() {
        super.onStart();
        instance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addAlarmBtn = (ImageButton) findViewById(R.id.addAlarmBtn);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmEditor = (RelativeLayout) findViewById(R.id.alarmEditor);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        addAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alarmEditor.getVisibility() == View.VISIBLE) {
                    alarmEditor.setVisibility(View.GONE);

                    Calendar calendar = Calendar.getInstance();
                    Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

                    calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                    calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());

                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
                    alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                }
                else {
                    alarmEditor.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void showMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

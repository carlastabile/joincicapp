package ve.com.joincic.joincicapp.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ve.com.joincic.joincicapp.R;
import ve.com.joincic.joincicapp.adapters.ScheduleItem;
import ve.com.joincic.joincicapp.application.JoincicApp;
import ve.com.joincic.joincicapp.controllers.ScheduleController;

/**
 * Created by Carla Urrea Stabile on 21/05/15.
 */
public class ScheduleItemDetailsActivity extends ParentActivity {
    SharedPreferences prefs;
    TextView titleTextV, presenterTextV, startHourTextV, endHourTextV, desc, dayTextV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences(JoincicApp.SCHEDULE_PREFS, MODE_PRIVATE);

        String title = prefs.getString("title", "");
        String startDate = prefs.getString("startDate", "");
        String endDate = prefs.getString("endDate", "");
        String description = prefs.getString("description", "");
        String presenterName = prefs.getString("presenterName", "");
        String day = prefs.getString("day", "");

        titleTextV = (TextView) findViewById(R.id.schedule_details_title);
        presenterTextV = (TextView) findViewById(R.id.schedule_details_presenter_name);
        dayTextV = (TextView) findViewById(R.id.schedule_details_day);
        startHourTextV = (TextView) findViewById(R.id.schedule_details_start_hour);
        endHourTextV = (TextView) findViewById(R.id.schedule_details_end_hour);
        desc = (TextView) findViewById(R.id.schedule_details_description);

        if (title != null && titleTextV != null){
            titleTextV.setText(title);
        }
        if (presenterName != null && presenterTextV != null){
            presenterTextV.setText(presenterName);
        }
        if (day != null && dayTextV != null){
            Date date = JoincicApp.stringToDate(day);
            day = new SimpleDateFormat("EEEE").format(date);
            dayTextV.setText(day);
        }
        if (startDate != null && startHourTextV != null){
            startHourTextV.setText(JoincicApp.getTime(startDate));
        }
        if (endDate != null && endHourTextV != null){
            endHourTextV.setText(JoincicApp.getTime(endDate));
        }
        if (description != null && desc != null){
            desc.setText(description);
        }
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule_details;
    }

    @Override
    protected String getActivityName() {
        prefs = getSharedPreferences(JoincicApp.SCHEDULE_PREFS, MODE_PRIVATE);
        boolean isPresentation = prefs.getBoolean("isPresentation", true);

        if (isPresentation) {
            return getResources().getString(R.string.presentation);
        }else {
            return getResources().getString(R.string.work_table);
        }
    }
}

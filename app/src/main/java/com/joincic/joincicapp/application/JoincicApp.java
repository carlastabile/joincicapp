package com.joincic.joincicapp.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Carla Urrea Stabile on 24/04/15.
 */
public class JoincicApp extends Application {
    public final static String TAG = "JoincicApp";
    public  final static String REQUEST_PREFS = "request_prefs";
    public final static String SCHEDULE_PREFS = "schedule_prefs";
    public static final String ASSISTANT_PREFS = "AssistantPrefs";
    public final static String SCHEDULE_REQUEST = "schedule_request";
    public final static String PARSE_APP_KEY = "GjE0qnaXb9uf7zePIhR1pN0lSrNIaSXAheM0PsJQ";
    public final static String PARSE_CLIENT_KEY = "s9aMpYXNCA0yQGcRs1UTmogarzFHwZlhtuGP79ex";
    public static final String DAY_1 = "2016-06-01";
    public static final String DAY_2 = "2016-06-02";
    public static final String DAY_3 = "2016-06-03";
    public static final String DAY_4 = "2016-06-04";
    public static final String ASSISTANT_NAME = "assistant_name";
    public static final String ASSISTANT_CI = "assistant_ci";
    public static final String ASSISTANT_ID = "assistant_id";
    public static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, PARSE_APP_KEY, PARSE_CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        Log.d(TAG, "-------------------------------- ON CREATE! ");

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }

    public static String getTime(String old){
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date dateObj;
        String newDateStr = null;
        try
        {
            dateObj = df.parse(old);
            SimpleDateFormat fd = new SimpleDateFormat("hh:mma");
            newDateStr = fd.format(dateObj);
            Log.d(TAG, "hora es:" + newDateStr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return newDateStr;
    }

    public static Date stringToDate(String date){
        String[] dateFormats = {"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd"};

        SimpleDateFormat df;
        Date dateObj;

        for (String formatString : dateFormats){
            try{
                df = new SimpleDateFormat(formatString);
                dateObj = df.parse(date);
                return dateObj;

            }
            catch (Exception e) {}
        }
        return null;
    }



}

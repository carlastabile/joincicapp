package ve.com.joincic.joincicapp.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by Carla Urrea Stabile on 24/04/15.
 */
public class JoincicApp extends Application {
    public final static String TAG = "JoincicApp";
    public  final static String REQUEST_PREFS = "request_prefs";
    public final static String SCHEDULE_REQUEST = "schedule_request";
    public final static String PARSE_APP_KEY = "GjE0qnaXb9uf7zePIhR1pN0lSrNIaSXAheM0PsJQ";
    public final static String PARSE_CLIENT_KEY = "s9aMpYXNCA0yQGcRs1UTmogarzFHwZlhtuGP79ex";



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
}
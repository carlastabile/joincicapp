package ve.com.joincic.joincicapp.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Carla Urrea Stabile on 24/04/15.
 */
public class JoincicApp extends Application {
    public final static String TAG = "JoincicApp";
    public  final static String REQUEST_PREFS = "request_prefs";
    public final static String SCHEDULE_REQUEST = "schedule_request";


    @Override
    public void onCreate() {
        super.onCreate();
    }
}

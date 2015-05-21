package ve.com.joincic.joincicapp.requesters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import ve.com.joincic.joincicapp.R;
import ve.com.joincic.joincicapp.application.JoincicApp;
import ve.com.joincic.joincicapp.controllers.Schedule;
import ve.com.joincic.joincicapp.controllers.ScheduleController;
import ve.com.joincic.joincicapp.views.ScheduleActivity;

/**
 * Created by Carla Urrea Stabile on 02/04/15.
 */
public class ScheduleRequester extends AsyncTask<String, Integer, Integer> {
    private static String TAG = "ScheduleRequester";
    private static String PRESENTATIONS_PATH = "http://sistema.joincic.com.ve/ponencias.json";
    private static String WORK_TABLES_PATH = "http://sistema.joincic.com.ve/mesas_de_trabajo.json";
    Context context;
    ProgressDialog prgDialog;

    public ScheduleRequester(Context context) {
        this.context = context;
        this.prgDialog = new ProgressDialog(this.context);
        this.prgDialog.setMessage(this.context.getResources().getString(R.string.loading));
        this.prgDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (this.prgDialog != null && !this.prgDialog.isShowing()){
            this.prgDialog.show();
        }
    }

    @Override
    protected Integer doInBackground(String... params) {

        int presentationsStatusCode = getPresentations();
        int workTableStatusCode = getWorkTables();

        int finalCode = presentationsStatusCode == 200 && workTableStatusCode == 200? 200 : 500;

        return finalCode;

    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        if (this.prgDialog != null && this.prgDialog.isShowing()){
            this.prgDialog.dismiss();
        }

        if (result == 200){
            Toast.makeText(this.context, "Fue 200!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(context, ScheduleActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(i);
        } else {
            Toast.makeText(this.context, "Hubo un error!", Toast.LENGTH_LONG).show();
        }


    }

    public int getPresentations(){
        HttpGet get = new HttpGet(PRESENTATIONS_PATH);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;

        try {
            response = client.execute(get);

            if (response != null) {

                String result = EntityUtils.toString(response.getEntity());
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    Log.d(TAG,"FUe 200 PRESENTATIONS");
                    JSONArray json = new JSONArray(result.substring(
                            result.indexOf("["), result.lastIndexOf("]") + 1));
                    Log.d(TAG, "----" + json.toString());
                    Gson gson = new Gson();
                    Schedule[] p = gson.fromJson(json.toString(),
                            Schedule[].class);

                        Log.d(TAG, "----" +p.length);

                    for (int i=0; i<p.length; i++){
                        Log.d(TAG, "---- id " + p[i].getPonencia().getTitulo());
                    }

                    ArrayList<Schedule> presentations = new ArrayList<Schedule>(Arrays.asList(p));

                    ScheduleController.getInstance(this.context);

                    ScheduleController.insertPresentations(presentations);
                }
                return statusCode;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 500;
    }

    /**
     * */

    public int getWorkTables(){
        HttpGet get = new HttpGet(WORK_TABLES_PATH);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;

        try {
            response = client.execute(get);

            if (response != null) {

                String result = EntityUtils.toString(response.getEntity());
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    Log.d(TAG,"FUE 200 MESA DE TRABAJO");
                    JSONArray json = new JSONArray(result.substring(
                            result.indexOf("["), result.lastIndexOf("]") + 1));
                    Log.d(TAG, "----" + json.toString());
                    Gson gson = new Gson();
                    Schedule[] p = gson.fromJson(json.toString(),
                            Schedule[].class);

                    Log.d(TAG, "----" +p.length);

                    for (int i=0; i<p.length; i++){
                        Log.d(TAG, "---- id " + p[i].getMesa_de_trabajo().getTitulo());
                    }

                    ArrayList<Schedule> workTables = new ArrayList<Schedule>(Arrays.asList(p));

                    ScheduleController.getInstance(this.context);

                    ScheduleController.insertWorkTables(workTables);
                }
                return statusCode;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 500;
    }

}

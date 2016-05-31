package com.joincic.joincicapp.requesters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.joincic.joincicapp.R;

import com.joincic.joincicapp.application.JoincicApp;
import com.joincic.joincicapp.controllers.Assistant;
import com.joincic.joincicapp.controllers.AssistantController;
import com.joincic.joincicapp.views.EnrollWorkTableActivity;

/**
 * Created by carla on 25/05/15.
 */
public class ValidateIDRequester extends AsyncTask<String, Integer, Integer> {
    private static String TAG = "ValidateIDRequester";
    private static String VALIDATE_PATH = "http://sistema.joincic.com.ve/participantes/validarCedula.json?cedula=";
    Context context;
    ProgressBar prgBar;
    int ci;

    public ValidateIDRequester(Context context, ProgressBar prgBar, int ci) {
        this.context = context;
        this.prgBar = prgBar;
        this.ci = ci;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (prgBar != null && !prgBar.isShown()) {
            prgBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected Integer doInBackground(String... params) {

        int validateCode = validate(), wtCode;

        if (validateCode == 200){
            ScheduleRequester requester = new ScheduleRequester(this.context, true);
             wtCode = requester.getWorkTables();
             return wtCode;
        } else {
            return validateCode;
        }


    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        if (prgBar != null && prgBar.isShown()) {
            prgBar.setVisibility(View.GONE);
        }

        if (result == 200) {
            RelativeLayout enrollLayout = EnrollWorkTableActivity.getEnrollLayout();
            RelativeLayout validateLayout = EnrollWorkTableActivity.getValidateLayout();
            enrollLayout.setVisibility(View.VISIBLE);
            Log.d(TAG, "enroll layout esta " +enrollLayout.getVisibility());
            validateLayout.setVisibility(View.GONE);
            Log.d(TAG, "validate layout esta " +validateLayout.getVisibility());

            EnrollWorkTableActivity.setList(this.context);
        } else if (result == 404) {
            Toast.makeText(this.context, context.getResources().getString(R.string.assistant_not_found),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.context, context.getResources().getString(R.string.validate_error),
                    Toast.LENGTH_LONG).show();
        }


    }


    public int validate() {
        HttpGet get = new HttpGet(VALIDATE_PATH + ci);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;

        try {
            response = client.execute(get);

            if (response != null) {

                String result = EntityUtils.toString(response.getEntity());
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    Log.d(TAG, "FUE 200 VALIDATE");

                    JSONObject json = new JSONObject(result);

                    Log.d(TAG, "----" + json.toString());
                    Gson gson = new Gson();
                    Assistant a = gson.fromJson(json.toString(),
                            Assistant.class);

                    Log.d(TAG, "---- id " + a.getParticipante().getId());

                    AssistantController.getInstance(context);
                    AssistantController.insertAssistant(a);
                    SharedPreferences prefs = context.getSharedPreferences(JoincicApp.ASSISTANT_PREFS,
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt(JoincicApp.ASSISTANT_CI, a.getParticipante().getCedula());
                    editor.putInt(JoincicApp.ASSISTANT_ID, a.getParticipante().getId());
                    editor.putString(JoincicApp.ASSISTANT_NAME, a.getParticipante().getNombre() + " " +
                            a.getParticipante().getApellido());
                    editor.apply();
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

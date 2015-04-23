package ve.com.joincic.joincicapp.requesters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import ve.com.joincic.joincicapp.controllers.Ponencia;
import ve.com.joincic.joincicapp.controllers.Schedule;
import ve.com.joincic.joincicapp.controllers.ScheduleController;

/**
 * Created by Carla Urrea Stabile on 02/04/15.
 */
public class ScheduleRequester extends AsyncTask<String, Integer, Integer> {
    private static String TAG = "ScheduleRequester";
    private static String PRESENTATIONS_PATH = "http://sistema.joincic.com.ve/ponencias.json";
    private static String WORK_TABLES_PATH = "http://sistema.joincic.com.ve/mesas_de_trabajo.json";
    Context context;

    public ScheduleRequester(Context context) {
        this.context = context;
    }


    @Override
    protected Integer doInBackground(String... params) {

        return getPresentations();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        if (result == 200){
            Toast.makeText(this.context, "Fue 200!", Toast.LENGTH_LONG).show();
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
                    Log.d(TAG,"FUe 200");
                    JSONArray json = new JSONArray(result.substring(
                            result.indexOf("["), result.lastIndexOf("]") + 1));
                    Log.d(TAG, "----" + json.toString());
                    Gson gson = new Gson();
                    Ponencia[] p = gson.fromJson(json.toString(),
                            Ponencia[].class);

                        Log.d(TAG, "----" +p.length);

                    for (int i=0; i<p.length; i++){
                        Log.d(TAG, "---- id " + p[i].getTitulo());
                    }

                    ArrayList<Ponencia> presentations = new ArrayList<Ponencia>(Arrays.asList(p));

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
}

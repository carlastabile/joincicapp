package ve.com.joincic.joincicapp.requesters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import ve.com.joincic.joincicapp.R;
import ve.com.joincic.joincicapp.controllers.Assistant;
import ve.com.joincic.joincicapp.controllers.AssistantController;
import ve.com.joincic.joincicapp.views.EnrollWorkTableActivity;

/**
 * Created by carla on 25/05/15.
 */
public class EnrollWorTableRequester extends AsyncTask<String, Integer, Integer> {
    private static String TAG = "EnrollWorTableRequester";
    private static String ENROLL_PATH = "http://sistema.joincic.com.ve/participantes_mesa/create.json";
    Context context;
    int ci, wtId;
    ProgressDialog prgDialog;

    public EnrollWorTableRequester(Context context, int ci, int wtId) {
        this.context = context;
        this.ci = ci;
        this.wtId = wtId;
        this.prgDialog = new ProgressDialog(this.context);
        this.prgDialog.setMessage(this.context.getResources().getString(R.string.enrolling));
        this.prgDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (prgDialog != null && !prgDialog.isShowing()) {
            prgDialog.show();
        }
    }

    @Override
    protected Integer doInBackground(String... params) {

        int enrollCode = enroll();

        return enrollCode;

    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        if (prgDialog != null && prgDialog.isShowing()) {
            prgDialog.dismiss();
        }

        if (result == 200) {
            Toast.makeText(this.context, context.getResources().getString(R.string.enroll_success),
                    Toast.LENGTH_LONG).show();
        } else if (result == 404) {
            Toast.makeText(this.context, context.getResources().getString(R.string.mt_not_found),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.context, context.getResources().getString(R.string.enroll_error),
                    Toast.LENGTH_LONG).show();
        }


    }

    public int enroll() {
        HttpPost get = new HttpPost(ENROLL_PATH + ci); //change
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("participantes_mesa[paticipante_id]", String.valueOf(ci)));
        postParams.add(new BasicNameValuePair("participantes_mesa[mesa_de_trabajo_id]",
                String.valueOf(wtId)));
        UrlEncodedFormEntity encodedParams;

        try {
            encodedParams = new UrlEncodedFormEntity(postParams, "UTF-8");
            get.setEntity(encodedParams);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        try {
            response = client.execute(get);

            if (response != null) {

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    Log.d(TAG, "FUE 200 INSCRIPCION");
                }
                return statusCode;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 500;
    }

}

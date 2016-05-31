package ve.com.joincic.joincicapp.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ve.com.joincic.joincicapp.R;
import ve.com.joincic.joincicapp.adapters.Item;
import ve.com.joincic.joincicapp.adapters.ItemAdapter;
import ve.com.joincic.joincicapp.adapters.ScheduleItem;
import ve.com.joincic.joincicapp.application.JoincicApp;
import ve.com.joincic.joincicapp.controllers.ScheduleController;
import ve.com.joincic.joincicapp.requesters.EnrollWorTableRequester;
import ve.com.joincic.joincicapp.requesters.ValidateIDRequester;

/**
 * Created by Carla Urrea Stabile on 25/05/15.
 */
public class EnrollWorkTableActivity extends ParentActivity {
    Button validateBtn;
    ProgressBar prgBar;
    EditText ci;
    static RelativeLayout enrollLayout, validateLayout;
    static ListView wtList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enrollLayout = (RelativeLayout) findViewById(R.id.enroll_work_table_enroll_layout);
        validateLayout = (RelativeLayout) findViewById(R.id.enroll_work_table_validate_layout);
        wtList = (ListView) findViewById(R.id.enroll_work_table_list);

        ci = (EditText) findViewById(R.id.enroll_work_table_ci);
        prgBar = (ProgressBar) findViewById(R.id.progressBar);
        validateBtn = (Button) findViewById(R.id.enroll_work_table_button);

        validateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {
                    if (ci != null && ci.length() > 0) {
                        int c = Integer.parseInt(ci.getText().toString());
                        ValidateIDRequester requester = new ValidateIDRequester(
                                EnrollWorkTableActivity.this, prgBar, c);
                        requester.execute();
                    } else {
                        Toast.makeText(EnrollWorkTableActivity.this,
                                res.getString(R.string.ci_blank), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EnrollWorkTableActivity.this,
                            res.getString(R.string.no_connection), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public static void setList(final Context context) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String day = format.format(cal.getTime()).toString();

        Log.d(TAG, "---- PARA EL DIA " + day);
        ScheduleController.getInstance(context);
        final ArrayList<Item> items = ScheduleController.getWorkTables(day);

        if (items != null && items.size() == 0) {
            format = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
            day = format.format(cal.getTime());
            Toast.makeText(context,
                    res.getString(R.string.no_work_tables_found) + " " + day, Toast.LENGTH_LONG).show();
        } else {
            ItemAdapter adapter = new ItemAdapter(context, items);
            SharedPreferences prefs = context.getSharedPreferences(JoincicApp.ASSISTANT_PREFS, 0);
            final int ci = prefs.getInt(JoincicApp.ASSISTANT_CI, 0);

            if (wtList != null) {
                wtList.setAdapter(adapter);

                wtList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(
                                context);
                        alert.setTitle(res.getString(R.string.confirm));
                        alert.setMessage(res.getString(R.string.enroll_confirm));
                        final int pos = position;
                        alert.setPositiveButton(res.getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do your work here
                                dialog.dismiss();
                                ScheduleItem item = (ScheduleItem) items.get(pos);
                                EnrollWorTableRequester requester =
                                        new EnrollWorTableRequester(context, ci, item.getId());
                                requester.execute();
                            }
                        });
                        alert.setNegativeButton(res.getString(R.string.no), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });

                        alert.show();


                    }
                });
            } else {
                Toast.makeText(context,
                        res.getString(R.string.unknown_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_enroll_into_work_table;
    }

    @Override
    protected String getActivityName() {
        return res.getString(R.string.work_table);

    }

    public static RelativeLayout getEnrollLayout() {
        return enrollLayout;
    }

    public static RelativeLayout getValidateLayout() {
        return validateLayout;
    }

    public static ListView getWtList() {
        return wtList;
    }
}

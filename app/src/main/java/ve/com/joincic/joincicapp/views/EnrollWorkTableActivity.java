package ve.com.joincic.joincicapp.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ve.com.joincic.joincicapp.R;
import ve.com.joincic.joincicapp.requesters.ValidateIDRequester;

/**
 * Created by Carla Urrea Stabile on 25/05/15.
 */
public class EnrollWorkTableActivity extends ParentActivity {
    Button validateBtn;
    ProgressBar prgBar;
    EditText ci;
    static RelativeLayout enrollLayout, validateLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enrollLayout = (RelativeLayout) findViewById(R.id.enroll_work_table_enroll_layout);
        validateLayout = (RelativeLayout) findViewById(R.id.enroll_work_table_validate_layout);
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
}

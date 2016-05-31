package ve.com.joincic.joincicapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import com.joincic.joincicapp.R;
import ve.com.joincic.joincicapp.application.JoincicApp;
import ve.com.joincic.joincicapp.controllers.ScheduleController;
import ve.com.joincic.joincicapp.views.ScheduleItemDetailsActivity;

/**
 * Created by carla on 02/04/15.
 */

// Instances of this class are fragments representing a single
// object in our collection.
public class DemoObjectFragment extends Fragment {
    public static final String DAY_NUMBER = "day_number";


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_collection_object, container, false);

        Bundle args = getArguments();
        int dayNumber = args.getInt(DAY_NUMBER);
        Log.d("", "el dia es " + dayNumber );


        switch (dayNumber){
            case 1: //Tuesday
                showInfoByDay(rootView, JoincicApp.DAY_1);
                break;
            case 2:
                showInfoByDay(rootView, JoincicApp.DAY_2);
                break;
            case 3:
                showInfoByDay(rootView, JoincicApp.DAY_3);
                break;
            case 4:
                showInfoByDay(rootView, JoincicApp.DAY_4);
                break;
            default:
                break;

        }
//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));
        return rootView;
    }

    public void showInfoByDay(View v, String day){
        ScheduleController.getInstance(getActivity());
        final ArrayList<Item> items = ScheduleController.getSchedule(day);

        if (items != null){
            ItemAdapter adapter = new ItemAdapter(getActivity(), items);
            ListView scheduleList = (ListView) v.findViewById(R.id.schedule_list);
            scheduleList.setAdapter(adapter);

            scheduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ScheduleItem si = (ScheduleItem) items.get(position);

                    SharedPreferences prefs = getActivity().getSharedPreferences(
                            JoincicApp.SCHEDULE_PREFS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("isPresentation", si.isPresentation());
                    editor.putString("title", si.getTitle());
                    editor.putString("description", si.getDescription());
                    editor.putString("day", si.getDay());
                    editor.putString("startDate", si.getStartDate());
                    editor.putString("endDate", si.getEndDate());
                    editor.putString("presenterName", "Carla Urrea");
                    editor.apply();

                    Intent i = new Intent(getActivity(), ScheduleItemDetailsActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                }
            });
        }
    }
}
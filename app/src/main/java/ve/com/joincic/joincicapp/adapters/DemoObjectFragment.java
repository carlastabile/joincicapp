package ve.com.joincic.joincicapp.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ve.com.joincic.joincicapp.R;
import ve.com.joincic.joincicapp.application.JoincicApp;
import ve.com.joincic.joincicapp.controllers.ScheduleController;

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
        ArrayList<Item> items = ScheduleController.getSchedule(day);

        if (items != null){
            ItemAdapter adapter = new ItemAdapter(getActivity(), items);
            ListView scheduleList = (ListView) v.findViewById(R.id.schedule_list);
            scheduleList.setAdapter(adapter);
        }
    }
}
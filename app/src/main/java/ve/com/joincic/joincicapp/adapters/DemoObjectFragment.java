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
import ve.com.joincic.joincicapp.controllers.ScheduleController;

/**
 * Created by carla on 02/04/15.
 */

// Instances of this class are fragments representing a single
// object in our collection.
public class DemoObjectFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_collection_object, container, false);

        Bundle args = getArguments();
        int dayNumber = (Integer) args.get(ARG_OBJECT);
        Log.d("", "el dia es " + dayNumber);
        switch (dayNumber){
            case 1: //Tuesday

                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;

        }
        showInfoByDay(rootView, dayNumber);
//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));
        return rootView;
    }

    public void showInfoByDay(View v, int day){
        ScheduleController.getInstance(getActivity());
        ArrayList<Item> items = ScheduleController.getSchedule();

        if (items != null){
            ItemAdapter adapter = new ItemAdapter(getActivity(), items);
            ListView scheduleList = (ListView) v.findViewById(R.id.schedule_list);
            scheduleList.setAdapter(adapter);
        }
    }
}
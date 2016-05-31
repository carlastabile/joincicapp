package ve.com.joincic.joincicapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.joincic.joincicapp.R;
import ve.com.joincic.joincicapp.application.JoincicApp;

/**
 * Created by Carla Urrea Stabile on 20/05/15.
 */
public class ItemAdapter extends BaseAdapter {
    Context context;
    ArrayList<Item> items;
    LayoutInflater inflater;

    public ItemAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {

        Item i = items.get(position);

        if (i.isScheduleItem()){
            row = inflater.inflate(R.layout.activity_schedule_row, null);

            ScheduleItem si = (ScheduleItem) i;

            ImageView icon = (ImageView) row.findViewById(R.id.schedule_icon);
            if (si.isPresentation){
                icon.setImageResource(R.drawable.ic_presenter);
            } else{
                icon.setImageResource(R.drawable.ic_practical_work);
            }

            TextView title = (TextView) row.findViewById(R.id.schedule_title);

            if (title != null)
                title.setText(si.getTitle());

            TextView presenter = (TextView) row.findViewById(R.id.schedule_presenter_name);

            if (presenter != null)
                presenter.setText("Carla Urrea");

            TextView startDate = (TextView) row.findViewById(R.id.schedule_start_hour);
            if (startDate != null)
                startDate.setText(JoincicApp.getTime(si.getStartDate()) + " -");

            TextView endDate = (TextView) row.findViewById(R.id.schedule_end_hour);
            if (endDate != null)
                endDate.setText(JoincicApp.getTime(si.getEndDate()));

        }


        return row;
    }
}

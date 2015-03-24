package ve.com.joincic.joincicapp.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ve.com.joincic.joincicapp.R;

/**
 * This class consists of creating an adapter to show the menu. It extends
 * BaseAdapter and overrides the methods necessary to create a proper adapter.
 *
 * @author Carla Urrea Stabile
 * */

public class DrawerItemAdapter extends BaseAdapter {
    static final String TAG = "MenuAdapter";

    static class ViewHolder {
        TextView name;
        ImageView icon;
    }

    private ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
    private LayoutInflater inflater = null;
    private Context context;
    String name;
    Drawable icon;

    /**
     * Constructs a new instance of MenuAdapter
     *
     * @param context
     *            The context of the Activity where the adapter is going to be
     *            used.
     * @param items
     *            An ArrayList of Item containing the information of the user
     *            profile that it is going to be shown in the ListView.
     *
     * */
    public DrawerItemAdapter(Context context, ArrayList<DrawerItem> items) {
        super();
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public int getCount() {
        return items.size();
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * {@inheritDoc}
     * */
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        final DrawerItem i = items.get(position);

        if (i != null){
            v = inflater.inflate(R.layout.drawer_list_item, null);
            final ViewHolder holder = new ViewHolder();
            holder.name = (TextView) v.findViewById(R.id.drawer_list_name);
            holder.icon = (ImageView) v.findViewById(R.id.drawer_list_icon);

            if (holder.name != null){
                holder.name.setText(i.getName());
            }

            if (holder.icon != null){
                holder.icon.setImageResource(i.getIconId());
            }
        }

        return v;
    }


    /**
     * Sets the icon for each position of the rows of the menu depending of the
     * name of the row
     *
     * @param position
     *            the position of the row
     * @param v
     *            the View
     * @param title
     *            the title of the row
     * */
    public void setIcon(int position, View v, TextView title) {
//        if (position > 0) {
//            ImageView icon = (ImageView) v.findViewById(R.id.drawer_item_icon);
//            String t = (String) title.getText();
//            Drawable d;
//
//            // Generic Menu
//            if (t.equals(res.getString(R.string.news))) {
//                d = v.getResources().getDrawable(R.drawable.icon_news);
//                icon.setImageDrawable(d);
//                return;
//            } else if (t.equals(res.getString(R.string.messages))) {
//                d = v.getResources().getDrawable(R.drawable.icon_messages);
//                icon.setImageDrawable(d);
//                return;
//            } else if (t.equals(res.getString(R.string.calendar))) {
//                d = v.getResources().getDrawable(R.drawable.icon_calendar);
//                icon.setImageDrawable(d);
//                return;
//            } else if (t.equals(res.getString(R.string.settings))) {
//                d = v.getResources().getDrawable(R.drawable.icon_settings);
//                icon.setImageDrawable(d);
//                return;
//            } else if (t.equals(res.getString(R.string.logout))) {
//                d = v.getResources().getDrawable(R.drawable.icon_logout);
//                icon.setImageDrawable(d);
//                return;
//            } // Extra functionalities in menu
//            else if (t.equals(res.getString(R.string.change_role))) {
//                d = v.getResources().getDrawable(R.drawable.icon_change_role);
//                icon.setImageDrawable(d);
//                return;
//            } else if (t
//                    .equals(res.getString(R.string.change_selected_student))) {
//                d = v.getResources()
//                        .getDrawable(R.drawable.icon_change_student);
//                icon.setImageDrawable(d);
//                return;
//            } else if (t.equals(res.getString(R.string.grades))) {
//                d = v.getResources().getDrawable(R.drawable.icon_grades);
//                icon.setImageDrawable(d);
//                return;
//            } else if (t.equals(res.getString(R.string.evaluations))) {
//                d = v.getResources().getDrawable(R.drawable.icon_evaluations);
//                icon.setImageDrawable(d);
//                return;
//            }
//        }
    }

}

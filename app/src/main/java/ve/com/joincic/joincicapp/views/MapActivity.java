package ve.com.joincic.joincicapp.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ve.com.joincic.joincicapp.R;

/**
 * This class shows a Google Map to locate the event
 *
 * Created by Carla Urrea Stabile on 3/24/15.
 */
public class MapActivity extends ParentActivity {
    public final static String TAG = "MapActivity";
    ImageView mapImageView;
    Resources res;

    static final LatLng JOINCIC_LOCATION = new LatLng(10.410236, -66.882526);
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        res = getResources();

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        Marker location = map.addMarker(new MarkerOptions()
                .position(JOINCIC_LOCATION)
                .title(res.getString(R.string.app_name))
                .snippet(res.getString(R.string.joincic))
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_joincic_logo)));

        // Move the camera instantly to hamburg with a zoom of 15.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(JOINCIC_LOCATION, 17));

        // Zoom in, animating the camera.
//        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected String getActivityName() {
        return getResources().getString(R.string.map);
    }
}

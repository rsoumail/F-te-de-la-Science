package fr.istic.m2il.mmm.fetescience.activities;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.fragments.EventFragment;
import fr.istic.m2il.mmm.fetescience.fragments.EventListFragment;
import fr.istic.m2il.mmm.fetescience.fragments.EventMapFragment;
import fr.istic.m2il.mmm.fetescience.models.Event;

public class EventMapActivity extends AppCompatActivity implements EventMapFragment.OnFragmentInteractionListener, EventFragment.OnEventFragmentInteractionListener {

    private GoogleMap mMap;
    @Nullable
    @BindView(R.id.map_event) FrameLayout frameLayout;
    FragmentManager fragmentManager;
    
    //private MyLocationOverlay myLocation = null;
    private List<Event> events = new ArrayList<>();

    //@OverrideAppCompatActivity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_map);

        fragmentManager = getSupportFragmentManager();

        EventMapFragment eventMapFragment = new EventMapFragment();
        eventMapFragment.setEMA(this);
        eventMapFragment.getMapAsync(eventMapFragment);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.map_event, eventMapFragment);
        fragmentTransaction.addToBackStack("mapEvent");
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    public void onItemSelected(Event item) {
        EventFragment eventFragment = new EventFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.map_event, eventFragment);
        fragmentTransaction.addToBackStack("event_info");
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        //eventFragment.update(item);
    }

    @Override
    public void onEventInteraction(Uri uri) {

    }
}



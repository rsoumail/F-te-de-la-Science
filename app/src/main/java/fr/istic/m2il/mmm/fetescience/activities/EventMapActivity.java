package fr.istic.m2il.mmm.fetescience.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import fr.istic.m2il.mmm.fetescience.Manifest;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.fragments.EventFragment;
import fr.istic.m2il.mmm.fetescience.fragments.EventMapFragment;
import fr.istic.m2il.mmm.fetescience.models.Event;

public class EventMapActivity extends BaseActivity implements EventMapFragment.OnFragmentInteractionListener, EventFragment.OnEventFragmentInteractionListener {

    FragmentManager fragmentManager;
    private List<Event> events;
    private static final String TAG = EventMapActivity.class.getSimpleName();

    //private MyLocationOverlay myLocation = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_map);
        EventMapFragment eventMapFragment = new EventMapFragment();
        Bundle bundle = new Bundle();
        if(getIntent()!= null &&  getIntent().getExtras() != null){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    ){//Can add more as per requirement

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                        123);
            }
            bundle.putBoolean("itinerary", true);
            bundle.putParcelableArrayList("events", getIntent().getExtras().getParcelableArrayList("events"));
            events = getIntent().getExtras().getParcelableArrayList("events");
            for(Event e : events){
                Log.v(TAG, "event's geolocation" + e.getGeolocalisation().toString());
            }
        }
        else {
            bundle.putBoolean("itinerary", false);
        }
        eventMapFragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState != null){
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.map_event, eventMapFragment);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    public void onItemSelected(Event event) {
        EventFragment eventFragment = new EventFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.map_event, eventFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        eventFragment.update(event);
    }

    @Override
    public void onEventInteraction(Event event) {}


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        invalidateOptionsMenu();
        MenuItem itemMap = menu.findItem(R.id.action_map);
        itemMap.setVisible(false);
        MenuItem itemManager = menu.findItem(R.id.action_manager);
        itemManager.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_events:
                Intent eventsIntent = new Intent(EventMapActivity.this, EventActivity.class);
                startActivity(eventsIntent);
                return true;

            case R.id.action_paths:
                Intent pathsIntent = new Intent(EventMapActivity.this, PathActivity.class);
                startActivity(pathsIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



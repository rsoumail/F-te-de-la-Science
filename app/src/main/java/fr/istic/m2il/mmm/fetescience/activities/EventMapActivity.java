package fr.istic.m2il.mmm.fetescience.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;


<<<<<<< HEAD
import java.util.List;
=======
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MeteoconsModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.fonts.TypiconsModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;
>>>>>>> c36a92e10130a0e03a628da5001e1f8999ba95a5

import javax.annotation.Nullable;

import butterknife.BindView;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.fragments.EventFragment;
import fr.istic.m2il.mmm.fetescience.fragments.EventListFragment;
import fr.istic.m2il.mmm.fetescience.fragments.EventMapFragment;
import fr.istic.m2il.mmm.fetescience.models.Event;

public class EventMapActivity extends AppCompatActivity implements EventMapFragment.OnFragmentInteractionListener, EventFragment.OnEventFragmentInteractionListener {

    FragmentManager fragmentManager;
    private List<Event> events;
    
    //private MyLocationOverlay myLocation = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new TypiconsModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new MeteoconsModule())
                .with(new WeathericonsModule())
                .with(new SimpleLineIconsModule())
                .with(new IoniconsModule());

        setContentView(R.layout.activity_event_map);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            events = extras.getParcelableArrayList("events");
        }

        fragmentManager = getSupportFragmentManager();

        EventMapFragment eventMapFragment = new EventMapFragment();
        eventMapFragment.getMapAsync(eventMapFragment);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.map_event, eventMapFragment);
        fragmentTransaction.addToBackStack("event_map");
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    public void onItemSelected(Event event) {
        EventFragment eventFragment = new EventFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.map_event, eventFragment);
        fragmentTransaction.addToBackStack("event_info_from_map");
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        eventFragment.update(event);
    }

    @Override
    public void onEventInteraction(Event event) {}

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_events:
                Intent intent = new Intent(EventMapActivity.this, EventActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



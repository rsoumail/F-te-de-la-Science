package fr.istic.m2il.mmm.fetescience.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;


import javax.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.fragments.EventFragment;
import fr.istic.m2il.mmm.fetescience.fragments.EventListFragment;

import fr.istic.m2il.mmm.fetescience.models.Event;

public class EventActivity extends BaseActivity implements EventListFragment.OnEventListFragmentInteractionListener, EventFragment.OnEventFragmentInteractionListener {

    private static final String TAG = EventActivity.class.getSimpleName();

    private FragmentManager fragmentManager;
    @Nullable @BindView(R.id.event_large) LinearLayout linearLayout;


    private String screenType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        if(linearLayout != null){
            screenType = "large";
            if(findViewById(R.id.large_event_list) != null && findViewById(R.id.large_event_item) != null){
                if(savedInstanceState != null){
                    return;
                }

                EventListFragment eventListFragment = new EventListFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.large_event_list, eventListFragment);
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        }
        else {
            screenType = "normal";
            if(findViewById(R.id.normal_event_list) != null){
                if(savedInstanceState != null){
                    return;
                }
                EventListFragment eventListFragment = new EventListFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.normal_event_list, eventListFragment);
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        }
    }

    @Override
    public void onItemSelected(Event item) {
        EventFragment eventFragment = new EventFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (screenType){
            case "large":
                fragmentTransaction.replace(R.id.large_event_item, eventFragment);
                break;

            case "normal":
                fragmentTransaction.replace(R.id.normal_event_list, eventFragment);
                break;
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        eventFragment.update(item);
    }

    @Override
    public void onEventInteraction(Event event) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        invalidateOptionsMenu();
        MenuItem item = menu.findItem(R.id.action_events);
        item.setVisible(false);
        MenuItem itemManager = menu.findItem(R.id.action_manager);
        itemManager.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_map:
                Intent mapIntent = new Intent(this, EventMapActivity.class);
                startActivity(mapIntent);
                return true;

            case R.id.action_paths:
                Intent pathsIntent = new Intent(this, PathActivity.class);
                startActivity(pathsIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

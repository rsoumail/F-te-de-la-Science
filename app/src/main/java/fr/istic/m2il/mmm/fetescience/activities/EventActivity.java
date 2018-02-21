package fr.istic.m2il.mmm.fetescience.activities;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import javax.annotation.Nullable;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.Optional;
import butterknife.Unbinder;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.fragments.EventFragment;
import fr.istic.m2il.mmm.fetescience.fragments.EventListFragment;
import fr.istic.m2il.mmm.fetescience.models.Event;

public class EventActivity extends AppCompatActivity implements EventListFragment.OnEventListFragmentInteractionListener, EventFragment.OnEventFragmentInteractionListener {

    FragmentManager fragmentManager;
    @Nullable @BindView(R.id.event_large) LinearLayout linearLayout;
    String screenType;


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
                fragmentTransaction.addToBackStack("event_list");
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
                fragmentTransaction.addToBackStack("event_list");
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        }
    }

    @Override
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
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_map:
                Intent intent = new Intent(EventActivity.this, EventMapActivity.class);
                startActivity(intent);
                return true;
            /*case R.id.help:
                showHelp();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
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
        fragmentTransaction.addToBackStack("event_info");
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        eventFragment.update(item);
    }

    @Override
    public void onEventInteraction(Uri uri) {
    }
}

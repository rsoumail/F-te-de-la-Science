package fr.istic.m2il.mmm.fetescience.activities;


import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.LinearLayout;

// à enlever avant de push
import android.content.Intent;

import org.json.JSONException;

import java.io.IOException;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.fragments.EventFragment;
import fr.istic.m2il.mmm.fetescience.fragments.EventListFragment;
import fr.istic.m2il.mmm.fetescience.helpers.DBManagerHelper;
import fr.istic.m2il.mmm.fetescience.helpers.GsonHelper;
import fr.istic.m2il.mmm.fetescience.helpers.PreferencesManagerHelper;
import fr.istic.m2il.mmm.fetescience.models.Event;
import fr.istic.m2il.mmm.fetescience.utils.Utils;

public class EventActivity extends FragmentActivity implements EventListFragment.OnEventListFragmentInteractionListener, EventFragment.OnEventFragmentInteractionListener {

    FragmentManager fragmentManager;
    @Nullable @BindView(R.id.event_large) LinearLayout linearLayout;
    String screenType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);



        fragmentManager = getSupportFragmentManager();

        //linearLayout = findViewById(R.id.event_large);

        if(linearLayout != null){
            screenType = "large";
            if(findViewById(R.id.large_event_list) != null && findViewById(R.id.large_event_item) != null){
                if(savedInstanceState != null){
                    return;
                }

                EventListFragment eventListFragment = new EventListFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.large_event_list, eventListFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                //eventListFragment.onAttach(this);
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
                fragmentTransaction.addToBackStack("normal_event_list");
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
    public void onEventInteraction(Uri uri) {

    }
}
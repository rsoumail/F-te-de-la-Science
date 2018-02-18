package fr.istic.m2il.mmm.fetescience.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

// à enlever avant de push
import android.content.Intent;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.adpaters.EventAdapter;
import fr.istic.m2il.mmm.fetescience.fragments.EventFragment;
import fr.istic.m2il.mmm.fetescience.fragments.EventListFragment;
import fr.istic.m2il.mmm.fetescience.helpers.DBManagerHelper;
import fr.istic.m2il.mmm.fetescience.helpers.GsonHelper;
import fr.istic.m2il.mmm.fetescience.helpers.PreferencesManagerHelper;
import fr.istic.m2il.mmm.fetescience.models.Event;
import fr.istic.m2il.mmm.fetescience.utils.Utils;

import static fr.istic.m2il.mmm.fetescience.helpers.DBManagerHelper.getInstance;

public class EventActivity extends FragmentActivity implements EventListFragment.OnFragmentInteractionListener {


    private FragmentManager fragmentManager;
    private LinearLayout linearLayout;
    private EventAdapter eventAdapter;
    private PreferencesManagerHelper preferencesManagerHelper;
    private String screenType;
    EventFragment eventFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        DBManagerHelper dbManagerHelper = Utils.initDatabase(this);
        preferencesManagerHelper = new PreferencesManagerHelper(this);

        if (preferencesManagerHelper.isFirstTimeLaunch()) {
            try {
                GsonHelper.jsonToSqlite(dbManagerHelper, this);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            preferencesManagerHelper.setFirstTimeLaunchToFalse();
        }

        fragmentManager = getSupportFragmentManager();

        linearLayout = findViewById(R.id.event_large);

        if(linearLayout != null){
            screenType = "large";
            Log.i("SIZE " , screenType);
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
            Log.i("SIZE " , screenType);
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

    // à enelever avant de push
    public void pass2Map(){
        Intent i = new Intent(this, EventMapActivity.class);
        startActivity(i);
    }
}

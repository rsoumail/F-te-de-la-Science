package fr.istic.m2il.mmm.fetescience.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.adpaters.EventAdapter;
import fr.istic.m2il.mmm.fetescience.fragments.EventFragment;
import fr.istic.m2il.mmm.fetescience.fragments.EventListFragment;
import fr.istic.m2il.mmm.fetescience.models.Event;

public class EventActivity extends FragmentActivity implements EventListFragment.OnFragmentInteractionListener {


    private FragmentManager fragmentManager;
    private LinearLayout linearLayout;
    private EventAdapter eventAdapter;
    private String screenType;
    EventFragment eventFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        fragmentManager = getSupportFragmentManager();

        linearLayout = findViewById(R.id.event_large);

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
}

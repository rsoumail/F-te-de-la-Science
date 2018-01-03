package fr.istic.m2il.mmm.fetescience.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.adpater.EventAdapter;
import fr.istic.m2il.mmm.fetescience.model.Event;

/**
 * Created by ismael on 29/12/17.
 */

public class EventListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EventAdapter eventAdapter;
    List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}

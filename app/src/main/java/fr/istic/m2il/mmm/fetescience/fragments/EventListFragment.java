package fr.istic.m2il.mmm.fetescience.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;


import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.adpaters.EventAdapter;
import fr.istic.m2il.mmm.fetescience.helpers.DBManagerHelper;
import fr.istic.m2il.mmm.fetescience.helpers.GsonHelper;
import fr.istic.m2il.mmm.fetescience.helpers.PreferencesManagerHelper;
import fr.istic.m2il.mmm.fetescience.loaders.AsyncEventLoader;
import fr.istic.m2il.mmm.fetescience.models.Event;
import fr.istic.m2il.mmm.fetescience.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * create an instance of this fragment.
 */

public class EventListFragment extends Fragment implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<List<Event>> {

    private static final String TAG = EventListFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> events = new ArrayList<>();
    private List<Event> cachedEvents = new ArrayList<>();
    private List<String> keys = new ArrayList<>();
    private SearchView searchView;
    private Spinner filterSpinner;
    private ProgressBar progressBar;
    private int selectedFilter = 0;
    private String currentQuery;
    private PreferencesManagerHelper preferencesManagerHelper;

    private OnEventListFragmentInteractionListener mListener;

    public EventListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        recyclerView = view.findViewById(R.id.event_recycler_view);
        searchView = view.findViewById(R.id.search_bar);
        progressBar = view.findViewById(R.id.progressBar_cyclic);
        recyclerView.setVisibility(View.GONE);
        filterSpinner = view.findViewById(R.id.key_words_filter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.query_filters, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);
        filterSpinner.setOnItemSelectedListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventAdapter = new EventAdapter(getActivity().getApplication().getApplicationContext(), events);
        eventAdapter.setOnEventClickListener(event -> {
            onItemSelected(event);
        });

        recyclerView.setAdapter(eventAdapter);

        getLoaderManager().initLoader(0, null, this).forceLoad();


        searchView.setOnQueryTextListener(this);
        return view;
    }

    public void onItemSelected(Event item) {
        if (mListener != null) {
            mListener.onItemSelected(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventListFragmentInteractionListener) {
            mListener = (OnEventListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void loadData(){

        /*DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("").child("");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                keys.clear();
                for(DataSnapshot c: dataSnapshot.getChildren()){
                    Log.v("Data ", c.toString());
                    events.add(c.getValue(Event.class));
                    keys.add(c.getKey().toString());
                }
               eventAdapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedFilter = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public Loader<List<Event>> onCreateLoader(int id, Bundle args) {
        return new AsyncEventLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Event>> loader, List<Event> data) {
        eventAdapter.swapData(data);
        cachedEvents.addAll(data);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onLoaderReset(Loader<List<Event>> loader) {
        eventAdapter.swapData(null);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        currentQuery = !TextUtils.isEmpty(query) ? query : null;
        List<Event> filteredEvents = new ArrayList<>();
        if(currentQuery != null){
            currentQuery = currentQuery.toLowerCase();
            switch (selectedFilter){
                case 0:
                    for(Event event: events){
                        if(event.getNom_du_lieu() != null){
                            if(event.getNom_du_lieu().toLowerCase().contains(currentQuery))
                                filteredEvents.add(event);
                        }
                    }
                case 1:
                    for(Event event: events){
                        if(event.getThematiques() != null){
                            if(event.getThematiques().toLowerCase().contains(currentQuery))
                                filteredEvents.add(event);
                        }
                    }
                case 2:
                    for(Event event: events){
                        if(event.getDates() != null){
                            if(event.getDates().toLowerCase().contains(currentQuery))
                                filteredEvents.add(event);
                        }
                    }
                case 3:
                    for(Event event: events){
                        if(event.getMots_cles_fr() != null){
                            if(event.getMots_cles_fr().toLowerCase().contains(currentQuery))
                                filteredEvents.add(event);
                        }
                    }
            }
        }
        else {
            filteredEvents.addAll(cachedEvents);
        }

        eventAdapter.swapData(filteredEvents);
        return true;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnEventListFragmentInteractionListener {
        void onItemSelected(Event item);
    }
}

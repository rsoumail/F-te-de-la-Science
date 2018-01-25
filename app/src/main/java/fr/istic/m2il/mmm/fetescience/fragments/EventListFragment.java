package fr.istic.m2il.mmm.fetescience.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.SearchView;


import java.util.ArrayList;
import java.util.List;

import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.adpaters.EventAdapter;
import fr.istic.m2il.mmm.fetescience.helpers.DBManagerHelper;
import fr.istic.m2il.mmm.fetescience.models.Event;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class EventListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Event>>{

    private static final String TAG = EventListFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> events = new ArrayList<>();
    private List<Event> allEvents = new ArrayList<>();
    private List<String> keys = new ArrayList<>();
    private SearchView searchView;


    private OnEventListFragmentInteractionListener mListener;

    public EventListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        events = DBManagerHelper.getInstance().getAllEvents();
        allEvents.addAll(events);
        Log.i(TAG, "Total events : " + String.valueOf(events.size()));
        recyclerView = view.findViewById(R.id.event_recycler_view);
        searchView = view.findViewById(R.id.search_bar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventAdapter = new EventAdapter(getActivity().getApplication().getApplicationContext(), events);
        eventAdapter.setOnEventClickListener(event -> {
            onItemSelected(event);
        });
        recyclerView.setAdapter(eventAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                final List<Event> filteredEvents = new ArrayList<>();
                Log.i(TAG, "Events Size " + events.size());
                query = query.toLowerCase();
                if(query.length() != 0){
                    for(Event event: events){
                        if(event.getTitre_fr() != null){
                            if(event.getTitre_fr().toLowerCase().contains(query))
                                filteredEvents.add(event);
                        }
                    }
                }
                else{
                    filteredEvents.addAll(allEvents);
                }


                eventAdapter.setFiltered(filteredEvents);
                return true;
            }
        });
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

    @Override
    public Loader<List<Event>> onCreateLoader(int id, Bundle args) {

        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Event>> loader, List<Event> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<Event>> loader) {

    }
}

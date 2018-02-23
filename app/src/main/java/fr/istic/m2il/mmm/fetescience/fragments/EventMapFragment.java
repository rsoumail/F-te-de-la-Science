package fr.istic.m2il.mmm.fetescience.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.activities.EventMapActivity;
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
 * {@link EventMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EventMapFragment extends SupportMapFragment implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<List<Event>> {

    private static final String TAG = EventMapFragment.class.getSimpleName();

    private List<Event> events = new ArrayList<>();
    private List<Event> cachedEvents = new ArrayList<>();
    private List<String> keys = new ArrayList<>();
    private SearchView searchView;
    private Spinner filterSpinner;
    private ProgressBar progressBar;
    private int selectedFilter = 0;
    private String currentQuery;
    private PreferencesManagerHelper preferencesManagerHelper;

    private OnFragmentInteractionListener mListener;
    private GoogleMap mMap;

    public EventMapFragment() {
        // Required empty public constructor
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        getLoaderManager().initLoader(0, null, this).forceLoad();
        return view;
    }

    public void onEventClicked(Event e) {
        if (mListener != null) {
            mListener.onItemSelected(e);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            Log.v("je passe", "ou pas ?");
            mListener = (OnFragmentInteractionListener) context;
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

    @Override
    public Loader<List<Event>> onCreateLoader(int id, Bundle args) {
        return new AsyncEventLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Event>> loader, List<Event> data) {
        events = data;
        addMarkers();
    }

    @Override
    public void onLoaderReset(Loader<List<Event>> loader) {

    }



    @Override
    public void onMapReady(GoogleMap map) {
        // on initialise la carte pour l'utiliser plus tard
        mMap = map;
    }

    public void addMarkers(){

        // init sur Paris
        LatLng pEvent = new LatLng(48.8534,2.3488);

        for (Event event : events){

            /* on récupère la liste de la localistion de l'evt
            et on créé le latlng */
            List<Double> locEvent = event.getGeolocalisation();

            // on vérifie que l'evenement possède une localisation précise
            if(locEvent != null){

                //Log.v("event name est passé",event.getTitre_fr());

                // on récupère la localisation
                pEvent = new LatLng(locEvent.get(0),locEvent.get(1));

                // on créé le nouveau marker
                mMap.addMarker(new MarkerOptions()
                        .title(event.getTitre_fr())
                        .snippet(event.getDescription_fr())
                        .position(pEvent))
                        .setTag(event);

            }
        }

        // on se fixe sur le dernier evt vu
        // à garder pour la fin ?
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pEvent, 6));

        mMap.setOnInfoWindowClickListener((marker) -> {
            marker.hideInfoWindow();
            onEventClicked((Event) marker.getTag());
        });

    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
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
    public interface OnFragmentInteractionListener {
        void onItemSelected(Event item);
    }

}

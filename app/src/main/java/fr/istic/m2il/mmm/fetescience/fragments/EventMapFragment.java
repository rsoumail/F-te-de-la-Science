package fr.istic.m2il.mmm.fetescience.fragments;

import android.content.Context;
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
import com.google.android.gms.maps.model.MarkerOptions;

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
 * {@link EventMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EventMapFragment extends SupportMapFragment implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<List<Event>> {

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
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
    }

    @Override
    public void onLoaderReset(Loader<List<Event>> loader) {
        //eventAdapter.swapData(null);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        mMap = map;

        for (Event event : events){

            // on récupère la liste de la localistion de l'evt
            // et on créé le latlng
            List<Double> locEvent = event.getGeolocalisation();
            LatLng pEvent = new LatLng(locEvent.get(0),locEvent.get(1));

            // on créé le nouveau marker
            mMap.addMarker(new MarkerOptions()
                    .title(event.getTitre_fr())
                    .snippet(event.getDescription_fr())
                    .position(pEvent));
        }

        LatLng sydney = new LatLng(-33.867, 151.206);

        //map.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        Log.v("event info",events.toString());

        mMap.addMarker(new MarkerOptions()
                .title("1")
                .snippet("2")
                .position(sydney));
    }
}

package fr.istic.m2il.mmm.fetescience.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.istic.m2il.mmm.fetescience.map.AsyncTaskMap;
import fr.istic.m2il.mmm.fetescience.map.GMapV2Direction;
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
    private OnFragmentInteractionListener mListener;
    private GoogleMap mMap;
    private boolean itineraire = false;

    public EventMapFragment() {
        // Required empty public constructor
        super();
    }

    public void setItineraire(boolean value){
        itineraire = value;
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
        // cas normal
        // affichage de tous les evenements
        if(!itineraire){
            events = data;
            addMarkers();
        }
        // cas intineraire
        // affichage des éléments de l'itineraire
        else {
            events = ((EventMapActivity) getActivity()).getEvents();
            createRoute();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Event>> loader) {

    }



    @Override
    public void onMapReady(GoogleMap map) {
        // on initialise la carte pour l'utiliser plus tard
        mMap = map;
    }

    /**
     * fonction qui permet d'ajouter les marqueurs
     * sur la totalité des évenements de l'application
     */
    public void addMarkers(){

        // init sur Paris
        LatLng pEvent = new LatLng(48.8534,2.3488);

        for (Event event : events){

            // on ajoute le marker sur la map
            addMarker(event);
        }

        // on se fixe sur le dernier evt vu
        // à garder pour la fin ?
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pEvent, 6));

        mMap.setOnInfoWindowClickListener((marker) -> {
            marker.hideInfoWindow();
            onEventClicked((Event) marker.getTag());
        });

    }

    public void addMarker(Event event){

        // on récupère la liste de la localistion de l'evt
        // et on créé le latlng
        List<Double> locEvent = event.getGeolocalisation();

        // on vérifie que l'evenement possède une localisation précise
        if(locEvent != null){

            // on récupère la localisation
            LatLng pEvent = new LatLng(locEvent.get(0),locEvent.get(1));

            // on créé le nouveau marker
            mMap.addMarker(new MarkerOptions()
                    .title(event.getTitre_fr())
                    .snippet(event.getDescription_fr())
                    .position(pEvent))
                    .setTag(event);
        }
    }

    /**
     * fonction qui permet de créer l'itinéraire à partir
     * d'une liste d'evenements
     */
    public void createRoute(){

        List<Double> geo;
        Event eventPred = null;

        // à chaque event, nous allons créer
        for (Event event : events){

            // création de l'Async task
            AsyncTaskMap laTache = new AsyncTaskMap();

            geo = event.getGeolocalisation();

            if(geo != null){

                // la première fois on utilise notre position
                // les autres fois les events récupérés
                // on récupère le point de départ de l'itinéraire
                if(eventPred != null){
                    List<Double> geo2 = eventPred.getGeolocalisation();
                    LatLng latlng1 = new LatLng(geo2.get(0),geo2.get(1));
                    laTache.setDepart(latlng1);
                }
                else {
                    LatLng latlng1 = new LatLng(48.11198, -1.67429);
                    laTache.setDepart(latlng1);
                }

                // on récupère le point d'arrive de l'itinéraire
                //geo = event.getGeolocalisation();
                LatLng latlng2 = new LatLng(geo.get(0),geo.get(1));
                laTache.setArrive(latlng2);
                addMarker(event);

                eventPred = event;
                PolylineOptions rectLine = null;
                try {
                    rectLine = laTache.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                // on ajoute l'itineraire à la map
                mMap.addPolyline(rectLine);

                // on positionne sur la map
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng2, 6));

            }
        }
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

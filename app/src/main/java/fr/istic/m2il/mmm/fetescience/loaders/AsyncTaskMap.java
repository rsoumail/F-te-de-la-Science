package fr.istic.m2il.mmm.fetescience.loaders;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;

import fr.istic.m2il.mmm.fetescience.services.GMapV2Direction;

/**
 * Created by tmerlet on 23/02/18.
 */

public class AsyncTaskMap extends AsyncTask<LatLng, LatLng, PolylineOptions> {

    private LatLng startPoint;
    private LatLng arrivalPoint;
    private String mode;
    private final String COLOR_ROUTE = "#353d90";
    private final int WIDTH_ROUTE = 10;

    public void setStartPoint(LatLng latLng){
        startPoint = latLng;
    }

    public void setArrivalPoint(LatLng latLng){
        arrivalPoint = latLng;
    }

    public void setMode(String mode){ this.mode = mode;}

    @Override
    protected PolylineOptions doInBackground(LatLng ... params) {

        GMapV2Direction md = new GMapV2Direction();
        Document doc = md.getDocument(startPoint, arrivalPoint,
                this.mode);

        ArrayList<LatLng> directionPoint = md.getDirection(doc);
        PolylineOptions rectLine = new PolylineOptions().width(WIDTH_ROUTE).color(
                Color.parseColor(COLOR_ROUTE));

        for (int i = 0; i < directionPoint.size(); i++) {
            rectLine.add(directionPoint.get(i));
        }

        return rectLine;
    }
}

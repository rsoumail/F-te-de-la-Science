package fr.istic.m2il.mmm.fetescience.map;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Created by tmerlet on 23/02/18.
 */

public class AsyncTaskMap extends AsyncTask<LatLng, LatLng, PolylineOptions> {

    private LatLng startPoint;
    private LatLng arrivalPoint;
    private String mode;

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
        PolylineOptions rectLine = new PolylineOptions().width(3).color(
                Color.RED);

        for (int i = 0; i < directionPoint.size(); i++) {
            rectLine.add(directionPoint.get(i));
        }

        return rectLine;
    }
}

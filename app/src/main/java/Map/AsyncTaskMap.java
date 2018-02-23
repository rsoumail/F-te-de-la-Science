package Map;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Created by tmerlet on 23/02/18.
 */

public class AsyncTaskMap extends AsyncTask<LatLng, LatLng, PolylineOptions> {

    private LatLng depart;
    private LatLng arrive;

    public void setDepart(LatLng latLng){
        depart = latLng;
    }

    public void setArrive(LatLng latLng){
        arrive = latLng;
    }

    @Override
    protected PolylineOptions doInBackground(LatLng ... params) {

        GMapV2Direction md = new GMapV2Direction();
        Document doc = md.getDocument(depart, arrive,
                GMapV2Direction.MODE_DRIVING);

        ArrayList<LatLng> directionPoint = md.getDirection(doc);
        PolylineOptions rectLine = new PolylineOptions().width(3).color(
                Color.RED);

        for (int i = 0; i < directionPoint.size(); i++) {
            rectLine.add(directionPoint.get(i));
        }

        return rectLine;
    }
}

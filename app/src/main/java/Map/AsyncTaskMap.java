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

    @Override
    protected PolylineOptions doInBackground(LatLng ... params) {

        GMapV2Direction md = new GMapV2Direction();
        Document doc = md.getDocument(new LatLng(48.8534,2.3488), new LatLng(48.11198, -1.67429),
                GMapV2Direction.MODE_DRIVING);

        Log.v("pitié",doc.toString());

        ArrayList<LatLng> directionPoint = md.getDirection(doc);
        PolylineOptions rectLine = new PolylineOptions().width(3).color(
                Color.RED);

        for (int i = 0; i < directionPoint.size(); i++) {
            rectLine.add(directionPoint.get(i));
        }

        return rectLine;
    }
}

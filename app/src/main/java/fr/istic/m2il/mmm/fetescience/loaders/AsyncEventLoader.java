package fr.istic.m2il.mmm.fetescience.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.istic.m2il.mmm.fetescience.helpers.DBManagerHelper;
import fr.istic.m2il.mmm.fetescience.models.Event;

/**
 * @author Ramadan Soumaila
 */

public class AsyncEventLoader extends AsyncTaskLoader<List<Event>> {

    private static final String TAG = AsyncEventLoader.class.getSimpleName();

    public AsyncEventLoader(Context context) {
        super(context);
    }

    @Override
    public List<Event> loadInBackground() {
        List<Event> events = DBManagerHelper.getInstance().getAllEvents();
        Log.i(TAG, "All events was loaded successful");
        return events;
    }

}

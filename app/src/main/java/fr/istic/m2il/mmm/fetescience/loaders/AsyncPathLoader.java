package fr.istic.m2il.mmm.fetescience.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.istic.m2il.mmm.fetescience.models.Path;

/**
 * @author Ramadan Soumaila
 */

public class AsyncPathLoader extends AsyncTaskLoader<List<Path>> {

    private static final String TAG = AsyncPathLoader.class.getSimpleName();
    private List<Path> paths = new ArrayList<>();

    public AsyncPathLoader(Context context) {
        super(context);
    }

    @Override
    public List<Path> loadInBackground() {



        if(!paths.isEmpty())
            Log.i(TAG, "Loading paths was loaded successful and not empty");
        else
            Log.i(TAG, "Loading paths was loaded successful but empty");
        return paths;
    }
}

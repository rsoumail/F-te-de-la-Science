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

    private static final String TAG = AsyncTaskLoader.class.getSimpleName();

    public AsyncPathLoader(Context context) {
        super(context);
    }

    @Override
    public List<Path> loadInBackground() {
        List<Path> paths = new ArrayList<>();
        Log.i(TAG, "All paths was loaded successful");
        return paths;
    }
}

package fr.istic.m2il.mmm.fetescience.loaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.util.List;

import fr.istic.m2il.mmm.fetescience.activities.EventActivity;
import fr.istic.m2il.mmm.fetescience.helpers.DBManagerHelper;
import fr.istic.m2il.mmm.fetescience.models.Event;
import fr.istic.m2il.mmm.fetescience.providers.EventContentProvider;
import fr.istic.m2il.mmm.fetescience.adpaters.EventAdapter;

/**
 * Created by waberi on 23/01/18.
 */

public class LoaderManagerAsync extends AsyncTaskLoader<List<Event>> {


    public LoaderManagerAsync(Context context) {
        super(context);
    }

    @Override
    public List<Event> loadInBackground() {
        return null;
    }
}

package fr.istic.m2il.mmm.fetescience.utils;

import android.content.Context;
import android.util.Log;

import fr.istic.m2il.mmm.fetescience.helpers.DBManagerHelper;

/**
 * @author ismael
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    public static DBManagerHelper initDatabase(Context context) {

        DBManagerHelper.init(context);
        DBManagerHelper manager = DBManagerHelper.getInstance();
        Log.i(TAG, "DBManagerHelper Init Successfully");
        return manager;
    }
}

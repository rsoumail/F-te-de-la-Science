package fr.istic.m2il.mmm.fetescience.utils;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.istic.m2il.mmm.fetescience.helpers.DBManagerHelper;
import fr.istic.m2il.mmm.fetescience.models.Event;

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

    public List<Date> parseEventsDaysToJavaDate(Event event){
        List<Date> dates = new ArrayList<>();
        String[] datesString = event.getDates().split(";");
        for(String d: datesString){
            String[] dParts = d.split("-");
            Date date = new Date(Integer.parseInt(dParts[0]), Integer.parseInt(dParts[1]), Integer.parseInt(dParts[2]));
            dates.add(date);
        }
        return dates;
    }
}

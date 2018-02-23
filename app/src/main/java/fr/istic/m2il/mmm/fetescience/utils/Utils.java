package fr.istic.m2il.mmm.fetescience.utils;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.istic.m2il.mmm.fetescience.helpers.DBManagerHelper;
import fr.istic.m2il.mmm.fetescience.models.Event;
import fr.istic.m2il.mmm.fetescience.models.EventDuration;

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

    public static List<EventDuration> parseDates(Event event){
        List<EventDuration> dates = new ArrayList<>();
        String[] datesString = event.getDates().split(";");
        String[] detailsHours = event.getHoraires_detailles_fr().split("\n");
        int index = 0;
        for(String d: datesString){
            String[] dParts = d.split("-");
            Calendar begin = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            String[] detailHour = detailsHours[index].split("-");
            String[] hours = detailHour[1].trim().split("\\u00e0");

            begin.set(Integer.parseInt(dParts[0]), Integer.parseInt(dParts[1]) - 1, Integer.parseInt(dParts[2]), Integer.parseInt(hours[0].trim().split("h")[0]), Integer.parseInt(hours[0].trim().split("h")[1]));
            end.set(Integer.parseInt(dParts[0]), Integer.parseInt(dParts[1]) - 1, Integer.parseInt(dParts[2]), Integer.parseInt(hours[1].trim().split("h")[0]), Integer.parseInt(hours[1].trim().split("h")[1]));
            EventDuration eventDuration = new EventDuration();
            eventDuration.setStart(begin);
            eventDuration.setEnd(end);
            dates.add(eventDuration);
            index++;
        }
        Log.i(TAG, "Dates size " + datesString.length + "Details Hours size " + detailsHours.length);
        return dates;
    }
}

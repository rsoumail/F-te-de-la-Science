package fr.istic.m2il.mmm.fetescience.helpers;

import android.content.Context;

import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.istic.m2il.mmm.fetescience.models.Event;

/**
 * @author Ramadan Soumaila
 */

public class DBManagerHelper {

    private SQLiteHelper  helper;
    private static DBManagerHelper instance;

    public static void init(Context context) {
        if (instance == null) {
            instance = new DBManagerHelper(context);
        }
    }

    public static DBManagerHelper getInstance() {
        return instance;
    }

    public DBManagerHelper(Context context) {
        helper = new SQLiteHelper(context);
    }

    private SQLiteHelper getHelper() {
        return helper;
    }



    public List<Event> getAllEvents() {
        try {
            return getHelper().getEventDAO().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Event>();
        }
    }

    public void createEvent(Event event) {
        try {
            getHelper().getEventDAO().create(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Event findEvent(int id) throws SQLException {
        return getHelper().getEventDAO().queryForId(id);
    }

    public void deleteAllEvents(List<Event> events){
        try {
            getHelper().getEventDAO().delete(events);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(){
        try {
            TableUtils.dropTable(getHelper().getConnectionSource(),Event.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

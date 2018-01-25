package fr.istic.m2il.mmm.fetescience.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import fr.istic.m2il.mmm.fetescience.models.Event;

/**
 * @author Ramadan Soumaila
 */

public class SQLiteHelper  extends OrmLiteSqliteOpenHelper {

    private static final String TAG = SQLiteHelper.class.getSimpleName();

    public SQLiteHelper(Context context) {
        super(context, ConstantsHelper.SQLITE_DATABASE_NAME, null, ConstantsHelper.SQLITE_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Event.class);
            Log.i(TAG, "SQLiteHelper onCreate done successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Event.class, true);
            Log.i(TAG, "SQLiteHelper onUpgrade done successfully");
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(TAG,"SQLiteHelper onUpgrade done from " + oldVersion + " to " + newVersion);
    }

    private Dao<Event, Integer> eventDAO = null;

    public Dao<Event, Integer> getEventDAO() {
        if (eventDAO == null) {
            try {
                eventDAO = getDao(Event.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eventDAO;
    }
}

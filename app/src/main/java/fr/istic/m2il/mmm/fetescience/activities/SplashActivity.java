package fr.istic.m2il.mmm.fetescience.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;

import java.io.IOException;

import fr.istic.m2il.mmm.fetescience.helpers.DBManagerHelper;
import fr.istic.m2il.mmm.fetescience.helpers.GsonHelper;
import fr.istic.m2il.mmm.fetescience.helpers.PreferencesManagerHelper;
import fr.istic.m2il.mmm.fetescience.utils.Utils;


public class SplashActivity extends AppCompatActivity {

    private static final Integer DEFAULT_DELAY = new Integer(1000);
    private PreferencesManagerHelper preferencesManagerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBManagerHelper dbManagerHelper = Utils.initDatabase(this);
        preferencesManagerHelper = new PreferencesManagerHelper(this);
        if (preferencesManagerHelper.isFirstTimeLaunch()) {
            GsonHelper gsonHelper = new GsonHelper();
            try {
                gsonHelper.jsonToSqlite(dbManagerHelper, this);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            preferencesManagerHelper.setFirstTimeLaunchToFalse();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, EventActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, DEFAULT_DELAY);
        }
    }
}

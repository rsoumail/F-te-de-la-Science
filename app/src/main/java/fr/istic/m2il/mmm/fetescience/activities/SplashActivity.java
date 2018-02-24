package fr.istic.m2il.mmm.fetescience.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import java.io.IOException;

import fr.istic.m2il.mmm.fetescience.helpers.DBManagerHelper;
import fr.istic.m2il.mmm.fetescience.helpers.GsonHelper;
import fr.istic.m2il.mmm.fetescience.helpers.PreferencesManagerHelper;
import fr.istic.m2il.mmm.fetescience.utils.Utils;


public class SplashActivity extends Activity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final Integer DEFAULT_DELAY = new Integer(1000);
    private PreferencesManagerHelper preferencesManagerHelper;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
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

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, EventActivity.class);
                startActivity(intent);
                finish();
            }
        }, DEFAULT_DELAY);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() == null){
            firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.i(TAG, "Anonymous user sign in with " + task.getResult().getUser().getUid());
                    }
                }
            });
        }
        else{
            Log.i(TAG, "Anonymous user is loged in with " + firebaseAuth.getCurrentUser().getUid());
        }
    }
}

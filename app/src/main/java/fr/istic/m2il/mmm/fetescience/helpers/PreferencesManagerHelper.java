package fr.istic.m2il.mmm.fetescience.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author ismael
 */

public class PreferencesManagerHelper {

    private SharedPreferences sharedPreferences;

    public PreferencesManagerHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(ConstantsHelper.PREFERENCE_FILE_NAME, ConstantsHelper.PREFERENCE_MODE);
    }


    public void setFirstTimeLaunchToFalse() {
        sharedPreferences.edit().putBoolean(ConstantsHelper.PREFERENCE_KEY_FIRST_LAUNCH, false).apply();
    }


    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(ConstantsHelper.PREFERENCE_KEY_FIRST_LAUNCH, ConstantsHelper.PREFERENCE_DEFAULT_VALUE_FIRST_LAUNCH);
    }


}

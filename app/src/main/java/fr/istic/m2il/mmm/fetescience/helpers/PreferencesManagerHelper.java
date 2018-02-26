package fr.istic.m2il.mmm.fetescience.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;
import java.util.Map;

/**
 * @author Ramadan Soumaila
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

    public void UpdateEventsRating(Integer ratesEventId){
        sharedPreferences.edit().putBoolean(ratesEventId.toString(), true).apply();
    }

    public boolean isEventAlreadyRates(Integer eventId){
        return sharedPreferences.getBoolean(eventId.toString(), ConstantsHelper.PREFERENCE_DEFAULT_VALUE_EVENT_ALREADY_RATES);
    }


}

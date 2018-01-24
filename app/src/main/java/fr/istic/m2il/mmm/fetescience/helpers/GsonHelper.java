package fr.istic.m2il.mmm.fetescience.helpers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.models.Event;


import android.util.Log;

/**
 * @author Ramadan Soumaila
 */

public class GsonHelper {

    private static final String TAG = GsonHelper.class.getSimpleName();

    public static void jsonToSqlite(DBManagerHelper manager, Context context) throws IOException, JSONException {
        Gson gson = new GsonBuilder().create();
        JsonParser jsonParser = new JsonParser();
        StringBuilder sb = new StringBuilder();

        InputStream inputStream = context.getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line, result;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            result = sb.toString();
            JSONArray json = new JSONArray(result);

            for (int i = 0; i < json.length(); i++) {
                JSONObject one = json.getJSONObject(i);
                JSONObject fields = one.getJSONObject("fields");
                JsonElement item = jsonParser.parse(fields.toString());
                Event event = gson.fromJson(item, Event.class);
                Log.i(TAG, "event: " + event);
                manager.createEvent(event);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }




}

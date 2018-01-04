package fr.istic.m2il.mmm.fetescience.providers;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ismael on 03/01/18.
 */

public class EventContentProvider extends ContentProvider {

    static final String AUTHORITY = "eventcontentprovider";
    public static final String PROVIDER_NAME = "eventcontentprovider";
    public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME);

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{""});

        //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("").child("");

        /*databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               *//* events.clear();
                keys.clear();
                for(DataSnapshot c: dataSnapshot.getChildren()){
                    Log.v("data", c.toString());
                    Event event = c.getValue(Test.class).getFields();
                    events.add(event);
                    keys.add(c.getKey().toString());
                }*//*
            }

        });*/
        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return ContentResolver.CURSOR_DIR_BASE_TYPE + '/' + "fr.istic.m2il.mmm.fetescience.events";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

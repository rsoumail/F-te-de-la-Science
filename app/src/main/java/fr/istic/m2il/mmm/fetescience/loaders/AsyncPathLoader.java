package fr.istic.m2il.mmm.fetescience.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fr.istic.m2il.mmm.fetescience.models.Path;

/**
 * @author Ramadan Soumaila
 */

public class AsyncPathLoader extends AsyncTaskLoader<List<Path>> {

    private static final String TAG = AsyncPathLoader.class.getSimpleName();
    private List<Path> paths = new ArrayList<>();

    public AsyncPathLoader(Context context) {
        super(context);
    }

    @Override
    public List<Path> loadInBackground() {

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("paths")){

                    for(DataSnapshot snapshot: dataSnapshot.child("paths").getChildren()){
                        Path path = snapshot.getValue(Path.class);
                        paths.add(path);
                        Log.i(TAG, "Loading paths on data change " + paths.size());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(!paths.isEmpty())
            Log.i(TAG, "Loading paths was loaded successful and not empty");
        else
            Log.i(TAG, "Loading paths was loaded successful but empty");
        return paths;
    }
}

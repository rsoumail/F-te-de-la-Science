package fr.istic.m2il.mmm.fetescience.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.fragments.EventFragment;
import fr.istic.m2il.mmm.fetescience.fragments.PathFragment;
import fr.istic.m2il.mmm.fetescience.fragments.PathListFragment;
import fr.istic.m2il.mmm.fetescience.models.Event;
import fr.istic.m2il.mmm.fetescience.models.Path;

public class PathActivity extends BaseActivity implements PathListFragment.OnPathListFragmentInteractionListener, PathFragment.OnPathFragmentInteractionListener, EventFragment.OnEventFragmentInteractionListener{

    private static final String TAG = PathActivity.class.getSimpleName();

    FragmentManager fragmentManager;
    @Nullable
    @BindView(R.id.path_large) LinearLayout linearLayout;
    String screenType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();

        if(linearLayout != null){
            screenType = "large";
            if(findViewById(R.id.large_path_list) != null && findViewById(R.id.large_path_item) != null){
                if(savedInstanceState != null){
                    return;
                }

                PathListFragment pathListFragment = new PathListFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.large_path_list, pathListFragment);
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        }
        else {
            screenType = "normal";
            if(findViewById(R.id.normal_path_list) != null){
                if(savedInstanceState != null){
                    return;
                }
                PathListFragment pathListFragment = new PathListFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.normal_path_list, pathListFragment);
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();


            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        invalidateOptionsMenu();
        MenuItem itemPath = menu.findItem(R.id.action_paths);
        itemPath.setVisible(false);
        MenuItem itemManager = menu.findItem(R.id.action_manager);
        itemManager.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_events:
                Intent eventsIntent = new Intent(this, EventActivity.class);
                startActivity(eventsIntent);
                return true;

            case R.id.action_map:
                Intent pathsIntent = new Intent(this, EventMapActivity.class);
                startActivity(pathsIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPathSelected(Path path) {
        PathFragment pathFragment = new PathFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (screenType){
            case "large":
                fragmentTransaction.replace(R.id.large_path_item, pathFragment);
                break;

            case "normal":
                fragmentTransaction.replace(R.id.normal_path_list, pathFragment);
                break;
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        pathFragment.update(path);
        Log.i(TAG, "Path Info Loaded ");
    }

    @Override
    public void onPathFragmentUpdate(Path path) {

    }

    @Override
    public void onPathEventSelected(Event event) {
        EventFragment eventFragment = new EventFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (screenType){
            case "large":
                fragmentTransaction.replace(R.id.large_path_item, eventFragment);
                break;

            case "normal":
                fragmentTransaction.replace(R.id.normal_path_list, eventFragment);
                break;
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        eventFragment.update(event);
    }

    @Override
    public void onEventInteraction(Event event) {

    }


}

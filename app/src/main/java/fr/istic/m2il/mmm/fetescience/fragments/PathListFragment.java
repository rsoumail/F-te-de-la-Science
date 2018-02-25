package fr.istic.m2il.mmm.fetescience.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.adpaters.PathAdapter;
import fr.istic.m2il.mmm.fetescience.loaders.AsyncEventLoader;
import fr.istic.m2il.mmm.fetescience.models.Event;
import fr.istic.m2il.mmm.fetescience.models.Path;


public class PathListFragment extends Fragment implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<List<Event>>{

    private static final String TAG = PathListFragment.class.getSimpleName();

    @BindView(R.id.path_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.path_search_bar) SearchView searchView;
    @BindView(R.id.path_progressBar_cyclic) ProgressBar progressBar;
    @BindView(R.id.empty_paths) TextView emptyPathsTextView;

    private Unbinder unbinder;
    private List<Path> paths;
    private PathAdapter pathAdapter;
    private DatabaseReference database;

    private OnPathListFragmentInteractionListener mListener;

    public PathListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        paths = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_path_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pathAdapter = new PathAdapter(getActivity().getApplication().getApplicationContext(), paths);
        pathAdapter.setOnPathClickListener(path -> {
            onPathSelected(path);
        });

        recyclerView.setAdapter(pathAdapter);


        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i(TAG, "Data Changed ");
                if(dataSnapshot.hasChild("paths")){

                    for(DataSnapshot snapshot: dataSnapshot.child("paths").getChildren()){
                        Path path = snapshot.getValue(Path.class);
                        paths.add(path);
                    }
                    progressBar.setVisibility(View.GONE);
                    if(!paths.isEmpty()){
                        recyclerView.setVisibility(View.VISIBLE);
                        pathAdapter.notifyDataSetChanged();
                    } else {
                        emptyPathsTextView.setVisibility(View.VISIBLE);
                    }
                }
                //FirebaseDatabase.
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    public void onPathSelected(Path path) {
        if (mListener != null) {
            mListener.onPathSelected(path);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPathListFragmentInteractionListener) {
            mListener = (OnPathListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public Loader<List<Event>> onCreateLoader(int id, Bundle args) {
        return new AsyncEventLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Event>> loader, List<Event> data) {
        progressBar.setVisibility(View.GONE);
        Log.i(TAG, "Paths data size " + data.size());
        if(!data.isEmpty()){
            //pathAdapter.swapData(data);
            //recyclerView.setVisibility(View.VISIBLE);
        } else {
          //  emptyPathsTextView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Event>> loader) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPathListFragmentInteractionListener {

        void onPathSelected(Path path);
    }
}

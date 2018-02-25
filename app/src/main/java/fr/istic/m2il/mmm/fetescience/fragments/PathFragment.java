package fr.istic.m2il.mmm.fetescience.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.adpaters.EventAdapter;
import fr.istic.m2il.mmm.fetescience.helpers.DBManagerHelper;
import fr.istic.m2il.mmm.fetescience.models.Event;
import fr.istic.m2il.mmm.fetescience.models.Path;

public class PathFragment extends Fragment {


    @BindView(R.id.author) TextView authorTextView;
    @BindView(R.id.comment) TextView commentTextView;
    @BindView(R.id.path_events_recycler_view) RecyclerView pathEventsRecyclerView;

    private Unbinder unbinder;
    private Path path;
    private EventAdapter pathEventAdapter;
    private List<Event> pathEvents = new ArrayList<>();

    private OnPathFragmentInteractionListener mListener;
    private static final String TAG = PathFragment.class.getSimpleName();

    public PathFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_path_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        pathEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pathEventAdapter = new EventAdapter(getActivity().getApplication().getApplicationContext(), pathEvents);

        pathEventAdapter.setShareActivated(false);

        pathEventAdapter.setOnEventClickListener(event -> {
            onPathEventSelected(event);
        });

        pathEventsRecyclerView.setAdapter(pathEventAdapter);
        return view;
    }

    public void onPathEventSelected(Event event){
        if (mListener != null) {
            mListener.onPathEventSelected(event);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPathFragmentInteractionListener) {
            mListener = (OnPathFragmentInteractionListener) context;
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

    public void update(Path path){
        this.path = path;
        authorTextView.setText(path.getAuthor());
        commentTextView.setText(path.getComment());
        for(Integer eventId: this.path.getEvents()){
            try {
                this.pathEvents.add(DBManagerHelper.getInstance().findEvent(eventId));
                Log.i(TAG, "Event with id" + eventId + " found on static DB");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        pathEventAdapter.swapData(this.pathEvents, false);
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
    public interface OnPathFragmentInteractionListener {
        void onPathFragmentUpdate(Path path);

        void onPathEventSelected(Event event);
    }
}

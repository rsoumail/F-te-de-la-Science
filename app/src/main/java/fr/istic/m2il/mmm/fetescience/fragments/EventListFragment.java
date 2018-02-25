package fr.istic.m2il.mmm.fetescience.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.activities.EventMapActivity;
import fr.istic.m2il.mmm.fetescience.adpaters.EventAdapter;
import fr.istic.m2il.mmm.fetescience.loaders.AsyncEventLoader;
import fr.istic.m2il.mmm.fetescience.models.Event;
import fr.istic.m2il.mmm.fetescience.models.Path;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * create an instance of this fragment.
 */

public class EventListFragment extends Fragment implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<List<Event>> {

    private static final String TAG = EventListFragment.class.getSimpleName();

    @BindView(R.id.event_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progressBar_cyclic) ProgressBar progressBar;
    @BindView(R.id.search_bar) SearchView searchView;
    @BindView(R.id.key_words_filter) Spinner filterSpinner;
    @BindView(R.id.options_path_layout_btn) LinearLayout optionsPathLineaLayoutBtn;
    @BindView(R.id.edit_path_btn) IconTextView editPathBtn;
    private Unbinder unbinder;

    private EventAdapter eventAdapter;
    private List<Event> events = new ArrayList<>();
    private List<Event> cachedEvents = new ArrayList<>();
    private int selectedFilter = 0;
    private String currentQuery;

    private Boolean editPathActivated = false;

    private OnEventListFragmentInteractionListener mListener;
    private DatabaseReference database;

    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setVisibility(View.GONE);
        this.editPathActivated = false;
        changeCurrentEventsCheckBoxesState(false);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.query_filters, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);
        filterSpinner.setOnItemSelectedListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventAdapter = new EventAdapter(getActivity().getApplication().getApplicationContext(), events);

        Log.i(TAG, "Edit Path Activated Value " + this.editPathActivated);
        eventAdapter.setShareActivated(this.editPathActivated);

        eventAdapter.setOnEventClickListener(event -> {
            onItemSelected(event);
        });

        recyclerView.setAdapter(eventAdapter);

        if(cachedEvents.isEmpty()){
            getLoaderManager().initLoader(0, null, this).forceLoad();
        }

        searchView.setOnQueryTextListener(this);
        return view;
    }

    public void onItemSelected(Event event) {
        if (mListener != null) {
            mListener.onItemSelected(event);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventListFragmentInteractionListener) {
            mListener = (OnEventListFragmentInteractionListener) context;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "instance Call ");
        if(savedInstanceState != null)
            this.editPathActivated = savedInstanceState.getBoolean("editPathActivated");
        Log.i(TAG, "Edit Path Activated Value " + this.editPathActivated);
        eventAdapter.setShareActivated(this.editPathActivated);
        if(this.editPathActivated){
            for(int i=0; i < recyclerView.getChildCount(); i++){
                if(recyclerView.getChildAt(i).findViewById(R.id.event_checkbox).getVisibility() == View.INVISIBLE)
                    recyclerView.getChildAt(i).findViewById(R.id.event_checkbox).setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(TAG, "In EventListFragment on save instance state ");
        outState.putBoolean("editPathActivated", this.editPathActivated);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedFilter = position;
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
        eventAdapter.swapData(data, true);
        cachedEvents.addAll(data);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onLoaderReset(Loader<List<Event>> loader) {
        eventAdapter.swapData(null, false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        currentQuery = !TextUtils.isEmpty(query) ? query : null;
        List<Event> filteredEvents = new ArrayList<>();
        if(currentQuery != null){
            currentQuery = currentQuery.toLowerCase();
            switch (selectedFilter){
                case 0:
                    for(Event event: events){
                        if(event.getNom_du_lieu() != null){
                            if(event.getNom_du_lieu().toLowerCase().contains(currentQuery))
                                filteredEvents.add(event);
                        }
                    }
                case 1:
                    for(Event event: events){
                        if(event.getThematiques() != null){
                            if(event.getThematiques().toLowerCase().contains(currentQuery))
                                filteredEvents.add(event);
                        }
                    }
                case 2:
                    for(Event event: events){
                        if(event.getDates() != null){
                            if(event.getDates().toLowerCase().contains(currentQuery))
                                filteredEvents.add(event);
                        }
                    }
                case 3:
                    for(Event event: events){
                        if(event.getMots_cles_fr() != null){
                            if(event.getMots_cles_fr().toLowerCase().contains(currentQuery))
                                filteredEvents.add(event);
                        }
                    }
            }
        }
        else {
            events = cachedEvents;
            filteredEvents.addAll(events);
        }

        eventAdapter.swapData(filteredEvents, true);
        return true;
    }

    @OnClick(R.id.share_path_btn)
    public void sharePath(){
        optionsPathLineaLayoutBtn.setVisibility(View.GONE);
        editPathBtn.setVisibility(View.VISIBLE);
        this.editPathActivated = false;

        Path path = new Path();
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.share_paths_dialog);
        dialog.setTitle(R.string.share_path_dialog_title);

        EditText authorEditText = dialog.findViewById(R.id.author_edit);
        EditText commentEditText = dialog.findViewById(R.id.comment_edit);
        Button validateButton = dialog.findViewById(R.id.validate);
        Button cancelButton = dialog.findViewById(R.id.cancel);

        validateButton.setOnClickListener( view -> {
            path.setAuthor(authorEditText.getText().toString().isEmpty() ? "Anonyme" : authorEditText.getText().toString());
            path.setComment(commentEditText.getText().toString());
            dialog.dismiss();
            for(Event event: events){
                if(event.isChecked()){
                    path.getEvents().add(event.getId());
                    event.setChecked(false);
                }
            }
            path.setAuthorId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            String pathKey = database.child("paths").push().getKey();
            database.child("paths").child(pathKey).setValue(path.mapToFireBasePath());
            eventAdapter.setShareActivated(this.editPathActivated);
            changeCurrentEventsCheckBoxesState(false);
            Toast.makeText(this.getActivity(), "Votre parcours à été bien partagé", Toast.LENGTH_SHORT).show();
        });
        cancelButton.setOnClickListener( view -> {
            dialog.dismiss();
            for(Event event: events){
                if(event.isChecked())
                    event.setChecked(false);
                Log.i(TAG, "Event state " + event.isChecked());
            }
            eventAdapter.setShareActivated(this.editPathActivated);
            changeCurrentEventsCheckBoxesState(false);
        });
        dialog.show();
    }

    @OnClick(R.id.itinerary_path_btn)
    public void showPathItinerary(){
        optionsPathLineaLayoutBtn.setVisibility(View.GONE);
        editPathBtn.setVisibility(View.VISIBLE);
        this.editPathActivated = false;
        List<Event> chosedEvents = new ArrayList<>();
        for(Event event: events){
            if(event.isChecked())
                chosedEvents.add(event);
        }
        Intent intent = new Intent(getActivity(), EventMapActivity.class);
        intent.putParcelableArrayListExtra("events", (ArrayList) chosedEvents);
        startActivity(intent);
    }

    @OnClick(R.id.edit_path_btn)
    public void activateEventsCheckBox(){
        editPathBtn.setVisibility(View.GONE);
        this.editPathActivated = true;
        optionsPathLineaLayoutBtn.setVisibility(View.VISIBLE);
        changeCurrentEventsCheckBoxesState(true);
        eventAdapter.setShareActivated(this.editPathActivated);
    }

    private void changeCurrentEventsCheckBoxesState(Boolean activated){
        for(int i=0; i < recyclerView.getChildCount(); i++){
            recyclerView.getChildAt(i).findViewById(R.id.event_checkbox).setVisibility(activated ? View.VISIBLE : View.INVISIBLE);
        }
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
    public interface OnEventListFragmentInteractionListener {
        void onItemSelected(Event item);
    }
}

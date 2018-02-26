package fr.istic.m2il.mmm.fetescience.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joanzapata.iconify.widget.IconTextView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.TimeZone;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.helpers.PreferencesManagerHelper;
import fr.istic.m2il.mmm.fetescience.models.Event;
import fr.istic.m2il.mmm.fetescience.models.EventDuration;
import fr.istic.m2il.mmm.fetescience.utils.Utils;


public class EventFragment extends Fragment {

    private static final String TAG = EventFragment.class.getSimpleName();

    @BindView(R.id.title) TextView titleTexteView;
    @BindView(R.id.theme) TextView themeTexteView;
    @BindView(R.id.description) TextView descriptionTexteView;
    @BindView(R.id.date_debut) TextView dateStartTexteView;
    @BindView(R.id.date_fin) TextView dateEndTexteView;
    @BindView(R.id.adress) TextView addressTextView;
    @BindView(R.id.event_rating_bar) RatingBar eventRatingBar;
    @BindView(R.id.animation) TextView animationTextView;
    @BindView(R.id.image) ImageView imageImageView;
    @BindView(R.id.add_to_agenda_btn) IconTextView agendaButtonView;
    @BindView(R.id.share_btn) IconTextView shareFacebookBtn;
    @BindView(R.id.manager_edit_layout) RelativeLayout managerRelativeLayout;
    @BindView(R.id.available_places_max_text) TextView availablePlaceMaxView;
    @BindView(R.id.fill_places_text) TextView fillPlaceView;
    @BindView(R.id.available_places_max_edit) EditText availablePlaceMaxEditText;
    @BindView(R.id.fill_places_edit) EditText fillPlacesEditText;
    @BindView(R.id.places) TextView placesTextView;
    @BindView(R.id.placesMax) TextView placesMaxTextView;

    private Unbinder unbinder;
    private OnEventFragmentInteractionListener mListener;
    private Event event;
    private DatabaseReference database;
    private PreferencesManagerHelper preferencesManagerHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance().getReference();
        preferencesManagerHelper = new PreferencesManagerHelper(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itemManager = menu.findItem(R.id.action_manager);
        itemManager.setVisible(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        database.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("events")){
                    for (DataSnapshot snapshot : dataSnapshot.child("events").getChildren()){
                        Event fEvent = snapshot.getValue(Event.class);
                        if(event.getId() == fEvent.getId()){
                            eventRatingBar.setRating(fEvent.getRating());
                            placesTextView.setText(fEvent.getFillPlaces().toString());
                            if(fEvent.getMaxAvailablePlaces() != 0)
                                placesMaxTextView.setText(fEvent.getMaxAvailablePlaces().toString());
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_manager:
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.active_manager_dialog);
                dialog.setTitle("Identification de l'organisateur");
                EditText identifiantEditText = dialog.findViewById(R.id.event_identifiant_edit);
                Button validateButton = dialog.findViewById(R.id.validate);
                Button cancelButton = dialog.findViewById(R.id.cancel);
                validateButton.setOnClickListener( view -> {
                    dialog.dismiss();
                    if(event.getIdentifiant().equals(identifiantEditText.getText().toString())){
                        managerRelativeLayout.setVisibility(View.VISIBLE);
                    }
                });
                cancelButton.setOnClickListener(view -> {
                    dialog.dismiss();
                });
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onEventIntercation(Event event) {
        if (mListener != null) {
            mListener.onEventInteraction(event);
        }
    }

    public void update(Event item) {
        this.event = item;
        if (item.getImage() != null) {
            Picasso.with(getContext()).load(item.getImage()).into(imageImageView);
        }

        if (item.getType_d_animation() != null)
            animationTextView.setText(item.getType_d_animation().toUpperCase());

        if (item.getAdresse() != null)
            addressTextView.setText(item.getAdresse());
        if (item.getTitre_fr() != null)
            titleTexteView.setText(item.getTitre_fr());
        if (item.getThematiques() != null)
            themeTexteView.setText(item.getThematiques());
        if (item.getDescription_longue_fr() != null)
            descriptionTexteView.setText(item.getDescription_longue_fr());
        if (item.getDate_debut() != null)
            dateStartTexteView.setText("Du " + item.getDate_debut() + " au");
        if (item.getDate_fin() != null)
            dateEndTexteView.setText(item.getDate_fin());
    }

    @OnClick(R.id.open_browser_btn)
    public void openEventOnBrowser() {
        Intent i = new Intent("android.intent.action.VIEW", Uri.parse(event.getLien()));
        startActivity(i);
    }

    @OnClick(R.id.add_to_agenda_btn)
    public void addAllEventsToCalendar() {
        long calID = 4;
        ContentResolver cr = getActivity().getContentResolver();
        List<EventDuration> dates = Utils.parseDates(this.event);
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR}, 0);
            return;

        } else {
            for(EventDuration d: dates){
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART, d.getStart().getTimeInMillis());
                values.put(CalendarContract.Events.DTEND, d.getEnd().getTimeInMillis());
                values.put(CalendarContract.Events.TITLE, event.getTitre_fr());
                values.put(CalendarContract.Events.ALL_DAY, false);
                values.put(CalendarContract.Events.EVENT_LOCATION, event.getVille());
                values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getDisplayName());
                values.put(CalendarContract.Events.CALENDAR_ID, calID);
                values.put(CalendarContract.Events.DESCRIPTION, event.getDescription_fr());
                cr.insert(CalendarContract.Events.CONTENT_URI, values);
            }
            Toast.makeText(this.getActivity(), "L'événement a bien été ajouté à votre agenda", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.share_btn)
    public void share(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.social_share_dialog);
        dialog.setTitle("Ajouter un commentaire");
        EditText commentEditText = dialog.findViewById(R.id.comment_edit);
        Button validateButton = dialog.findViewById(R.id.validate);
        Button cancelButton = dialog.findViewById(R.id.cancel);
        validateButton.setOnClickListener( view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, event.getLien());
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, commentEditText.getText().toString());
            dialog.dismiss();
            startActivity(Intent.createChooser(intent, "Share"));
        });
        cancelButton.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @OnClick(R.id.manager_update_max_places)
    public void updateMaxPlacesToFirebase(){
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot :dataSnapshot.child("events").getChildren()) {
                    String fEventkey = snapshot.getKey();
                    Event fEvent = snapshot.getValue(Event.class);
                    if(event.getId() == fEvent.getId()){
                        event.setMaxAvailablePlaces(Integer.parseInt(availablePlaceMaxEditText.getText().toString()));
                        database.child("events").child(fEventkey).setValue(event.mapToFireBaseEvent());
                        Log.i(TAG, "Event's available max places With Key " + fEventkey + " and Id " + event.getId() + " Was Updated");
                        Toast.makeText(getActivity(), "Le nombre de place maximum à été mis à jour", Toast.LENGTH_SHORT).show();
                        break ;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.manager_update_fill_places)
    public void udpateFillPlacesToFireBase(){
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot :dataSnapshot.child("events").getChildren()) {
                    String fEventkey = snapshot.getKey();
                    Event fEvent = snapshot.getValue(Event.class);
                    if(event.getId() == fEvent.getId()){
                        event.setFillPlaces(Integer.parseInt(fillPlacesEditText.getText().toString()));
                        database.child("events").child(fEventkey).setValue(event.mapToFireBaseEvent());
                        Log.i(TAG, "Event's fill places With Key " + fEventkey + " and Id " + event.getId() + " Was Updated");
                        Toast.makeText(getActivity(), "Le nombre de personnes présentes a bien été mis à jour", Toast.LENGTH_SHORT).show();
                        break ;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.event_rate_btn)
    public void rate(){
        if(!preferencesManagerHelper.isEventAlreadyRates(event.getId())){
            Float rate = new Float(eventRatingBar.getRating());
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                Boolean eventExistOnFireBase = false;
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot :dataSnapshot.child("events").getChildren()) {
                        String fEventkey = snapshot.getKey();
                        Event fEvent = snapshot.getValue(Event.class);
                        if(event.getId() == fEvent.getId()){
                            event.setRating((rate + fEvent.getRating() * fEvent.getVotersNumber()) / (fEvent.getVotersNumber() + 1));
                            event.setVotersNumber(fEvent.getVotersNumber() + 1);
                            database.child("events").child(fEventkey).setValue(event.mapToFireBaseEvent());
                            eventExistOnFireBase = true;
                            Log.i(TAG, "Event's info With Key " + fEventkey + " and Id " + event.getId() + " Was Updated");
                            break ;
                        }
                    }
                    if(eventExistOnFireBase == false){
                        event.setRating(rate);
                        event.setVotersNumber(1);
                        database.child("events").push().setValue(event.mapToFireBaseEvent());
                        Log.i(TAG, "Event's info with Id " + event.getId() + " Was Added");
                    }
                    eventRatingBar.setRating(event.getRating());
                    Toast.makeText(getActivity(), "Votre vote a bien été pris en compte", Toast.LENGTH_SHORT).show();
                    preferencesManagerHelper.UpdateEventsRating(event.getId());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            Toast.makeText(this.getActivity(), "Vous aviez déjà voter cet évenement", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventFragmentInteractionListener) {
            mListener = (OnEventFragmentInteractionListener) context;
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
    public interface OnEventFragmentInteractionListener {
        // TODO: Update argument type and name
        void onEventInteraction(Event event);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }
}

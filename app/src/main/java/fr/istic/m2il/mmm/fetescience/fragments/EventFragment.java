package fr.istic.m2il.mmm.fetescience.fragments;

import android.Manifest;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joanzapata.iconify.widget.IconTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.models.Event;
import fr.istic.m2il.mmm.fetescience.models.EventDuration;
import fr.istic.m2il.mmm.fetescience.utils.Utils;


public class EventFragment extends Fragment {

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
    @BindView(R.id.share_facebook_btn) IconTextView shareFacebookBtn;


    private Unbinder unbinder;
    private OnEventFragmentInteractionListener mListener;
    private Event event;
    private Event fEvent;

    private static final String TAG = EventFragment.class.getSimpleName();

    DatabaseReference database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance().getReference();
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
            Toast.makeText(this.getActivity(), "Evénement ajouté", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.share_facebook_btn)
    public void openFacebook(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.facebook.com/"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.event_rate_btn)
    public void rate(){
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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

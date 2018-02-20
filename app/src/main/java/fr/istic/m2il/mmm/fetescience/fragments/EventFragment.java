package fr.istic.m2il.mmm.fetescience.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.models.Event;


public class EventFragment extends Fragment {

    @BindView(R.id.title) TextView titleTexteView;
    @BindView(R.id.theme) TextView themeTexteView;
    @BindView(R.id.description) TextView descriptionTexteView;
    @BindView(R.id.date_debut) TextView dateStartTexteView;
    @BindView(R.id.date_fin) TextView dateEndTexteView;
    @BindView(R.id.adress) TextView addressTextView;
    @BindView(R.id.animation) TextView animationTextView;
    @BindView(R.id.hour) TextView hourTextView;
    @BindView(R.id.organisateur) TextView organisatorTextView;
    @BindView(R.id.lien) TextView linkTextView;
    @BindView(R.id.image) ImageView imageImageView;
    @BindView(R.id.agenda) Button agendaButtonView;
    @BindView(R.id.imageButton) ImageButton imageButton;
    private Unbinder unbinder;
    private OnEventFragmentInteractionListener mListener;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onEventIntercation(Uri uri) {
        if (mListener != null) {
            mListener.onEventInteraction(uri);
        }
    }

    public void update(Event item){
        if(item.getImage() != null){
            Picasso.with(getContext()).load(item.getImage()).into(imageImageView);
        }

        if(item.getOrganisateur() != null)
            organisatorTextView.setText(item.getOrganisateur());
        if(item.getType_d_animation() != null)
            animationTextView.setText(item.getType_d_animation().toUpperCase());
        if(item.getHoraires_detailles_fr() != null)
            hourTextView.setText(item.getHoraires_detailles_fr());
        if(item.getAdresse() != null)
            addressTextView.setText(item.getAdresse());
        if(item.getTitre_fr() != null)
            titleTexteView.setText(item.getTitre_fr());
        if(item.getThematiques() != null)
            themeTexteView.setText(item.getThematiques());
        if(item.getDescription_longue_fr() != null)
            descriptionTexteView.setText(item.getDescription_longue_fr());
        if(item.getDate_debut() != null)
            dateStartTexteView.setText("Du " + item.getDate_debut() +" au");
        if(item.getDate_fin() != null)
            dateEndTexteView.setText(item.getDate_fin());
        if(item.getLien() != null)
            linkTextView.setText(item.getLien());
        //if(item.getDates() != null)
          //  linkTextView.setText(item.getDates());

    }

    @OnClick(R.id.lien)
    public void openBrowser(View view){
        Intent i  = new Intent("android.intent.action.VIEW", Uri.parse(linkTextView.getText().toString()));
        startActivity(i);
    }


    @OnClick(R.id.agenda)
    public void openSaveInAgenda(){
        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setData(CalendarContract.Events.CONTENT_URI);
        startActivity(calIntent);
    }


    @OnClick(R.id.imageButton)
    public void openFacebook(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.facebook.com/"));
        startActivity(browserIntent);
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
        void onEventInteraction(Uri uri);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }
}

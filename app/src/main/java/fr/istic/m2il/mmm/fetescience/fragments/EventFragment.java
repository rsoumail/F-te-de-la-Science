package fr.istic.m2il.mmm.fetescience.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.models.Event;


public class EventFragment extends Fragment {

    private TextView titleTexteView, themeTexteView, descriptionTexteView, dateDebutTexteView, dateFinTexteView,
            adressTextView, animationTextView, hourTextView, organisateurTextView;
    private ImageView imageImageView;
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
        adressTextView = view.findViewById(R.id.adress);
        organisateurTextView = view.findViewById(R.id.organisateur);
        animationTextView = view.findViewById(R.id.animation);
        hourTextView = view.findViewById(R.id.hour);
        imageImageView = view.findViewById(R.id.image);
        titleTexteView = view.findViewById(R.id.title);
        themeTexteView = view.findViewById(R.id.theme);
        descriptionTexteView = view.findViewById(R.id.description);
        dateDebutTexteView = view.findViewById(R.id.date_debut);
        dateFinTexteView = view.findViewById(R.id.date_fin);
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
            organisateurTextView.setText(item.getOrganisateur());
        if(item.getType_d_animation() != null)
            animationTextView.setText(item.getType_d_animation().toUpperCase());
        if(item.getHoraires_detailles_fr() != null)
            hourTextView.setText(item.getHoraires_detailles_fr());
        if(item.getAdresse() != null)
            adressTextView.setText(item.getAdresse());
        if(item.getTitre_fr() != null)
            titleTexteView.setText(item.getTitre_fr());
        if(item.getThematiques() != null)
            themeTexteView.setText(item.getThematiques());
        if(item.getDescription_longue_fr() != null)
            descriptionTexteView.setText(item.getDescription_longue_fr());
        if(item.getDate_debut() != null)
            dateDebutTexteView.setText("Du " + item.getDate_debut() +" au");
        if(item.getDate_fin() != null)
            dateFinTexteView.setText(item.getDate_fin());
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
}

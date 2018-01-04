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

    private TextView titleTexteView, themeTexteView, descriptionTexteView, dateDebutTexteView, dateFinTexteView;
    private ImageView imageView;
    private OnEventInteractionListener mListener;

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
        View view = inflater.inflate(R.layout.event_info_layout, container, false);
        imageView = view.findViewById(R.id.imageView);
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
        if(item.getFields().getApercu() != null){
            Picasso.with(getContext()).load(item.getFields().getApercu()).into(imageView);
        }

        titleTexteView.setText(item.getFields().getTitre_fr());
        themeTexteView.setText(item.getFields().getThematiques());
        descriptionTexteView.setText(item.getFields().getDescription_fr());
        dateDebutTexteView.setText(item.getFields().getDate_debut());
        dateFinTexteView.setText(item.getFields().getDate_fin());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventInteractionListener) {
            mListener = (OnEventInteractionListener) context;
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
    public interface OnEventInteractionListener {
        // TODO: Update argument type and name
        void onEventInteraction(Uri uri);
    }
}

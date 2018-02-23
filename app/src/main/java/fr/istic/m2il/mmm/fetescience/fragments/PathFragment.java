package fr.istic.m2il.mmm.fetescience.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.models.Path;

public class PathFragment extends Fragment {


    @BindView(R.id.author) TextView authorTextView;
    @BindView(R.id.comment) TextView commentTextView;

    private OnPathFragmentInteractionListener mListener;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_path_info, container, false);
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
        authorTextView.setText(path.getAuthor());
        commentTextView.setText(path.getComment());
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
    }
}

package fr.istic.m2il.mmm.fetescience.adpaters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.listeners.OnPathClickListener;
import fr.istic.m2il.mmm.fetescience.models.Path;

/**
 * @author Ramadan Soumaila
 */

public class PathAdapter extends RecyclerView.Adapter<PathAdapter.PathViewHolder>{

    private static final String TAG = PathAdapter.class.getSimpleName();

    private Context context;
    private List<Path> paths;

    public void setOnPathClickListener(OnPathClickListener onPathClickListener) {
        this.onPathClickListener = onPathClickListener;
    }

    private OnPathClickListener onPathClickListener;


    public PathAdapter(Context context, List<Path> paths) {
        this.context = context;
        this.paths = paths;
    }

    @Override
    public PathViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.path_item_layout, parent, false);
        return new PathViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PathViewHolder holder, int position) {
        Path path = paths.get(position);

        holder.authorTextView.setText(path.getAuthor());
        holder.commentTextView.setText(path.getComment().length() >= 50 ? path.getComment().substring(0,49) + " ..." : path.getComment());
       // holder.eventsNumberTextView.setText(path.getEvents().size());
        holder.bind(path, onPathClickListener);
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    public void swapData(List<Path> data){
        paths.clear(); // add this so that it will clear old data
        if(data != null)
            paths.addAll(data);
        notifyDataSetChanged();
    }

    class PathViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.path_cardview_container) CardView cardView;
        @BindView(R.id.author) TextView authorTextView;
        @BindView(R.id.comment) TextView commentTextView;
        @BindView(R.id.events_number) TextView eventsNumberTextView;

        public PathViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Path path, final OnPathClickListener onPathClickListener){
            cardView.setOnClickListener(view -> {
                onPathClickListener.onPathClick(path);
            });
        }
    }
}

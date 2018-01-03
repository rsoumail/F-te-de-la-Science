package fr.istic.m2il.mmm.fetescience.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.model.Event;

/**
 * Created by ismael on 30/12/17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private List<Event> events;

    public EventAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.event_item, null);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.titleTextView.setText(event.getTitre_fr());
        holder.descriptionTextView.setText(event.getDescription_fr());
        holder.themeTextView.setText(event.getThematiques());
        holder.dateTextView.setText("Du " + event.getDate_debut() + " au " + event.getDate_fin());
        holder.placeTextView.setText(event.getAdresse());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView, descriptionTextView, themeTextView, dateTextView, placeTextView;

        public EventViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            descriptionTextView = itemView.findViewById(R.id.description);
            themeTextView = itemView.findViewById(R.id.theme);
            dateTextView = itemView.findViewById(R.id.date);
            placeTextView = itemView.findViewById(R.id.place);
        }
    }
}

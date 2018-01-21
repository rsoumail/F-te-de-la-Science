package fr.istic.m2il.mmm.fetescience.adpaters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.models.Event;

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
        View view = layoutInflater.inflate(R.layout.event_item_layout, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.titleTextView.setText(event.getTitre_fr().length() >= 35 ? event.getTitre_fr().substring(0,34) + " ..." : event.getTitre_fr());
        holder.descriptionTextView.setText(event.getDescription_fr().length() >= 50 ? event.getDescription_fr().substring(0,49) + " ..." : event.getDescription_fr());
        if(event.getThematiques() != null){
            holder.themeTextView.setText(event.getThematiques().length() >= 40 ? event.getThematiques().substring(0,39) + " ..." : event.getThematiques());
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView titleTextView, descriptionTextView, themeTextView;

        public EventViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.event_cardview_container);
            titleTextView = itemView.findViewById(R.id.title);
            descriptionTextView = itemView.findViewById(R.id.description);
            themeTextView = itemView.findViewById(R.id.theme);
        }
    }
}

package fr.istic.m2il.mmm.fetescience.adpaters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import fr.istic.m2il.mmm.fetescience.R;
import fr.istic.m2il.mmm.fetescience.listeners.OnEventClickListener;
import fr.istic.m2il.mmm.fetescience.models.Event;

/**
 * @author Ramadan Soumaila
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private static final String TAG = EventAdapter.class.getSimpleName();

    private Context context;
    private List<Event> events;
    private List<Boolean> checkboxsChecked = new ArrayList<>();
    private List<Event> eventsCheked = new ArrayList<>();
    private OnEventClickListener onEventClickListener;
    private Boolean shareActivated = false;

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

        if(event.getApercu() != null){
            Picasso.with(this.context).load(event.getApercu()).into(holder.apercuImageView);
        }
        holder.titleTextView.setText(event.getTitre_fr().length() >= 35 ? event.getTitre_fr().substring(0,34) + " ..." : event.getTitre_fr());
        holder.descriptionTextView.setText(event.getDescription_fr().length() >= 50 ? event.getDescription_fr().substring(0,49) + " ..." : event.getDescription_fr());
        if(event.getThematiques() != null){
            holder.themeTextView.setText(event.getThematiques().length() >= 40 ? event.getThematiques().substring(0,39) + " ..." : event.getThematiques());
        }

        holder.eventCheckBox.setChecked(event.getChecked());
        holder.bind(event, onEventClickListener);


    }

    @Override
    public int getItemCount() {
        return events.size();
    }



    public void setOnEventClickListener(OnEventClickListener onEventClickListener) {
        this.onEventClickListener = onEventClickListener;
    }

    public void swapData(List<Event> data){
        events.clear(); // add this so that it will clear old data
        if(data != null)
            events.addAll(data);
        notifyDataSetChanged();
    }

    public void setShareActivated(Boolean shareActivated) {
        this.shareActivated = shareActivated;
    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.event_cardview_container)CardView cardView;
        @BindView(R.id.title) TextView titleTextView;
        @BindView(R.id.description) TextView descriptionTextView;
        @BindView(R.id.theme) TextView themeTextView;
        @BindView(R.id.apercu) ImageView apercuImageView;
        @BindView(R.id.event_checkbox) CheckBox eventCheckBox;

        public EventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if(EventAdapter.this.shareActivated && eventCheckBox.getVisibility() != View.VISIBLE)
                eventCheckBox.setVisibility(View.VISIBLE);
        }

        public void bind(final Event event, final OnEventClickListener onEventClickListener){
            cardView.setOnClickListener((view) -> {
                onEventClickListener.onEventClick(event);
            });

            eventCheckBox.setOnCheckedChangeListener( (buttonView, isCheched) -> {
                Log.i(TAG, "Event Checkbox Value " + isCheched);
                event.setChecked(isCheched);
            });

        }



    }
}

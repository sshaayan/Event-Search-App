package com.example.hw9;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private List<EventsList> eventsLists;
    private Context context;

    public EventsAdapter(List<EventsList> eventsLists, Context context) {
        this.eventsLists = eventsLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventsAdapter.ViewHolder holder, final int position) {
        final EventsList eventsList = eventsLists.get(position);
        LinearLayout.LayoutParams normalLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        holder.itemView.setLayoutParams(normalLayout);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsList currEventList = eventsLists.get(position);
                Intent seeDetails = new Intent(v.getContext(), EventDetails.class);
                seeDetails.putExtra("allDetails", currEventList.getDetails().toString());
                v.getContext().startActivity(seeDetails);
            }
        });

        String category = eventsList.getCategory().split("\\s+")[0];
        Drawable drawable;
        if (category.equals("Music")) {
            drawable = context.getResources().getDrawable(R.drawable.music_icon);
            holder.eventIcon.setImageDrawable(drawable);
        }
        else if (category.equals("Sports")) {
            drawable = context.getResources().getDrawable(R.drawable.ic_sport_icon);
            holder.eventIcon.setImageDrawable(drawable);
        }
        else if (category.equals("Arts")) {
            drawable = context.getResources().getDrawable(R.drawable.art_icon);
            holder.eventIcon.setImageDrawable(drawable);
        }
        else if (category.equals("Film")) {
            drawable = context.getResources().getDrawable(R.drawable.film_icon);
            holder.eventIcon.setImageDrawable(drawable);
        }
        holder.eventName.setText(eventsList.getName());
        holder.eventVenue.setText(eventsList.getVenue());
        holder.eventDate.setText(eventsList.getDate());
        if (eventsList.getFavourited()) {
            drawable = context.getResources().getDrawable(R.drawable.heart_fill_red);
            holder.eventFaved.setImageDrawable(drawable);
        }
    }

    @Override
    public int getItemCount() { return eventsLists.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView eventIcon;
        public TextView eventName;
        public TextView eventVenue;
        public TextView eventDate;
        public ImageView eventFaved;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            eventIcon = (ImageView) itemView.findViewById(R.id.eventIcon);
            eventName = (TextView) itemView.findViewById(R.id.eventName);
            eventVenue = (TextView) itemView.findViewById(R.id.venueName);
            eventDate = (TextView) itemView.findViewById(R.id.dateInfo);
            eventFaved = (ImageView) itemView.findViewById(R.id.heartStatus);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linLayout);
        }
    }

}

package com.avadhesh.firedemotask.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avadhesh.firedemotask.NodeFirebase;
import com.avadhesh.firedemotask.PostEvent.EventEditDelete;
import com.avadhesh.firedemotask.R;
import com.avadhesh.firedemotask.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.ViewHolder> {

    private Activity context;
    private ArrayList<EventData> feeds;
    public AdapterEvent(Activity context1,
                       ArrayList<EventData> feeds1
    ) {
        this.feeds = feeds1;
        this.context = context1;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name, txt_msg, txt_time,txt_date,people,view;


        ViewHolder(View v) {

            super(v);

            txt_name = v.findViewById(R.id.name);
            txt_time = v.findViewById(R.id.timestamp);
            txt_msg = v.findViewById(R.id.txtStatusMsg);
            txt_date = v.findViewById(R.id.eventDate);
            people= v.findViewById(R.id.people);
            view = v.findViewById(R.id.view);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view1 = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);

        return new ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // where recycler view was updating the items on scroll.
        holder.setIsRecyclable(false);
        holder.txt_name.setText("Event Title: "+feeds.get(position).getTitle() );
        holder.txt_msg.setText("Event Agenda : "+feeds.get(position).getDescription());
        String formatted = Utility.getFormattedDate(feeds.get(position).getDate());
        holder.txt_time.setText("Event Crated time : "+formatted);
        holder.txt_date.setText("Event Date: "+feeds.get(position).getEventDate());
        holder.people.setText("Participant: "+feeds.get(position).getParticipants());

        holder.view.setTag(position);
        holder.view.setOnClickListener(v -> {
            int pos= (int) v.getTag();
            EventData eventData = feeds.get(pos);
            Intent intent = new Intent(context, EventEditDelete.class);
            intent.putExtra("event",eventData);
            intent.putExtra("key",eventData);
            context.startActivity(intent);
            context.finish();
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

}
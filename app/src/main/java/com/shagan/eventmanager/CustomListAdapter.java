package com.shagan.eventmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shagan.eventmanager.gurpreet.UpdateActivity;
import com.shagan.eventmanager.gurpreet.viewUpcomingActivity;

import java.util.ArrayList;


public class CustomListAdapter extends BaseAdapter {
    Context context;
    ArrayList<DataClass> data;
    LayoutInflater inflator;

    public CustomListAdapter(Context context, ArrayList<DataClass> data) {
        this.context = context;
        this.data = data;

        inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i).title;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflator.inflate(R.layout.list_item_view_all, null);
            holder = new ViewHolder();
            holder.editBT = (ImageButton) view.findViewById(R.id.editBT);
            holder.locateBT = (ImageButton) view.findViewById(R.id.locateBT);
            holder.titleTV = (TextView) view.findViewById(R.id.titleTV);
            holder.desciptionTV = (TextView) view.findViewById(R.id.descriptionTV);
            holder.timeTV = (TextView) view.findViewById(R.id.timeTV);
            holder.venueTV = (TextView) view.findViewById(R.id.venueTV);
            holder.dateTV = (TextView) view.findViewById(R.id.dateTV);
            holder.locateTV = (TextView) view.findViewById(R.id.locateTV);


            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();

        }

        holder.titleTV.setText(data.get(i).title);
        holder.desciptionTV.setText(data.get(i).description);
        holder.timeTV.setText(data.get(i).time);
        if(data.get(i).venue.contains("Not Set"))
        {
            holder.venueTV.setText("Not Set");
            holder.locateBT.setVisibility(View.INVISIBLE);
            holder.locateTV.setVisibility(View.INVISIBLE);

        }else{
            holder.venueTV.setText(data.get(i).venue);
            holder.locateBT.setVisibility(View.VISIBLE);
            holder.locateTV.setVisibility(View.VISIBLE);

        }

        holder.dateTV.setText(data.get(i).date);
        holder.editBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", data.get(i).id);
                context.startActivity(intent);
                viewUpcomingActivity activity = (viewUpcomingActivity) context;
                activity.finish();

            }
        });

        holder.locateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LocateActivity.class);
                intent.putExtra("venue", data.get(i).venue);
                intent.putExtra("lat", data.get(i).lat);
                intent.putExtra("long", data.get(i).longitude);
                intent.putExtra("address", data.get(i).address);
                context.startActivity(intent);
                viewUpcomingActivity activity = (viewUpcomingActivity) context;
                activity.finish();
            }
        });
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return data.size();
    }
}

class ViewHolder {

    TextView titleTV, desciptionTV, timeTV, venueTV, dateTV , locateTV;
    ImageButton editBT, locateBT;
}

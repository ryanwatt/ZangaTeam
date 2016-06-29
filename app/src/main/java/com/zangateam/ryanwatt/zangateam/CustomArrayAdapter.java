package com.zangateam.ryanwatt.zangateam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by nickzarate on 6/22/16.
 */
public class CustomArrayAdapter extends ArrayAdapter<Event> {
    private ArrayList<Event> list;

    public CustomArrayAdapter(Context context, int textViewResourceId, ArrayList<Event> eventList)
    {
        //populate the local list with data.
        super(context, textViewResourceId, eventList);
        this.list = new ArrayList<Event>();
        this.list.addAll(eventList);
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        //creating the ViewHolder we defined earlier.
        ViewHolder holder = new ViewHolder();

        //creating LayoutInflator for inflating the row layout.
        LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflating the row layout we defined earlier.
        convertView = inflator.inflate(R.layout.event_item, null);

        //setting the views into the ViewHolder.
        holder.title = (TextView) convertView.findViewById(R.id.tvItemTitle);
        holder.changeRowStatus = (ImageView) convertView.findViewById(R.id.iStatus);
        holder.changeRowStatus.setTag(position);

        //define an onClickListener for the ImageView.
        holder.changeRowStatus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(activity, "Image from row " + position + " was pressed", Toast.LENGTH_LONG).show();
            }
        });
        holder.checked = (CheckBox) convertView.findViewById(R.id.cbCheckListItem);
        holder.checked.setTag(position);

        //define an onClickListener for the CheckBox.
        holder.checked.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //assign check-box state to the corresponding object in list.
                CheckBox checkbox = (CheckBox) v;
                rowDataList.get(position).setChecked(checkbox.isChecked());
                Toast.makeText(activity, "CheckBox from row " + position + " was checked", Toast.LENGTH_LONG).show();
            }
        });

        //setting data into the the ViewHolder.
        holder.title.setText(rowDataList.get(position).getName());
        holder.checked.setChecked(rowDataList.get(position).isChecked());

        //return the row view.
        return convertView;
    }

    public static class ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView date;
    }

}

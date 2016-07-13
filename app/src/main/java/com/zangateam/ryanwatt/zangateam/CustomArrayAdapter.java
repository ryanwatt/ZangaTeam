package com.zangateam.ryanwatt.zangateam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by nickzarate on 6/22/16.
 */
public class CustomArrayAdapter extends ArrayAdapter<Event> {
    private ArrayList<Event> list;
    private RetrieveImageBitmap bitmap;

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
        holder.title = (TextView) convertView.findViewById(R.id.event_title);
        holder.image = (ImageView) convertView.findViewById(R.id.event_image);
        holder.date = (TextView) convertView.findViewById(R.id.event_date);

        //setting data into the the ViewHolder.
//        holder.image.setImageBitmap(bitmap);
        holder.title.setText(list.get(position).getTitle());
        holder.date.setText(list.get(position).getDate());

        //return the row view.
        return convertView;
    }

    public static class ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView date;
    }

}

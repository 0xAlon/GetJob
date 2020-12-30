package com.team3.getjob;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

<<<<<<< HEAD
=======

// This class put your row into your list!!!!!!!!!!

>>>>>>> origin/ArtiomHackathon
public class jobs_adapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<job_model> mDataSource;


    public jobs_adapter(Context context, ArrayList<job_model> items)
    {
        mDataSource = items;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mDataSource.size();
    }

<<<<<<< HEAD

=======
>>>>>>> origin/ArtiomHackathon
    @Override
    public job_model getItem(int position) {
        return mDataSource.get(position);
    }

<<<<<<< HEAD

=======
>>>>>>> origin/ArtiomHackathon
    @Override
    public long getItemId(int position) {
        return position;
    }


<<<<<<< HEAD
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View rowItem = mInflater.inflate(R.layout.job_row, parent, false);
        TextView title =(TextView) rowItem.findViewById(R.id.job_title);
        TextView description =(TextView) rowItem.findViewById(R.id.job_description);
        TextView location =(TextView) rowItem.findViewById(R.id.job_location);
        TextView date =(TextView) rowItem.findViewById(R.id.job_date);


        job_model jobs = getItem(position);

        title.setText(jobs.getTitle());
        description.setText(jobs.getDescription());
        location.setText(jobs.getLocation());
        date.setText(jobs.getDate().toString());


=======
// If ypu want changes how your row looks and and what fiels its here!!!!!!!
//Change id for yours !!!!!
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View rowItem = mInflater.inflate(R.layout.job_row_profile, parent, false);
        TextView title =(TextView) rowItem.findViewById(R.id.job_title);
        TextView date =(TextView) rowItem.findViewById(R.id.job_date);
        job_model jobs = getItem(position);//this one should stay always!!!!

        title.setText(jobs.getTitle());
        date.setText(jobs.getDate().toString());

>>>>>>> origin/ArtiomHackathon
        return rowItem;

    }


}

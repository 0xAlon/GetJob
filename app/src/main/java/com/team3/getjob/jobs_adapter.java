package com.team3.getjob;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


// This class put your row into your list!!!!!!!!!!

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

    @Override
    public job_model getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


// If ypu want changes how your row looks and and what fiels its here!!!!!!!
//Change id for yours !!!!!
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View rowItem = mInflater.inflate(R.layout.job_row, parent, false);
        TextView title =(TextView) rowItem.findViewById(R.id.job_title);
        TextView date =(TextView) rowItem.findViewById(R.id.job_date);
        job_model jobs = getItem(position);//this one should stay always!!!!

        title.setText(jobs.getTitle());
        date.setText(jobs.getDate().toString());

        return rowItem;

    }


}

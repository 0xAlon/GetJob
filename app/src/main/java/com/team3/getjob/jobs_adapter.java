package com.team3.getjob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowItem = mInflater.inflate(R.layout.job_row, parent, false);
        TextView title =(TextView) rowItem.findViewById(R.id.job_title);
        TextView description =(TextView) rowItem.findViewById(R.id.job_description);
        TextView location =(TextView) rowItem.findViewById(R.id.job_location);

        job_model jobs = getItem(position);

        title.setText(jobs.getTitle());
        description.setText(jobs.getDescription());
        location.setText(jobs.getLocation());


        return rowItem;

    }


}

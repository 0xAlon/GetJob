package com.team3.getjob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import static com.team3.getjob.R.id.checkBox;
import static com.team3.getjob.R.id.checkBox6;
import static com.team3.getjob.R.id.checkBox7;
import static com.team3.getjob.R.id.checkBox8;
import static com.team3.getjob.R.id.checkBox9;

public class rank_filter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_filter);
        //option for cancel location filter and came back for filter menu
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rank_filter.this, Filter.class);
                startActivity(intent);
            }
        });
        //--------------------------------------------------------------------------
    }

    public void click(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        if (view.getId() == checkBox) {
            if(checked)
                Log.d("tag","Hi!");
        }
        else if(view.getId() == checkBox6) {
            if(checked)


        else if(view.getId() == checkBox7) {
            if(checked)

        }
        else if(view.getId() == checkBox8) {
            if(checked)

        }
        else if(view.getId() == checkBox9) {
            if(checked)

        }
    }

}


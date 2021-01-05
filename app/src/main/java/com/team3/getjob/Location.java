package com.team3.getjob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class Location extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        CheckBox ageF=findViewById(R.id.ageFilter);
        ageF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ageF.isChecked()) {
                    if (!Filter_model.ageAdult) {
                        Filter_model.ageAdult=true;
                    }

                }
                else{
                    if (Filter_model.ageAdult) {
                        Filter_model.ageAdult=false;
                    }

                }
            }
        });
        //option for cancel location filter and came back for filter menu
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Location.this, Filter.class);
                startActivity(intent);
            }
        });

        //Apply ageFilter
        Button apply = (Button) findViewById(R.id.applyChanges);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFilter = new Intent(Location.this, Filter.class);
                startActivity(intentFilter);
            }
        });
        //---------------------------------------------------------------------------
    }

    }

package com.team3.getjob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

public class rank_filter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_filter);
        CheckBox check3 = findViewById(R.id.checkBox7);
        CheckBox check4 = findViewById(R.id.checkBox8);
        CheckBox check5 = findViewById(R.id.checkBox9);

        //Add class implementation


        check3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check3.isChecked()) {
                    if (!Filter_model.s_ranks.contains("3")) {
                        Filter_model.s_ranks.add("3");
                    }

                }
                else{
                    if (Filter_model.s_ranks.contains("3")) {
                        Filter_model.s_ranks.remove("3");
                    }
                }
            }
        });
        check4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check4.isChecked()){
                    if (!Filter_model.s_ranks.contains("4")) {
                        Filter_model.s_ranks.add("4");
                    }
            }
                else
                    if (Filter_model.s_ranks.contains("4")) {
                        Filter_model.s_ranks.remove("4");
                    }
            }
        });
        check5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(check5.isChecked()) {
                   if (!Filter_model.s_ranks.contains("5")) {
                       Filter_model.s_ranks.add("5");
                   }
               }
                else {
                   if (Filter_model.s_ranks.contains("5")) {
                       Filter_model.s_ranks.remove("5");
                   }
               }
            }
        });


        //Complete
        Button complete = (Button) findViewById(R.id.buttonSucR);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rank_filter.this, Filter.class);
                startActivity(intent);
            }
        });
        //--------------------------------------------------------------------------

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
}



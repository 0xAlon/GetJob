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
        CheckBox check1 = findViewById(R.id.checkBox);
        CheckBox check2 = findViewById(R.id.checkBox6);
        CheckBox check3 = findViewById(R.id.checkBox7);
        CheckBox check4 = findViewById(R.id.checkBox8);
        CheckBox check5 = findViewById(R.id.checkBox9);
        ArrayList<String>result = new ArrayList<String>();

        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check1.isChecked())
                    result.add("1");
                else
                    result.remove("1");
            }
        });
        check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check2.isChecked())
                    result.add("2");
                else
                    result.remove("2");
            }
        });
        check3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check1.isChecked())
                    result.add("3");
                else
                    result.remove("3");
            }
        });
        check4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check4.isChecked())
                    result.add("4");
                else
                    result.remove("4");
            }
        });
        check5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check5.isChecked())
                    result.add("5");
                else
                    result.remove("5");
            }
        });


        //Complete
        Button complete = (Button) findViewById(R.id.buttonSucR);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rank_filter.this, Filter.class);
                intent.putExtra("ranks", result);
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
    //need to add and think how to return this array of rank choces
    }
}



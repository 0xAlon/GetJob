package com.team3.getjob;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Filter extends AppCompatActivity {
    ArrayList<String> ranks;
    ArrayList<String> languages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setup
        setContentView(R.layout.activity_filter);
        Button button = (Button) findViewById(R.id.button4);


        //Filter to location
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Filter.this, Age.class);
                startActivity(intent1);
            }
        });

        //Filter to rank
        Button rankB = (Button) findViewById(R.id.button5);
        rankB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Filter.this, rank_filter.class);
                startActivity(intent2);
            }
        });

        //Filter to Languages
        Button LanguagesB = (Button) findViewById(R.id.button3);
        LanguagesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Filter.this, LanguagesActivity.class);
                startActivity(intent3);
            }
        });

        //Filter to Payment
        Button PaymentB = (Button) findViewById(R.id.button6);
        PaymentB .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Filter.this, PaymentActivity.class);
                startActivity(intent4);
            }
        });


        //Filter to jobsPull and cancel filters
        Button BackToSearch = (Button) findViewById(R.id.button7);
        BackToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Filter_model.s_languages != null)
                {
                    Filter_model.s_languages.clear();
                }

                if(Filter_model.s_ranks != null)
                {
                    Filter_model.s_ranks.clear();
                }

                Filter_model.Min_payment = null;
                Filter_model.Max_payment = null;
                Filter_model.ageAdult=false;

                //Go to jobs Pull
                Intent intent5 = new Intent(Filter.this, Jobs_Pull.class);
                startActivity(intent5);
            }
        });

        //Filter to jobsPull and Apply filters
        Button BackToSearchWithFilters = (Button) findViewById(R.id.button2);
        BackToSearchWithFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add info to PullJobs that will effect Database pull
                    Log.d("Language", "FLanguage: " + Filter_model.s_languages);
                Intent intent6 = new Intent(Filter.this, Jobs_Pull.class);
                startActivity(intent6);
            }
        });

    }
}
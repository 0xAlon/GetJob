package com.team3.getjob;

import android.content.Intent;
import android.os.Bundle;
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

        setContentView(R.layout.activity_filter);
        Button button = (Button) findViewById(R.id.button4);

        ArrayList<String> ranks = new ArrayList<String>();
        ArrayList<String> languages = new ArrayList<String>();

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            ranks = extras.getStringArrayList("result");
            languages = extras.getStringArrayList("languages");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Filter.this, Location.class);
                startActivity(intent1);
            }
        });

        Button rankB = (Button) findViewById(R.id.button5);
        rankB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Filter.this, rank_filter.class);
                startActivity(intent2);
            }
        });

        Button LanguagesB = (Button) findViewById(R.id.button3);
        LanguagesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Filter.this, LanguagesActivity.class);
                startActivity(intent3);
            }
        });

        Button PaymentB = (Button) findViewById(R.id.button6);
        PaymentB .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Filter.this, PaymentActivity.class);
                startActivity(intent4);
            }
        });
        Button BackToSearch = (Button) findViewById(R.id.button7);
        BackToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(Filter.this, Jobs_Pull.class);
                startActivity(intent5);
            }
        });
        Button BackToSearchWithFilters = (Button) findViewById(R.id.button2);
        ArrayList<String> finalRanks = ranks;
        ArrayList<String> finalLanguages = languages;
        BackToSearchWithFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add info to PullJobs tha will effect Database pull
                Intent intent6 = new Intent(Filter.this, Jobs_Pull.class);
                intent6.putExtra("ranks", finalRanks);
                intent6.putExtra("languages", finalLanguages);
                startActivity(intent6);
            }
        });

    }
}
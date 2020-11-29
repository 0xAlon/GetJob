package com.team3.getjob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Filter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Button button = (Button) findViewById(R.id.button4);
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
        LanguagesB .setOnClickListener(new View.OnClickListener() {
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

    }
}
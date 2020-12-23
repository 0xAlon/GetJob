package com.team3.getjob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LanguagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);
        CheckBox englishL = findViewById(R.id.English);
        CheckBox hebrewL = findViewById(R.id.Hebrew);
        CheckBox russianL = findViewById(R.id.Russian);


        //Add class implementation and add languages


        englishL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (englishL.isChecked())
                   // languages.add("English");
               *//* else*//*
                  //  languages.remove("English");*/
            }
        });
        hebrewL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (hebrewL.isChecked())
                   *//* languages.add("Hebrew");
                else
                    languages.remove("Hebrew");*/
            }
        });

        russianL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (russianL.isChecked())
                   *//* languages.add("Russia");
                else
                    languages.remove("Russian");*/
            }
        });

        //option for cancel location filter and came back for filter menu
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LanguagesActivity.this, Filter.class);
                startActivity(intent);
            }
        });


        Button buttonSucV = (Button) findViewById(R.id.button_LAN_V);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LanguagesActivity.this, Filter.class);
                startActivity(intent);
            }
        });
        //---------------------------------------------------------------------------
    }
    }

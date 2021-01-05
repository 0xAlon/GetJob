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
                if(!Filter_model.s_languages.contains("English"))
                {
                    Filter_model.s_languages.add("English");
                }
                else if(Filter_model.s_languages.contains("English"))
                {
                    Filter_model.s_languages.remove("English");
                }
            }
        });
        hebrewL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Filter_model.s_languages.contains("Hebrew"))
                {
                    Filter_model.s_languages.add("Hebrew");
                }
                else if(Filter_model.s_languages.contains("Hebrew"))
                {
                    Filter_model.s_languages.remove("Hebrew");
                }
            }
        });

        russianL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (russianL.isChecked())
                    if(!Filter_model.s_languages.contains("Russian"))
                    {
                        Filter_model.s_languages.add("Russian");
                    }

                    else if(Filter_model.s_languages.contains("Russian"))
                    {
                        Filter_model.s_languages.remove("Russian");
                    }
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
        buttonSucV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LanguagesActivity.this, Filter.class);
                startActivity(intent);
            }
        });
        //---------------------------------------------------------------------------
    }
    }

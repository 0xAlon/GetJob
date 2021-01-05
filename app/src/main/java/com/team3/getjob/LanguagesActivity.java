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
                if(!Filter_model.s_languages.contains("אנגלית"))
                {
                    Filter_model.s_languages.add("אנגלית");
                }
                else if(Filter_model.s_languages.contains("אנגלית"))
                {
                    Filter_model.s_languages.remove("אנגלית");
                }
            }
        });
        hebrewL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Filter_model.s_languages.contains("עברית"))
                {
                    Filter_model.s_languages.add("עברית");
                }
                else if(Filter_model.s_languages.contains("עברית"))
                {
                    Filter_model.s_languages.remove("עברית");
                }
            }
        });

        russianL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (russianL.isChecked())
                    if(!Filter_model.s_languages.contains("רוסית"))
                    {
                        Filter_model.s_languages.add("רוסית");
                    }

                    else if(Filter_model.s_languages.contains("רוסית"))
                    {
                        Filter_model.s_languages.remove("רוסית");
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

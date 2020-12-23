package com.team3.getjob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText low,high;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        //option for cancel location filter and came back for filter menu
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, Filter.class);
                startActivity(intent);
            }
        });
        //---------------------------------------------------------------------------

        //Let's Filter
        low=findViewById(R.id.editText);
        high=findViewById(R.id.editText1);
        Button buttonF = (Button) findViewById(R.id.button);


        buttonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(low.getText().toString().isEmpty() || high.getText().toString().isEmpty()){
                    Toast.makeText(PaymentActivity.this,"Empty",Toast.LENGTH_LONG).show();
                }else{
                    String lowF=low.getText().toString();
                    String highF=high.getText().toString();
                    boolean x=CheckInput(lowF);
                    boolean y=CheckInput(highF);
                }
            }
        });
        //---------------------------------------------------------------------------

    }
    boolean CheckInput(String str){
        return str.matches("[0-9]+") && str.length() > 2;
    }
}
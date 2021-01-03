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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //Setup-------------------------------------------------------------------------
        EditText low,high;

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

        //View setup
        low=findViewById(R.id.editText);
        high=findViewById(R.id.editText1);
        Button buttonF = (Button) findViewById(R.id.applyChanges);


        //Apply button
        buttonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(low.getText().toString().isEmpty() || high.getText().toString().isEmpty()){
                    Toast.makeText(PaymentActivity.this,"Empty",Toast.LENGTH_LONG).show();
                }else{
                    String lowF = low.getText().toString();
                    String highF = high.getText().toString();
                    Filter_model.Max_payment = highF;
                    Filter_model.Min_payment = lowF;

                    Intent intent_Apply = new Intent(PaymentActivity.this, Filter.class);
                    startActivity(intent_Apply);

                }
            }
        });

    }

}
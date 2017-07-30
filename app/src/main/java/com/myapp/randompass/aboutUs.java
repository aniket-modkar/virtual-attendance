package com.myapp.randompass;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by rishi on 4/30/2017.
 */

public class aboutUs extends AppCompatActivity {

    TextView textedits;
    String[] ar=new String[8];
    int i=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abouts_layout);
        textedits=(TextView) findViewById(R.id.texts_genrated);
        ar[0]="App Created By";
        ar[1]="Aniket Modker";
        ar[2]="An Kit Singh";
        ar[3]="Anshika Jaiswal";
        ar[4]="Guided by:- KAVITA NAMDEV";
        ar[5]="Database Used";
        ar[6]="FIREBASE";
        ar[7]="Thanks For Using Our App";

        new CountDownTimer(100000, 2000) {
            public void onFinish() {

            }
            public void onTick(long millisUntilFinished) {
                textedits.setText(ar[i]);
                i++;
                if(i>10)
                {
                    finish();
                }
                // millisUntilFinished    The amount of time until finished.
            }
        }.start();
    }
}

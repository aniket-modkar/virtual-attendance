package com.myapp.randompass;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.Wave;

public class StartLogo extends Activity
{

    ImageView img;
    int i=0;
    Wave wave=new Wave();
    String[] clr = new String[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startlogo_layout);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);

        clr[0]="#f4511e";
        clr[1]="#039be5";
        clr[2]="#6d4c41";
        clr[3]="#283593";
        clr[4]="#c62828";
        clr[5]="#c2185b";
        img = (ImageView) findViewById(R.id.imgvlogo);

        progressBar.setIndeterminateDrawable(wave);

        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                wave.setColor(Color.parseColor(clr[i]));
                i++;
            }

            public void onFinish() {
                startActivity(new Intent(StartLogo.this, circlee.class));
                finish();
            }
        }.start();

    }

}

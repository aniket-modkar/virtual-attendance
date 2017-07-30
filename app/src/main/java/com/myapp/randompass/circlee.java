package com.myapp.randompass;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

public class circlee extends AppCompatActivity {

    CircleMenu circleMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circlemenu_layout);
        circleMenu = (CircleMenu) findViewById(R.id.circle_menu);

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.mipmap.clss, R.drawable.close);
        circleMenu.addSubMenu(Color.parseColor("#258CFF"), R.drawable.start)
                .addSubMenu(Color.parseColor("#30A400"), R.drawable.about)
                .addSubMenu(Color.parseColor("#FF4B32"), R.drawable.list)
                .addSubMenu(Color.parseColor("#8A39FF"), R.drawable.check);


        circleMenu.setOnMenuSelectedListener(new OnMenuSelectedListener() {
         public void onMenuSelected(int index) {
          switch (index) {
         case 0:
         new CountDownTimer(1000, 1000) {
          public void onFinish() {
            startActivity(new Intent(circlee.this, MainActivity.class));
           }
           public void onTick(long millisUntilFinished) {
        // millisUntilFinished    The amount of time until finished.
            }
            }.start();

             break;
              case 1:
                  new CountDownTimer(1000, 1000) {
                      public void onFinish() {
                          startActivity(new Intent(circlee.this, aboutUs.class));
                      }
                      public void onTick(long millisUntilFinished) {
                          // millisUntilFinished    The amount of time until finished.
                      }
                  }.start();

              break;
               case 2:
              //list of students
                   new CountDownTimer(1000, 1000) {
                       public void onFinish() {
                           startActivity(new Intent(circlee.this, attendancelist_two.class));
                       }
                       public void onTick(long millisUntilFinished) {
                           // millisUntilFinished    The amount of time until finished.
                       }
                   }.start();
               break;
               case 3:
                   new CountDownTimer(1000, 1000) {
                       public void onFinish() {
                           startActivity(new Intent(circlee.this, calculate_attendance.class));
                       }
                       public void onTick(long millisUntilFinished) {
                           // millisUntilFinished    The amount of time until finished.
                       }
                   }.start();


                break;
                case 4:
                Toast.makeText(circlee.this, "GPS button Clicked", Toast.LENGTH_SHORT).show();
                break;
                                                     }
                                                 }
                                             }

        );

        circleMenu.setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

                                                     @Override
                                                     public void onMenuOpened() {

                                                     }

                                                     @Override
                                                     public void onMenuClosed() {
                                            
                                                     }
                                                 }
        );
    }

    @Override
    public void onBackPressed() {
        if (circleMenu.isOpened())
            circleMenu.closeMenu();
        else
            finish();
    }

}

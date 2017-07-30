package com.myapp.randompass;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static android.R.attr.data;
import static android.R.attr.id;
import static android.R.attr.onClick;

public class MainActivity extends AppCompatActivity {


    String s1="",s2="",s3="";
    int off=0;
    String class_name,lecture_name,subject_name;
    private DatabaseReference root= FirebaseDatabase.getInstance().getReference().getRoot();
    DatabaseReference root1;
    int check=0;
    String pass="";
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Button b1 = (Button) findViewById(R.id.btn_genrate);
        Button b2=(Button)findViewById(R.id.delete);

        final Spinner spinner_classname= (Spinner) findViewById(R.id.spinner_classname);
        final Spinner spinner_sub_name= (Spinner) findViewById(R.id.spinner_sub_name);
        final Spinner spinner_lecture_name= (Spinner)findViewById(R.id.spinner_lecture_name);

        List<String> categories = new ArrayList<String>();
        categories.add("CS1");
        categories.add("CS2");
        categories.add("CS3");
        categories.add("CS4");
        List<String> sub = new ArrayList<String>();
        sub.add("Computer Network");
        sub.add("Priniciples of Programming Languages");
        sub.add("Advance Computer Architecture");
        sub.add("Micro Processor and Interfacing");
        sub.add("Software Engineering and Project Management");
        List<String> lecture = new ArrayList<String>();
        lecture.add("1Lecture");
        lecture.add("2lecture");
        lecture.add("3Lecture");
        lecture.add("4Lecture");
        lecture.add("5Lecture");
        lecture.add("6Lecture");
        lecture.add("7Lecture");



        ArrayAdapter<String> data_adaptercn = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories);
        data_adaptercn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_classname.setAdapter(data_adaptercn);

        ArrayAdapter<String> data_adaptersn = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sub);
        data_adaptersn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sub_name.setAdapter(data_adaptersn);

        ArrayAdapter<String> data_adapterln = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lecture);
        data_adapterln.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_lecture_name.setAdapter(data_adapterln);



        b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

               //     TextView tv_pas = (TextView) findViewById(R.id.textView);
                    char[] chars1 = "1234567890".toCharArray();
                    StringBuilder sb1 = new StringBuilder();
                    Random random1 = new Random();
                    for (int i = 0; i < 6; i++) {
                        char c1 = chars1[random1.nextInt(chars1.length)];
                        sb1.append(c1);
                    }
                    String random_string = sb1.toString();
                 //   tv_pas.setText(random_string);
                    setTitle(random_string);

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(random_string, "");
                    pass=random_string;
                    if(check==0) {
                        root.updateChildren(map);
                        check = 1;
                    }
                    else
                    {
                 //       tv_pas.setText("Password is already genreated");
                    }

                }
            });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    TextView tv_pas = (TextView) findViewById(R.id.textView);
             //   tv_pas.setText("");
                class_name=spinner_classname.getSelectedItem().toString();
                lecture_name = spinner_lecture_name.getSelectedItem().toString();
                subject_name=spinner_sub_name.getSelectedItem().toString();
                check=0;
                create();
                new CountDownTimer(60000, 1000) {
                    TextView timertv= (TextView) findViewById(R.id.textViewtimer);
                    public void onTick(long millisUntilFinished) {
                        timertv.setText("seconds remaining: " + millisUntilFinished / 1000);
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {

                        TextView timertv= (TextView) findViewById(R.id.textViewtimer);
                        timertv.setText("done");
                        check=0;
                        root.child(pass).removeValue();
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Confirm Password")
                                .setMessage("Create a confirm password now")
                                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        confirm_password();
                                    }
                                })
                                .create().show();

                        // change the activity here
                    }

                }.start();

              /*  Map<String, Object> map = new HashMap<String, Object>();
                map.put(db_name, "");
                    root.updateChildren(map);
                */// root.removeEventListener();
            }
        });
 }

    private void confirm_password() {
        char[] chars1 = "1234567890".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++) {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        String random_string = sb1.toString();
        //   tv_pas.setText(random_string);
        setTitle(random_string);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(random_string, "");
        pass=random_string;
            root.updateChildren(map);

        new CountDownTimer(30000, 1000) {
            TextView timertv= (TextView) findViewById(R.id.textViewtimer);
            public void onTick(long millisUntilFinished) {
                timertv.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {

                TextView timertv= (TextView) findViewById(R.id.textViewtimer);
                timertv.setText("done");
                root.child(pass).removeValue();
                onBack();
                // change the activity here
            }

        }.start();

    }

    private void onBack() {
    finish();
    }

    private void create() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        final String db_name=subject_name+""+""+lecture_name+""+date;

        Map <String,Object> map= new HashMap<String, Object>();
        map.put(db_name,"");
        root.updateChildren(map);
        Toast toast=Toast.makeText(getApplicationContext(),db_name, Toast.LENGTH_LONG);
        toast.setMargin(50,50);
        toast.show();
        childcreate();

    }

    private void childcreate() {

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String date1 = date;
        final String db_childname=class_name+""+subject_name+""+lecture_name;
        String db_name= subject_name+""+lecture_name+""+date1;
        root1=FirebaseDatabase.getInstance().getReference().child(db_name);
        Map <String,Object> map2= new HashMap<String, Object>();
        map2.put(db_childname,"");
        root1.updateChildren(map2);
        DatabaseReference atndncname=root1.child(db_childname);
        Toast toast=Toast.makeText(getApplicationContext(),db_childname, Toast.LENGTH_LONG);
        toast.setMargin(0,0);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        Toast toast=Toast.makeText(getApplicationContext(),"You can not exit app now . You have to wait till process get over", Toast.LENGTH_LONG);
        toast.setMargin(0,0);
        toast.show();

    }
}
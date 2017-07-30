package com.myapp.randompass;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;

/**
 * Created by rishi on 4/16/2017.
 */

public class listofstudents extends AppCompatActivity {

    TextView students_name;
    String listname="";
    DatabaseReference child;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listofstudents_layout);
        students_name=(TextView)findViewById(R.id.textView_students_name);
        listname=getIntent().getExtras().get("list_name").toString();
        child= FirebaseDatabase.getInstance().getReference().child(listname);
        students_name.setTypeface(null, Typeface.BOLD);

        child.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                showlist(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                showlist(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    String student_name="";


       private void showlist(DataSnapshot dataSnapshot) {
        Iterator i=dataSnapshot.getChildren().iterator();
        while ((i.hasNext()))
        {

            student_name=(String)((DataSnapshot)i.next()).getKey();
            students_name.append(student_name +"  "   + " \n\n");

        }
    }


}

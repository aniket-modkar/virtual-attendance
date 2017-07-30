package com.myapp.randompass;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

/**
 * Created by rishi on 4/23/2017.
 */

public class calculate_attendance extends AppCompatActivity {
    ListView listofclass;
    ArrayAdapter<String> arrayad;
Button search_button,email_button;
    ProgressBar progressLOading;
    String email;
    int count=0;
    ArrayList<String > arraylistofclass= new ArrayList<>();
    int sizes =0;
    DatabaseReference root= FirebaseDatabase.getInstance().getReference().getRoot();
    ArrayList<String > tempsearchlist= new ArrayList<>();
    ArrayList<String > tempsearchemail= new ArrayList<>();
    TextInputLayout inputLayoutfname,inputLayoutsname;
    EditText fname_et,sname_et;
    int list_position=0;
    String EMAIL ="enter a reference email here";
    DatabaseReference child;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calculate_layout);
        inputLayoutfname = (TextInputLayout) findViewById(R.id.input_layout_fname);
        inputLayoutsname = (TextInputLayout) findViewById(R.id.input_layout_sname);
        fname_et=(EditText) findViewById(R.id.input_fname);
        sname_et=(EditText) findViewById(R.id.input_sname);
        fname_et.addTextChangedListener(new MyTextWatcher(fname_et));
        sname_et.addTextChangedListener(new MyTextWatcher(sname_et));
        search_button=(Button) findViewById(R.id.butooncal);
        email_button=(Button) findViewById(R.id.send_email_button);

        email_button.setVisibility(View.GONE);
        listofclass=(ListView) findViewById(R.id.listvietemp);
        progressLOading=(ProgressBar) findViewById(R.id.progressBar);
        progressLOading.setVisibility(View.GONE);


        arrayad= new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,arraylistofclass);
        listofclass.setAdapter(arrayad);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set= new HashSet<String>();
                Iterator i =dataSnapshot.getChildren().iterator();

                while(i.hasNext())
                {
                    set.add(((DataSnapshot)i.next()).getKey());

                }

                arraylistofclass.clear();
                arraylistofclass.addAll(set);

                arrayad.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        sname_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String search=s.toString();

                tempsearchlist.clear();
                for(String se:arraylistofclass)
                {
                    if(se.contains(search))
                    {
                        tempsearchlist.add(se);
                    }
                }
                ArrayAdapter<String> adpter=new ArrayAdapter<String>(calculate_attendance.this,android.R.layout.simple_expandable_list_item_1,tempsearchlist);
                listofclass.setAdapter(adpter);
                adpter.notifyDataSetChanged();
                sizes=tempsearchlist.size();
            }
        });
search_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        submitForm();
        method();
        showattendance();
        progressLOading.setVisibility(View.VISIBLE);
            // here we et all list name of search results


    }
});
email_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        sendemail();


    }
});
    }

    private void sendemail() {

        sendEmail( email);


    }

    private void showattendance() {

        new CountDownTimer(10000, 1000) {
            public void onFinish() {

                float siz=sizes;
                float cnt=count;
                float attendnce= (cnt/siz)*100;
                if(attendnce<60)
                {

                    email_button.setVisibility(View.VISIBLE);
                }
                String att=Float.toString(attendnce);
                Toast.makeText(calculate_attendance.this, att+" percent attendance"+sizes+""+count, Toast.LENGTH_SHORT).show();

                String tittle= sname_et.getText().toString();
                String name=fname_et.getText().toString();
                new AlertDialog.Builder(calculate_attendance.this)
                        .setTitle(tittle)
                        .setMessage(""+name +" \n"+""+att+"%\n"+"Lecture Attended "+""+count+"\n"+"Total Lectures "+""+sizes)
                        .create().show();
                progressLOading.setVisibility(View.GONE);
            }
            public void onTick(long millisUntilFinished) {

                // millisUntilFinished    The amount of time until finished.
            }
        }.start();

        count=0;
    }


    private void method() {
        for(int i=0;i<sizes;i++)
        {

            final String result=tempsearchlist.get(i);
            child= FirebaseDatabase.getInstance().getReference().child(result);

            child.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Iterator i=dataSnapshot.getChildren().iterator();
                    String student_name= fname_et.getText().toString();
                    while ((i.hasNext()))
                    {
                        String  check_name=(String)((DataSnapshot)i.next()).getValue();
                        String[] splitArray = check_name.split("\\s+");

                        if(student_name.equalsIgnoreCase(splitArray[1]))
                        {
                            //String email=dataSnapshot.getValue();
                            tempsearchemail.add(result);
                            String email_list=tempsearchemail.get(count);
                            email=splitArray[0];
                            count++;
                        }


                    }


                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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



    }

    private void submitForm() {
        if (!validateName()) {

            return;
        }
        if (!validateSName()) {

            return;
        }
    }

    private boolean validateName() {
        if (fname_et.getText().toString().trim().isEmpty()) {
            inputLayoutfname.setError("Feild Required");
            requestFocus(fname_et);
            return false;
        } else {
            inputLayoutfname.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateSName() {
        if (sname_et.getText().toString().trim().isEmpty()) {
            inputLayoutsname.setError("Feild Required");
            requestFocus(sname_et);
            return false;
        } else {
            inputLayoutsname.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_fname:
                    validateName();
                case R.id.input_sname:
                    validateSName();
            }
        }
    }
    private void sendEmail(final String email) {
        String student_name=fname_et.getText().toString();
        final String Subject="Low Attendnace";
        final String message=student_name+" your name is added in sort list due to low attendance.For further queries contact your class coordinator";


//if(checkConnectivity()==1) {
        new Thread(new Runnable() {

            public void run() {

                try {

                    GMailSender sender = new GMailSender(
                            "enter mail id here", "enter password here");
                    //sender.addAttachment(Environment.getExternalStorageDirectory().getPath()+"/image.jpg");
                    sender.sendMail(Subject, message,
                            EMAIL,
                            email);

                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }

            }

        }).start();
    }

}

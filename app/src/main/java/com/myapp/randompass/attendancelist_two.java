package com.myapp.randompass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by rishi on 4/16/2017.
 */

public class attendancelist_two extends AppCompatActivity {
    ListView listofclass;
    ArrayAdapter<String> arrayad;

    ArrayList<String > arraylistofclass= new ArrayList<>();



    DatabaseReference root= FirebaseDatabase.getInstance().getReference().getRoot();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendancelist_layout);
        listofclass=(ListView) findViewById(R.id.listviewclass_id);



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
        listofclass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),listofstudents.class);
                intent.putExtra("list_name",((TextView)view).getText().toString());
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);

        MenuItem searchItem= menu.findItem(R.id.search_icon);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String > tempsearchlist= new ArrayList<>();
                for(String item: arraylistofclass)
                {
                    if(item.contains(newText))
                    {
                        tempsearchlist.add(item);
                    }
                }

                ArrayAdapter<String> adpter=new ArrayAdapter<String>(attendancelist_two.this,android.R.layout.simple_expandable_list_item_1,tempsearchlist);
                listofclass.setAdapter(adpter);

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.search_icon)
        {

        }
        return super.onOptionsItemSelected(item);
    }

}

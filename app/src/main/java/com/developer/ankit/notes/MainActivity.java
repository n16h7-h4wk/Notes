package com.developer.ankit.notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> List = new ArrayList<String>();
    static ArrayAdapter mArrayAdapter ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.add_note){
            Intent mIntent = new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(mIntent);
            return true;
        }
        return false ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mListView = (ListView) findViewById(R.id.list_view);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("com.developer.ankit.notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>)prefs.getStringSet("notes",null);
        if(set==null) {
            List.add("Example note...");
        }else {
            List = new ArrayList(set);
        }
        mArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,List);
        mListView.setAdapter(mArrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getApplicationContext(),Main2Activity.class);
                mIntent.putExtra("noteID",position);
                startActivity(mIntent);
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete the note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                List.remove(position);
                                mArrayAdapter.notifyDataSetChanged();
                                HashSet<String> set = new HashSet<>(MainActivity.List);
                                SharedPreferences prefs = getApplicationContext().getSharedPreferences("com.developer.ankit.notes", Context.MODE_PRIVATE);
                                prefs.edit().putStringSet("notes",set).apply();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

                return true;
            }
        });
    }
}

package com.developer.ankit.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;

public class Main2Activity extends AppCompatActivity {

    int noteID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EditText mTextView = (EditText) findViewById(R.id.edit_text);
        Intent mIntent = getIntent();
        noteID = mIntent.getIntExtra("noteID",-1);
        if(noteID!=-1){
            mTextView.setText(MainActivity.List.get(noteID));
        }else {
            MainActivity.List.add("");
            noteID = MainActivity.List.size()-1 ;
            MainActivity.mArrayAdapter.notifyDataSetChanged();
        }
        mTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.List.set(noteID,String.valueOf(s));
                MainActivity.mArrayAdapter.notifyDataSetChanged();
                HashSet<String> set = new HashSet<>(MainActivity.List);
                SharedPreferences prefs = getApplicationContext().getSharedPreferences("com.developer.ankit.notes", Context.MODE_PRIVATE);
                prefs.edit().putStringSet("notes",set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

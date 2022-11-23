package com.example.lab_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;

public class AddNote extends AppCompatActivity {
    int noteId;
    EditText title,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = (EditText) findViewById(R.id.editTitle);
        description = (EditText) findViewById(R.id.editDescription);
        Button noteSaveBtn = (Button) findViewById(R.id.noteSaveBtn);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId",-1);
        if(noteId != -1){
            title.setText(MainActivity.notes.get(noteId));
            description.setText((MainActivity.descriptions.get(noteId)));
        }

        noteSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int length = title.getText().toString().length();
                if(length < 1){
                    Toast.makeText(AddNote.this, getResources().getString(R.string.noInput), Toast.LENGTH_LONG).show();
                }
                else{
                    if(noteId == -1){
                        MainActivity.notes.add(title.getText().toString());
                        MainActivity.descriptions.add(description.getText().toString());
                    }
                    else{
                        MainActivity.notes.set(noteId,title.getText().toString());
                        MainActivity.descriptions.set(noteId,description.getText().toString());
                    }
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AddNote.this);
                    HashSet<String> titles = new HashSet<>(MainActivity.notes);
                    HashSet<String> descriptionsNew = new HashSet<>(MainActivity.descriptions);
                    prefs.edit().putStringSet("newTitles",titles).apply();
                    prefs.edit().putStringSet("newDescriptions",descriptionsNew).apply();
                    finish();
                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int length = title.getText().toString().length();
        if(length < 1){
            finish();
        }
        else{
            if(noteId == -1){
                MainActivity.notes.add(title.getText().toString());
                MainActivity.descriptions.add(description.getText().toString());
            }
            else{
                MainActivity.notes.set(noteId,title.getText().toString());
                MainActivity.descriptions.set(noteId,description.getText().toString());
            }
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AddNote.this);
            HashSet<String> titles = new HashSet<>(MainActivity.notes);
            HashSet<String> descriptionsNew = new HashSet<>(MainActivity.descriptions);
            prefs.edit().putStringSet("newTitles",titles).apply();
            prefs.edit().putStringSet("newDescriptions",descriptionsNew).apply();
        }
    }
}
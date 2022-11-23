package com.example.lab_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayList<String> descriptions = new ArrayList<>();
    static ArrayAdapter adapter;
    ListView notesList;
    int noteId;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_notes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.addNote){
            Intent intent = new Intent(this, AddNote.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesList = (ListView) findViewById(R.id.notesList);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        notesList.setAdapter(adapter);


        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), AddNote.class);
                intent.putExtra("noteId",i);
                startActivity(intent);


            }
        });
        notesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),DeleteNote.class);
                intent.putExtra("deleteId",i);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        HashSet<String> titles = (HashSet<String>) prefs.getStringSet("newTitles",null);
        HashSet<String> descriptionsNew = (HashSet<String>) prefs.getStringSet("newDescriptions",null);
        if(titles != null){
            notes = new ArrayList<>(titles);
            descriptions = new ArrayList<>(descriptionsNew);
            adapter.clear();
            for(int i = 0; i < notes.size(); i++)
            {
                adapter.add(notes.get(i));
                adapter.notifyDataSetChanged();
            }
        }


    }
}
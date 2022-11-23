package com.example.lab_4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;

public class DeleteNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);
        Intent intent = getIntent();
        int deleteId = intent.getIntExtra("deleteId",-1);
        new AlertDialog.Builder(DeleteNote.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getResources().getString(R.string.alertTitle))
                .setMessage(getResources().getString(R.string.alertMessage))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.notes.remove(deleteId);
                        MainActivity.descriptions.remove(deleteId);

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(DeleteNote.this);
                        HashSet<String> titles = new HashSet<>(MainActivity.notes);
                        HashSet<String> descriptionsNew = new HashSet<>(MainActivity.descriptions);
                        prefs.edit().putStringSet("newDescriptions",descriptionsNew).apply();
                        prefs.edit().putStringSet("newTitles",titles).apply();
                        finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
    }
}
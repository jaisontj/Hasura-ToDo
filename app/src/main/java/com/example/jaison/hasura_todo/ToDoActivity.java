package com.example.jaison.hasura_todo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToDoActivity extends BaseActivity {

    private static String FIREBASE_TABLENAME = "todo";
    private static String TAG = "ToDoActivity";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ToDoRecyclerViewAdapter adapter;

    public static void startActivity(Activity startingActivity) {
        startingActivity.startActivity(new Intent(startingActivity,ToDoActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addTodo:
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                final EditText edittext = new EditText(this);
                alert.setMessage("Describe your task");
                alert.setTitle("Create new task");
                alert.setView(edittext);
                alert.setPositiveButton("Add Todo", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();
                return true;
            case R.id.signOut:
                AlertDialog.Builder signOutAlert = new AlertDialog.Builder(this);
                signOutAlert.setTitle("Sign Out");
                signOutAlert.setMessage("Are you sure you want to sign out?");
                signOutAlert.setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                signOutAlert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                signOutAlert.show();
                return true;
        }
        return false;
    }
}

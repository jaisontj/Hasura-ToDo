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

import com.example.jaison.hasura_todo.db.SharedPrefHandler;
import com.example.jaison.hasura_todo.db.tables.records.TodoRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.hasura.auth.AuthException;
import io.hasura.auth.LogoutResponse;
import io.hasura.core.Callback;
import io.hasura.db.DBException;
import io.hasura.db.DeleteResult;
import io.hasura.db.InsertResult;
import io.hasura.db.UpdateResult;

import static com.example.jaison.hasura_todo.db.Tables.TODO;


public class ToDoActivity extends BaseActivity {

    private static String FIREBASE_TABLENAME = "todo";
    private static String TAG = "ToDoActivity";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ToDoRecyclerViewAdapter adapter;

    public static void startActivity(Activity startingActivity) {
        startingActivity.startActivity(new Intent(startingActivity,ToDoActivity.class));
        startingActivity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ToDoRecyclerViewAdapter(new ToDoRecyclerViewAdapter.Interactor() {
            @Override
            public void onTodoClicked(int position, TodoRecord record) {
                toggleTodo(position, record);
            }

            @Override
            public void onTodoLongClicked(int position, TodoRecord record) {
                deleteTodo(position, record);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        fetchTodosFromDB();
    }

    private void fetchTodosFromDB() {
        showProgressIndicator();
        Hasura.db.select(TODO)
                .columns(TODO.COMPLETED,TODO.ID, TODO.TITLE, TODO.USER_ID)
                .where(TODO.USER_ID.eq(Hasura.getCurrentUserId()))
                .build().enqueue(new Callback<List<TodoRecord>, DBException>() {
            @Override
            public void onSuccess(final List<TodoRecord> todoRecords) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressIndicator();
                        adapter.setData(todoRecords);
                    }
                });
            }

            @Override
            public void onFailure(final DBException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressIndicator();
                        showErrorAlert(e.getLocalizedMessage(),null);
                    }
                });
            }
        });
    }

    private void toggleTodo(final int recyclerViewPostion, final TodoRecord record) {
        showProgressIndicator();
        record.completed = !record.completed;
        Hasura.db.update(TODO)
                .set(TODO.COMPLETED, record.completed)
                .where(TODO.ID.eq(record.id))
                .build().enqueue(new Callback<UpdateResult<TodoRecord>, DBException>() {
            @Override
            public void onSuccess(UpdateResult<TodoRecord> todoRecordUpdateResult) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressIndicator();
                        adapter.updateData(recyclerViewPostion,record);
                    }
                });
            }

            @Override
            public void onFailure(final DBException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressIndicator();
                        showErrorAlert(e.getLocalizedMessage(),null);
                    }
                });
            }
        });
    }

    private void deleteTodo(final int recyclerViewPosition, final TodoRecord record) {
        showProgressIndicator();
        Hasura.db.delete(TODO)
                .where(TODO.ID.eq(record.id))
                .build().enqueue(new Callback<DeleteResult<TodoRecord>, DBException>() {
            @Override
            public void onSuccess(DeleteResult<TodoRecord> todoRecordDeleteResult) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressIndicator();
                        adapter.deleteData(recyclerViewPosition,record);
                    }
                });
            }

            @Override
            public void onFailure(final DBException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressIndicator();
                        showErrorAlert(e.getLocalizedMessage(),null);
                    }
                });
            }
        });
    }

    private void addATodo(final String description) {
        showProgressIndicator();
        Hasura.db.insert(TODO)
                .set(TODO.TITLE,description)
                .set(TODO.COMPLETED, false)
                .set(TODO.USER_ID, Hasura.getCurrentUserId())
                .returning(TODO.ID)
                .build().enqueue(new Callback<InsertResult<TodoRecord>, DBException>() {
            @Override
            public void onSuccess(final InsertResult<TodoRecord> todoRecordInsertResult) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressIndicator();
                        TodoRecord record = new TodoRecord();
                        record.completed = false;
                        record.id = todoRecordInsertResult.getRecords().get(0).id;
                        record.userId = Hasura.getCurrentUserId();
                        record.title = description;

                        adapter.addData(record);
                    }
                });

            }

            @Override
            public void onFailure(final DBException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressIndicator();
                        showErrorAlert(e.getLocalizedMessage(),null);
                    }
                });
            }
        });

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
                        addATodo(edittext.getText().toString());
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
                        showProgressIndicator();
                        Hasura.auth.logout().enqueue(new Callback<LogoutResponse, AuthException>() {
                            @Override
                            public void onSuccess(LogoutResponse logoutResponse) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Hasura.clearSession();
                                        sharedPrefHandler.setUserData(-1,null,null);
                                        hideProgressIndicator();
                                        SignInActivity.startActivity(ToDoActivity.this);

                                    }
                                });
                            }

                            @Override
                            public void onFailure(final AuthException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideProgressIndicator();
                                        showErrorAlert(e.getLocalizedMessage(),null);
                                    }
                                });
                            }
                        });
                    }
                });
                signOutAlert.show();
                return true;
        }
        return false;
    }
}

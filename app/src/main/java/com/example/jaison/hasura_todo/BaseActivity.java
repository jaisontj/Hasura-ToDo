package com.example.jaison.hasura_todo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.jaison.hasura_todo.db.SharedPrefHandler;

public class BaseActivity extends AppCompatActivity {

    ProgressDialog pd;
    SharedPrefHandler sharedPrefHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(this);
        sharedPrefHandler = new SharedPrefHandler(this);
    }

    protected void showErrorAlert(String message, DialogInterface.OnClickListener listener) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(message);
        if (listener != null) {
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Dismiss", listener);
        } else {
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
        }
        alertDialog.show();
    }

    protected void showProgressIndicator() {
        pd.setMessage("Please wait");
        pd.show();
    }

    protected void hideProgressIndicator() {
        pd.dismiss();
    }
}

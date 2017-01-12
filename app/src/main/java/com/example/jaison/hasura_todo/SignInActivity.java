package com.example.jaison.hasura_todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.jaison.hasura_todo.db.SharedPrefHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.hasura.auth.AuthException;
import io.hasura.auth.LoginRequest;
import io.hasura.auth.LoginResponse;
import io.hasura.auth.RegisterRequest;
import io.hasura.auth.RegisterResponse;
import io.hasura.core.Callback;

public class SignInActivity extends BaseActivity {

    @BindView(R.id.emailId)
    EditText emailId;
    @BindView(R.id.password)
    EditText password;

    public static void startActivity(Activity startingActivity) {
        startingActivity.startActivity(new Intent(startingActivity,SignInActivity.class));
        startingActivity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        Hasura.setClient();

        if (sharedPrefHandler.getUserId() != -1) {
            //Logged In
            Hasura.setCurrentSession(sharedPrefHandler.getUserId(),"user",sharedPrefHandler.getUserSessionId());
            ToDoActivity.startActivity(this);
        }
    }


    @OnClick({R.id.signInButton, R.id.signUpButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signInButton:
                final LoginRequest loginRequest = new LoginRequest(getEmailId(),getPassword());
                showProgressIndicator();
                Hasura.auth.login(loginRequest).enqueue(new Callback<LoginResponse, AuthException>() {
                    @Override
                    public void onSuccess(final LoginResponse loginResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideProgressIndicator();
                                sharedPrefHandler.setUserData(loginResponse.getHasuraId(),"role",loginResponse.getSessionId());
                                Hasura.setCurrentSession(loginResponse.getHasuraId(),"user",loginResponse.getSessionId());
                                ToDoActivity.startActivity(SignInActivity.this);
                            }
                        });
                    }

                    @Override
                    public void onFailure(final AuthException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideProgressIndicator();
                                Log.i("SignInActivity",e.getMessage());
                                Log.i("SignInActivity",e.getLocalizedMessage());
                                Log.i("SignInActivity",e.toString());
                                showErrorAlert(e.getLocalizedMessage(),null);
                            }
                        });
                    }
                });
                break;
            case R.id.signUpButton:
                final RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setUsername(getEmailId());
                registerRequest.setPassword(getPassword());
                showProgressIndicator();
                Hasura.auth.register(registerRequest).enqueue(new Callback<RegisterResponse, AuthException>() {
                    @Override
                    public void onSuccess(final RegisterResponse registerResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sharedPrefHandler.setUserData(registerResponse.getHasuraId(),"role",registerResponse.getSessionId());
                                Hasura.setCurrentSession(registerResponse.getHasuraId(),"user",registerResponse.getSessionId());
                                hideProgressIndicator();
                                ToDoActivity.startActivity(SignInActivity.this);
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
                break;
        }
    }

    private String getEmailId() {
        return emailId.getText().toString();
    }

    private String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}

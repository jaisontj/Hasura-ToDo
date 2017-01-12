package com.example.jaison.hasura_todo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity {

    @BindView(R.id.emailId)
    EditText emailId;
    @BindView(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.signInButton, R.id.signUpButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signInButton:

                break;
            case R.id.signUpButton:

                break;
        }
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

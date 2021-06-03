package com.example.goingplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Login extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;




    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    @InjectView(R.id.link_signup)
    TextView _signupLink;
    @InjectView(R.id.txtForgot)
    TextView _forgotLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);


        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        firebaseAuthListener = new com.google.firebase.auth.FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@androidx.annotation.NonNull com.google.firebase.auth.FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            }
        };

        _forgotLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this,ForgotPassword.class);
                startActivity(intent);
            }
        });

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();

            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivityForResult(intent, REQUEST_SIGNUP);

            }
        });

    }


    public void login() {
        Log.d(TAG, "login");

        if (!validate()) {
            onLoginFailed();
            return;
        }else{

            _loginButton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                    R.style.Theme_AppCompat_DayNight_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            String email = _emailText.getText().toString();
            String password = _passwordText.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@androidx.annotation.NonNull com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> task) {
                    // Telling the user that the sign in Failed

                    if (task.isSuccessful()) {

                        Intent intent = new Intent(Login.this, MapsA.class);
                        startActivity(intent);

                    }else {
                        android.widget.Toast.makeText(Login.this, "Wrong User name Or password", android.widget.Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }
                }
            });

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call onLoginSuccess
                            progressDialog.dismiss();
                            onLoginSuccess();


                        }
                    }, 3000);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // By this we go in to the account
                Intent intent = new Intent(Login.this,Signup.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}

package com.example.goingplaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.FloatProperty;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class ForgotPassword extends AppCompatActivity {

    private Button Find;

    private EditText email;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private DatabaseReference mCustomerDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        mAuth = FirebaseAuth.getInstance();


        Find = (Button)findViewById(R.id.FindPass);

        email = (EditText)findViewById(R.id.txtEmail) ;

        Find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();

                mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (!task.isSuccessful()){

                            android.widget.Toast.makeText(ForgotPassword.this, "This email Has not been registerd with the system", android.widget.Toast.LENGTH_SHORT).show();

                        }else{
                            android.widget.Toast.makeText(ForgotPassword.this, "Your password has been sent to your email", android.widget.Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                if (mail.isEmpty()){

                    email.setError("Please enter a valid email address");
                }else {



                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();

                final ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this,
                        R.style.Theme_AppCompat_DayNight_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Logging User out ...");
                progressDialog.show();

                new Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call  onLogout Success
                                Toast.makeText(getBaseContext(), "You Have been logged Out ", Toast.LENGTH_SHORT);
                                progressDialog.dismiss();
                                Intent intent = new Intent(ForgotPassword.this, Login.class);
                                startActivity(intent);


                            }
                        }, 3000);
                return true;

            case R.id.profile:
                Intent intent0 = new Intent(ForgotPassword.this, Profile.class);
                startActivity(intent0);
                return true;
            case R.id.List:
                Intent intent1 = new Intent(ForgotPassword.this, ForgotPassword.class);//FavouritCities
                startActivity(intent1);
                return true;
            case R.id.settings:
                Intent intent9= new Intent(ForgotPassword.this, Settings.class);
                startActivity(intent9);
                return true;
            case R.id.satellite:

                return true;
            case R.id.normal:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

}
package com.example.goingplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button Back;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Spinner spinner1 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Matrics, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);


        Back = (Button)findViewById(R.id.btnBack);



        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, MapsA.class);
                startActivity(intent);
                Toast.makeText(getBaseContext(), "Settings are Updated", Toast.LENGTH_LONG).show();

            }
        });


    }
    String gender ;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String text = parent.getItemAtPosition(position).toString();
        if (text.equals("metric")) {

            Toast.makeText(parent.getContext(), "You will be using Kilo meters (km) ", Toast.LENGTH_SHORT).show();
        }
        if (text.equals("imperial")){
            Toast.makeText(parent.getContext(), "You will be using Miles (mil) ", Toast.LENGTH_SHORT).show();
        }
        if (text.equals("Historical")){
            Toast.makeText(parent.getContext(), "Showing Historical places ", Toast.LENGTH_SHORT).show();
        }
        if (text.equals("Modern")){
            Toast.makeText(parent.getContext(), "Showing Modern places ", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
package com.example.goingplaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class FavouriteCities extends AppCompatActivity {
// Add list of users favourite cities
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_cities);


        MyListData[] myListData = new MyListData[] {
                new MyListData("Botanical garden ", R.drawable.botenical ),
                new MyListData("TheColosseum", R.drawable.colosseum),
                new MyListData("ThePalace Of France", R.drawable.palacefrance),
                new MyListData("WaterFront", R.drawable.waterfront),
                new MyListData("GrandLine", R.drawable.download),
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleV);
        Adapter adapter = new Adapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}


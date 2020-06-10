package com.example.fruitmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ListaTipos extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private EAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tipos);

        ArrayList<Item> list_items = new ArrayList<>();
        list_items.add(new Item(R.drawable.cherry, "Cerejeira"));
        list_items.add(new Item(R.drawable.orange, "Laranjeira"));
        list_items.add(new Item(R.drawable.coconut, "Coqueira"));

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new EAdapter(list_items);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
    public void changeItem(int position, String text){
        list_items.get(position).changeclicked(text);
    }
}
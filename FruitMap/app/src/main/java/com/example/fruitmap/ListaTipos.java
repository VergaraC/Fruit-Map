package com.example.fruitmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import java.util.ArrayList;

public class ListaTipos extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private EAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Item> list_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tipos);

        Bundle bundle = getIntent().getExtras();
        final double lLat = bundle.getDouble("latitude");
        final double lLong = bundle.getDouble("longitude");

        list_items = new ArrayList<>();
        list_items.add(new Item(R.drawable.cherry, "Cerejeira"));
        list_items.add(new Item(R.drawable.orange, "Laranjeira"));
        list_items.add(new Item(R.drawable.coconut, "Coqueiro"));

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new EAdapter(list_items);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListerner(new EAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("TESTE pf");
                String tipo = list_items.get(position).changeclicked("fam");
                mAdapter.notifyItemChanged(position);
                Intent intent = new Intent(ListaTipos.this, Cadastro.class);

                intent.putExtra("Tipo", tipo);
                intent.putExtra("latitude", lLat);
                intent.putExtra("longitude", lLong);

                System.out.println("Tipo: " + tipo);


                startActivity(intent);

            }
        });

    }
    public void changeItem(int position, String text){
        list_items.get(position).changeclicked(text);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.escrever, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
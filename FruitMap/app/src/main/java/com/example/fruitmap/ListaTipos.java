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

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

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
        list_items.add(new Item(R.drawable.avocado, "Abacateiro"));
        list_items.add(new Item(R.drawable.plum, "Ameixeira"));
        list_items.add(new Item(R.drawable.raspberry, "Amoreira"));
        list_items.add(new Item(R.drawable.banana, "Bananeira"));
        list_items.add(new Item(R.drawable.cocoa, "Cacaueiro"));
        list_items.add(new Item(R.drawable.starfruit, "Caramboleira"));
        list_items.add(new Item(R.drawable.brazilnut, "Castanheiro"));
        list_items.add(new Item(R.drawable.cherry, "Cerejeira"));
        list_items.add(new Item(R.drawable.coconut, "Coqueiro"));
        list_items.add(new Item(R.drawable.apricot, "Damasqueiro"));
        list_items.add(new Item(R.drawable.fig, "Figueira"));
        list_items.add(new Item(R.drawable.durian, "Fruta do Conde"));
        list_items.add(new Item(R.drawable.guava, "Goiabeira"));
        list_items.add(new Item(R.drawable.jabuticaba, "Jabuticabeira"));
        list_items.add(new Item(R.drawable.jackfruit, "Jaqueira"));
        list_items.add(new Item(R.drawable.kiwi, "Kiwizeiro"));
        list_items.add(new Item(R.drawable.orange, "Laranjeira"));
        list_items.add(new Item(R.drawable.lychee, "Lichieira"));
        list_items.add(new Item(R.drawable.lime, "Limoeiro"));
        list_items.add(new Item(R.drawable.siciliano, "Limoeiro Siciliano"));
        list_items.add(new Item(R.drawable.apple, "Macieira"));
        list_items.add(new Item(R.drawable.papaya, "Mamoeiro"));
        list_items.add(new Item(R.drawable.mango, "Mangueira"));
        list_items.add(new Item(R.drawable.passion_fruit, "Maracujazeiro"));
        list_items.add(new Item(R.drawable.melon, "Meloeiro"));
        list_items.add(new Item(R.drawable.blueberry, "Mirtilos"));
        list_items.add(new Item(R.drawable.strawberry, "Morangueiro"));
        list_items.add(new Item(R.drawable.pears, "Pereira"));
        list_items.add(new Item(R.drawable.peach, "Pessegueiro"));
        list_items.add(new Item(R.drawable.paprika, "Pimenteiro"));
        list_items.add(new Item(R.drawable.dragon_fruit, "Pitaya"));
        list_items.add(new Item(R.drawable.tamarindo, "Tamarindeiro"));



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
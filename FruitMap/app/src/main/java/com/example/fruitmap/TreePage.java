package com.example.fruitmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class TreePage extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference trees;

    TextView tipo;
    TextView address1;
    TextView address2;
    TextView distancia;

    RatingBar acesso;
    RatingBar qualidade;
    RatingBar quantidade;

    Geocoder geocoder;

    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_page);

        geocoder = new Geocoder(this, Locale.getDefault());

        database = FirebaseDatabase.getInstance();
        trees = database.getReference("trees");

        tipo = findViewById(R.id.tipo_t);
        address1 = findViewById(R.id.address1_t);
        address2 = findViewById(R.id.address2_t);
        distancia = findViewById(R.id.d_t);
        qualidade = findViewById(R.id.R_qualiW);
        acesso = findViewById(R.id.R_acessoW);
        quantidade = findViewById(R.id.R_quantW);

        Bundle bundle = getIntent().getExtras();
        final String treeId = bundle.getString("id");
        int distance = bundle.getInt("distance");
        System.out.println("distancia na TreePage "+distance);

        final int distFinal = distance/1000;

        trees.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tree : dataSnapshot.getChildren()) {

                    if (tree.getKey().equals(treeId)) {
                        Tree arvore = tree.getValue(Tree.class);
                        tipo.setText(arvore.getTipo());

                        try {
                            addresses = geocoder.getFromLocation(arvore.getLat(), arvore.getLongi(), 1);
                            Address address = addresses.get(0);
                            String result = address.getAddressLine(0);
                            int index = result.indexOf("-");

                            distancia.setText("Distância: " + distFinal + "km");

                            try {
                                String text = result.substring(0, index);
                                String text2 = result.substring(index+2);
                                address1.setText(text);
                                address2.setText(text2);

                            }catch (Exception e){
                                index = result.indexOf(",");
                                String text = result.substring(0, index);
                                String text2 = result.substring(index+2);
                                address1.setText(text);
                                address2.setText(text2);
                            }


                        } catch (IOException e){
                            e.printStackTrace();
                            Toast.makeText(TreePage.this,"Endereço nao encontrado", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
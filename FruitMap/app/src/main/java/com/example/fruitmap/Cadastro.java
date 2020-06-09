package com.example.fruitmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cadastro extends AppCompatActivity {

    double rating_quali;
    double rating_quant;
    double rating_acesso;

    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("trees");

        final Spinner tipo = findViewById(R.id.tipo);

        final RatingBar quali = findViewById(R.id.R_quali_fruta);
        final RatingBar acesso = findViewById(R.id.R_acesso);
        final RatingBar quant = findViewById(R.id.R_quant_fruta);
        final EditText extra = findViewById(R.id.extra);
        final Button cadastrar = findViewById(R.id.localizacao);

        Bundle bundle = getIntent().getExtras();
        final double lLat = bundle.getDouble("latitude");
        final double lLong = bundle.getDouble("longitude");

        System.out.println("Cadastro latitude: " + lLat);
        System.out.println("Cadastro longitude: " + lLong);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tipoCadastro = tipo.getSelectedItem().toString();

                rating_quali = quali.getRating();
                rating_quant = quant.getRating();
                rating_acesso = acesso.getRating();
                String comentario = extra.getText().toString();

                Tree arvore = new Tree(comentario, tipoCadastro, rating_acesso, rating_quant, rating_quali, lLat, lLong);

                String id = ref.push().getKey();
                ref.child(id).setValue(arvore);

                // if (DEU CERTO){
                Intent intent = new Intent(Cadastro.this, MapActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Cadastro concluido, obrigado! ", Toast.LENGTH_SHORT).show();
                /* }else{
                Toast.makeText(getApplicationContext(), "Ocorreu alguma problema de criar o cadastro! Certifique-se que esta conectado a Internet! ", Toast.LENGTH_SHORT).show();
            } */
            }
        });
    }
}

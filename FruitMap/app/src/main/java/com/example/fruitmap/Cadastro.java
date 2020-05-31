package com.example.fruitmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        final Spinner tipo = findViewById(R.id.tipo);
        final Spinner quali = findViewById(R.id.quali_fruta);
        final Spinner acesso = findViewById(R.id.acesso);
        final Spinner quant = findViewById(R.id.quant_fruta);

        Button cadastrar = findViewById(R.id.localizacao);

        final TextView textLatitude = findViewById(R.id.latitude);
        final TextView textLongitude = findViewById(R.id.longitude);

        Bundle bundle = getIntent().getExtras();
        double lLat = bundle.getDouble("latitude");
        double lLong = bundle.getDouble("longitude");

        System.out.println("Cadastro latitude: " + lLat);
        System.out.println("Cadastro longitude: " + lLong);

        textLatitude.setText(String.valueOf(lLat));
        textLongitude.setText(String.valueOf(lLong));

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.labels_acesso, android.R.layout.simple_spinner_item);
        acesso.setAdapter(adapter);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String acessoCadastro = acesso.getSelectedItem().toString();
                String qualiCadastro = quali.getSelectedItem().toString();
                String quantCadastro = quant.getSelectedItem().toString();
                String tipoCadastro = tipo.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "O item selecionado foi "+ acessoCadastro + ", " + qualiCadastro + ", " + quantCadastro + ", " + tipoCadastro, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Cadastro.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }
}

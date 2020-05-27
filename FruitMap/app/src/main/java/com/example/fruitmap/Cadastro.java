package com.example.fruitmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        Spinner tipo = (Spinner) findViewById(R.id.tipo);
        // tipo click listener
        tipo.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        Spinner acesso = (Spinner) findViewById(R.id.acesso);
        // acesso click listener
        acesso.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        Spinner quant = (Spinner) findViewById(R.id.quant_fruta);
        // quant click listener
        quant.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        Spinner quali = (Spinner) findViewById(R.id.quali_fruta);
        // quali click listener
        quali.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

    }

    //@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

}

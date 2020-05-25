package com.example.fruitmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    public void startCadastroActivity() {
        Intent startCadastroActivity = new Intent(this, Cadastro.class);
        startActivity(startCadastroActivity);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button cad = findViewById(R.id.cadastro);

        cad.setOnClickListener((view) -> {
            startCadastroActivity();
        });
    }
    }
}

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Cadastro extends AppCompatActivity {

    Float rating_quali;
    Float rating_quant;
    Float rating_acesso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        final Spinner tipo = findViewById(R.id.tipo);

        final RatingBar quali = findViewById(R.id.R_quali_fruta);
        final RatingBar acesso = findViewById(R.id.R_acesso);
        final RatingBar quant = findViewById(R.id.R_quant_fruta);
        final EditText extra = findViewById(R.id.extra);
        final Button cadastrar = findViewById(R.id.localizacao);

        //ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.labels_acesso, android.R.layout.simple_spinner_item);
        //acesso.setAdapter(adapter);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tipoCadastro = tipo.getSelectedItem().toString();

                rating_quali = quali.getRating();
                rating_quant = quant.getRating();
                rating_acesso = acesso.getRating();
                String comentario = extra.getText().toString();

                Tree arvore = new Tree(comentario, tipoCadastro,rating_acesso,rating_quant,rating_quali);

                Intent intent = new Intent(Cadastro.this, MainActivity.class);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "O item selecionado foi " + tipoCadastro + ", " +comentario + ", " + rating_acesso + ", " + rating_quali + ", " + rating_quant, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Cadastro concluido, obrigado! ",Toast.LENGTH_SHORT).show();

            }
        });
    }
}

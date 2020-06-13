package com.example.fruitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cadastro extends AppCompatActivity {

    double rating_quali;
    double rating_quant;
    double rating_acesso;

    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;

    private String lastPath;

    FirebaseDatabase db;
    DatabaseReference ref;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("trees");

        storageReference = FirebaseStorage.getInstance().getReference();

        final Spinner tipo = findViewById(R.id.tipo);

        final RatingBar quali = findViewById(R.id.R_quali_fruta);
        final RatingBar acesso = findViewById(R.id.R_acesso);
        final RatingBar quant = findViewById(R.id.R_quant_fruta);
        final EditText extra = findViewById(R.id.extra);
        final Button cadastrar = findViewById(R.id.localizacao);

        final Button botaoFoto = findViewById(R.id.ButtonPhoto);

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
                // = fotoArvore.getImage();

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

        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions();
            }
        });
    }

    private void askCameraPermissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else{
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            } else{
                Toast.makeText(this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView fotoArvore = findViewById(R.id.fotoarvore);
            fotoArvore.setImageBitmap(image);
        }
    }
}

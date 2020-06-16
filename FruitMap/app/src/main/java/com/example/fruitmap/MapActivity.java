package com.example.fruitmap;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    double lLat = 0;
    double lLong = 0;
    double endLat = 0;
    double endLong = 0;

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final float DEFAULT_ZOOM = 15f;

    private Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    FirebaseDatabase database;
    DatabaseReference trees;
    Button typeFilterButton, gradeFilterButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        typeFilterButton = findViewById(R.id.buttonType);
        gradeFilterButton = findViewById(R.id.buttonGrade);

        getLocationPermission();

        /* Essas variaveis sao para conexao com o banco de dados */

        database = FirebaseDatabase.getInstance();
        trees = database.getReference("trees");

        /* ========================================================== */

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        trees.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tree : dataSnapshot.getChildren()){
                    Tree arvore = tree.getValue(Tree.class);

                    LatLng location = new LatLng(arvore.getLat(), arvore.getLongi());
                  
                    MarkerOptions marker = new MarkerOptions().position(location).title(arvore.getTipo());

                    if (arvore.getTipo().equals("Ameixeira")){
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.plum));
                    } else if (arvore.getTipo().equals("Amoreira")){
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.raspberry));
                    } else if (arvore.getTipo().equals("Cerejeira")){
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.cherry));
                    } else if (arvore.getTipo().equals("Coqueiro")){
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.coconut));
                    }else if (arvore.getTipo().equals("Goiabeira")){
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.guava));
                    }else if (arvore.getTipo().equals("Jabuticabeira")){
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.jabuticaba));
                    }else if (arvore.getTipo().equals("Jaqueira")){
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.jackfruit));
                    } else if (arvore.getTipo().equals("Kiwizeiro")){
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.kiwi));
                    } else if (arvore.getTipo().equals("Laranjeira")){
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.orange));
                    }else if (arvore.getTipo().equals("Limoeiro")){
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.lime));
                    }else if (arvore.getTipo().equals("Limoeiro Siciliano")) {
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.siciliano));
                    }else if (arvore.getTipo().equals("Macieira")){
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.apple));
                    }else if (arvore.getTipo().equals("Mangueira")){
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mango));
                    } else if (arvore.getTipo().equals("Pessegueiro")){
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.peach));
                    } else if (arvore.getTipo().equals("Tamarindeiro")){
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.tamarindo));
                    }else{
                        System.out.println("Sem icon");
                    }

                    mMap.addMarker(marker).setTag(tree.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Metodo getDeviceLocation() transferido

        if(mLocationPermissionGranted){
            Log.d(TAG, "getDeviceLocation: getting the devices current location");

            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            try{
                if (mLocationPermissionGranted){
                    Task location = mFusedLocationProviderClient.getLastLocation();
                    location.addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                Log.d(TAG, "onComplete: found location!");
                                Location currentLocation = (Location) task.getResult();

                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                        DEFAULT_ZOOM);

                                lLat = currentLocation.getLatitude();
                                lLong = currentLocation.getLongitude();

                            } else {
                                Log.d(TAG, "onComplete: current location is null");
                                Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }catch (SecurityException e){
                Log.e(TAG, "getDeviceLocation: SecurityException" + e.getMessage());
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            mMap.setMyLocationEnabled(true);
        }

        final AlertDialog.Builder typeFilterDialog = new AlertDialog.Builder(this);
        typeFilterDialog.setTitle("Filtrar por tipo");
        typeFilterDialog.setItems(R.array.trees, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String[] treesArray =  getResources().getStringArray(R.array.trees);
                final String selected = treesArray[i];
                System.out.println(selected);
                mMap.clear();
                if (selected.equalsIgnoreCase("all")) {
                    trees.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot tree : dataSnapshot.getChildren()){
                                Tree arvore = tree.getValue(Tree.class);

                                LatLng location = new LatLng(arvore.getLat(), arvore.getLongi());
                              
                                MarkerOptions marker = new MarkerOptions().position(location).title(arvore.getTipo());

                                if (arvore.getTipo().equals("Ameixeira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.plum));
                                } else if (arvore.getTipo().equals("Amoreira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.raspberry));
                                } else if (arvore.getTipo().equals("Cerejeira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.cherry));
                                } else if (arvore.getTipo().equals("Coqueiro")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.coconut));
                                }else if (arvore.getTipo().equals("Goiabeira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.guava));
                                }else if (arvore.getTipo().equals("Jabuticabeira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.jabuticaba));
                                }else if (arvore.getTipo().equals("Jaqueira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.jackfruit));
                                } else if (arvore.getTipo().equals("Kiwizeiro")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.kiwi));
                                } else if (arvore.getTipo().equals("Laranjeira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.orange));
                                }else if (arvore.getTipo().equals("Limoeiro")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.lime));
                                }else if (arvore.getTipo().equals("Limoeiro Siciliano")) {
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.siciliano));
                                }else if (arvore.getTipo().equals("Macieira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.apple));
                                }else if (arvore.getTipo().equals("Mangueira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mango));
                                } else if (arvore.getTipo().equals("Pessegueiro")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.peach));
                                } else if (arvore.getTipo().equals("Tamarindeiro")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.tamarindo));
                                }else{
                                    System.out.println("Sem icon");
                                }

                                mMap.addMarker(marker).setTag(tree.getKey());                              
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    mMap.clear();
                    trees.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot tree : dataSnapshot.getChildren()){
                                Tree arvore = tree.getValue(Tree.class);

                                if (arvore.getTipo().equalsIgnoreCase(selected)) {
                                    LatLng location = new LatLng(arvore.getLat(), arvore.getLongi());
                                    MarkerOptions marker = new MarkerOptions().position(location).title(arvore.getTipo());

                                    if (arvore.getTipo().equals("Ameixeira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.plum));
                                    } else if (arvore.getTipo().equals("Amoreira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.raspberry));
                                    } else if (arvore.getTipo().equals("Cerejeira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.cherry));
                                    } else if (arvore.getTipo().equals("Coqueiro")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.coconut));
                                    }else if (arvore.getTipo().equals("Goiabeira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.guava));
                                    }else if (arvore.getTipo().equals("Jabuticabeira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.jabuticaba));
                                    }else if (arvore.getTipo().equals("Jaqueira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.jackfruit));
                                    } else if (arvore.getTipo().equals("Kiwizeiro")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.kiwi));
                                    } else if (arvore.getTipo().equals("Laranjeira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.orange));
                                    }else if (arvore.getTipo().equals("Limoeiro")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.lime));
                                    }else if (arvore.getTipo().equals("Limoeiro Siciliano")) {
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.siciliano));
                                    }else if (arvore.getTipo().equals("Macieira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.apple));
                                    }else if (arvore.getTipo().equals("Mangueira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mango));
                                    } else if (arvore.getTipo().equals("Pessegueiro")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.peach));
                                    } else if (arvore.getTipo().equals("Tamarindeiro")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.tamarindo));
                                    }else{
                                        System.out.println("Sem icon");
                                    }

                                    mMap.addMarker(marker).setTag(tree.getKey()); 
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        final AlertDialog.Builder gradeFilterDialog = new AlertDialog.Builder(this);
        gradeFilterDialog.setTitle("Filtrar por nota");
        gradeFilterDialog.setItems(R.array.grades, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String[] grades =  getResources().getStringArray(R.array.grades);
                final String selected = grades[i];
                System.out.println(selected);
                mMap.clear();
                if (selected.equalsIgnoreCase("all")) {
                    trees.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot tree : dataSnapshot.getChildren()){
                                Tree arvore = tree.getValue(Tree.class);

                                LatLng location = new LatLng(arvore.getLat(), arvore.getLongi());
                              
                                MarkerOptions marker = new MarkerOptions().position(location).title(arvore.getTipo());

                                if (arvore.getTipo().equals("Ameixeira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.plum));
                                } else if (arvore.getTipo().equals("Amoreira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.raspberry));
                                } else if (arvore.getTipo().equals("Cerejeira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.cherry));
                                } else if (arvore.getTipo().equals("Coqueiro")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.coconut));
                                }else if (arvore.getTipo().equals("Goiabeira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.guava));
                                }else if (arvore.getTipo().equals("Jabuticabeira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.jabuticaba));
                                }else if (arvore.getTipo().equals("Jaqueira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.jackfruit));
                                } else if (arvore.getTipo().equals("Kiwizeiro")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.kiwi));
                                } else if (arvore.getTipo().equals("Laranjeira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.orange));
                                }else if (arvore.getTipo().equals("Limoeiro")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.lime));
                                }else if (arvore.getTipo().equals("Limoeiro Siciliano")) {
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.siciliano));
                                }else if (arvore.getTipo().equals("Macieira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.apple));
                                }else if (arvore.getTipo().equals("Mangueira")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mango));
                                } else if (arvore.getTipo().equals("Pessegueiro")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.peach));
                                } else if (arvore.getTipo().equals("Tamarindeiro")){
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.tamarindo));
                                }else{
                                    System.out.println("Sem icon");
                                }

                                mMap.addMarker(marker).setTag(tree.getKey()); 
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    mMap.clear();
                    trees.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot tree : dataSnapshot.getChildren()){
                                Tree arvore = tree.getValue(Tree.class);


                                int num1 = Integer.parseInt(selected.split("-")[0]);
                                int num2 = Integer.parseInt(selected.split("-")[1]);
                                System.out.println(num1);
                                System.out.println(num2);
                                System.out.println(arvore.getGrade());
                                if (arvore.getGrade() >= num1 && arvore.getGrade() <= num2) {
                                    LatLng location = new LatLng(arvore.getLat(), arvore.getLongi());
                                    MarkerOptions marker = new MarkerOptions().position(location).title(arvore.getTipo());

                                    if (arvore.getTipo().equals("Ameixeira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.plum));
                                    } else if (arvore.getTipo().equals("Amoreira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.raspberry));
                                    } else if (arvore.getTipo().equals("Cerejeira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.cherry));
                                    } else if (arvore.getTipo().equals("Coqueiro")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.coconut));
                                    }else if (arvore.getTipo().equals("Goiabeira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.guava));
                                    }else if (arvore.getTipo().equals("Jabuticabeira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.jabuticaba));
                                    }else if (arvore.getTipo().equals("Jaqueira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.jackfruit));
                                    } else if (arvore.getTipo().equals("Kiwizeiro")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.kiwi));
                                    } else if (arvore.getTipo().equals("Laranjeira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.orange));
                                    }else if (arvore.getTipo().equals("Limoeiro")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.lime));
                                    }else if (arvore.getTipo().equals("Limoeiro Siciliano")) {
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.siciliano));
                                    }else if (arvore.getTipo().equals("Macieira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.apple));
                                    }else if (arvore.getTipo().equals("Mangueira")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mango));
                                    } else if (arvore.getTipo().equals("Pessegueiro")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.peach));
                                    } else if (arvore.getTipo().equals("Tamarindeiro")){
                                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.tamarindo));
                                    }else{
                                        System.out.println("Sem icon");
                                    }

                                    mMap.addMarker(marker).setTag(tree.getKey()); 
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        typeFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeFilterDialog.show();
            }
        });

        gradeFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gradeFilterDialog.show();
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String id = (String) marker.getTag();

                double endLat = marker.getPosition().latitude;
                double endLong = marker.getPosition().longitude;

                int distancia = 0;

                float distances[] = new float[10];
                Location.distanceBetween(lLat, lLong, endLat, endLong, distances);

                distancia = (int) (distances[0]);

                Intent intent = new Intent(MapActivity.this, TreePage.class);

                intent.putExtra("distance", distancia);

                intent.putExtra("id", id);

                System.out.println("distance no Map "+ distances[0]);
                System.out.println("id:" + id);

                startActivity(intent);

                return false;
            }
        });


        ImageButton btnCadastro = findViewById(R.id.buttonCadastro);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MapActivity.this, ListaTipos.class);

                intent.putExtra("latitude", lLat);
                intent.putExtra("longitude", lLong);

                System.out.println("MapActivity latitude: " + lLat);
                System.out.println("MapActivity longitude: " + lLong);

                startActivity(intent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.map);
        View mapView = mapFragment.getView();
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();

        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 100, 370);
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " +latLng.latitude + ", lng: "+ latLng.longitude);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        Log.d(TAG, "onMapReady: initializing map");
        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permission");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0){
                    for (int i = 0; i < grantResults.length; i++){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionGranted = true;
                    //initialize map
                    initMap();
                }
            }
        }
    }

}

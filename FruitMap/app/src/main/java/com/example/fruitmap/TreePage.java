package com.example.fruitmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TreePage extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference trees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_page);

        database = FirebaseDatabase.getInstance();
        trees = database.getReference("trees");

        Bundle bundle = getIntent().getExtras();
        final String treeId = bundle.getString("");

        trees.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tree : dataSnapshot.getChildren()) {
                    if (tree.getKey() == treeId) {
                        Tree arvore = tree.getValue(Tree.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
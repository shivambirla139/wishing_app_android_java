package com.notandroid.wishingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.QueryListener;
import com.notandroid.wishingapp.Adapter.WishAdapter;
import com.notandroid.wishingapp.Model.CategoryModel;
import com.notandroid.wishingapp.Model.WishModel;

import java.util.ArrayList;
import java.util.List;

public class WishActivity extends AppCompatActivity {

    TextView toolbarText;
    FirebaseFirestore db;
    RecyclerView rcWish;
    ImageView toolbarBackBtn;
    ProgressBar progressBar;
    ArrayList<WishModel> allWish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);

        String id = getIntent().getStringExtra("id");
        String categoryName = getIntent().getStringExtra("categoryName");
        toolbarText = findViewById(R.id.toolbarText);
        db = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);
        rcWish = findViewById(R.id.rcWish);
        toolbarBackBtn = findViewById(R.id.toolbarBackBtn);
        allWish = new ArrayList<WishModel>();
        allWish.clear();

        toolbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WishActivity.super.onBackPressed();
                finish();
            }
        });
        toolbarText.setText(categoryName.toString());

        rcWish.setLayoutManager(new LinearLayoutManager(WishActivity.this));
        WishAdapter wishAdapter = new WishAdapter(WishActivity.this,allWish);
        rcWish.setAdapter(wishAdapter);
        db.collection("category").document(id).collection("all")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressBar.setVisibility(View.GONE);
                allWish.addAll(queryDocumentSnapshots.toObjects(WishModel.class));
                wishAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WishActivity.this,"Failed",Toast.LENGTH_SHORT).show();

            }
        });


    }
}
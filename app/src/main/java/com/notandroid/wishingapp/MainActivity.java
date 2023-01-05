package com.notandroid.wishingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.notandroid.wishingapp.Adapter.CategoryAdapter;
import com.notandroid.wishingapp.Model.CategoryModel;
import com.notandroid.wishingapp.Model.WishModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    ImageView navMenuBtn;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    RecyclerView rcCategory;
    FirebaseFirestore db;
    ProgressBar progressBar;
    ArrayList<CategoryModel> allCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navMenuBtn = findViewById(R.id.navMenuBtn);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.draweLayout);
        rcCategory = findViewById(R.id.rcCategory);
        progressBar = findViewById(R.id.progressBar);
        db = FirebaseFirestore.getInstance();
        allCategory = new ArrayList<>();
        allCategory.clear();
        navMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }else{
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_shareapp) {
                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                        String shareMessage= "\nBest Wishing App \n\n";
                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        startActivity(Intent.createChooser(shareIntent, "choose one"));
                    } catch(Exception e) {
                        //e.toString();
                    }
                    drawerLayout.closeDrawer(Gravity.LEFT);

                    return true;
                } else if (id == R.id.nav_more) {
                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                    drawerLayout.closeDrawer(Gravity.LEFT);

                    return true;

                } else if (id == R.id.nav_rate) {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        startActivity(myAppLinkToMarket);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(MainActivity.this, " unable to find market app", Toast.LENGTH_LONG).show();
                    }
                    drawerLayout.closeDrawer(Gravity.LEFT);

                    return true;

                }
                return true;
            }
        });

        rcCategory.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        CategoryAdapter categoryAdapter = new CategoryAdapter(MainActivity.this,allCategory);
        rcCategory.setAdapter(categoryAdapter);
        db.collection("category").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressBar.setVisibility(View.GONE);
                allCategory.addAll(queryDocumentSnapshots.toObjects(CategoryModel.class));
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }
}
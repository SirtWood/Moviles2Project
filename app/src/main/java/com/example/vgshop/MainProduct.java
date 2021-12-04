package com.example.vgshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.vgshop.Adapters.ProductAdapter;
import com.example.vgshop.Entities.Product;
import com.example.vgshop.databinding.ActivityMainProductBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainProduct extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainProductBinding mainProductBinding;
    private FirebaseFirestore db;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;
    String role;
    String shopName;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainProductBinding = ActivityMainProductBinding.inflate(getLayoutInflater());
        View view = mainProductBinding.getRoot();
        setContentView(view);
        db = FirebaseFirestore.getInstance();
        productArrayList = new ArrayList<>();
        readUserPreferences(this);
        Log.d("UserData", role);
        productAdapter = new ProductAdapter(this, productArrayList, db, role, shopName, email);
        mainProductBinding.rvProducts.setHasFixedSize(true);
        mainProductBinding.rvProducts.setLayoutManager(new LinearLayoutManager(this));
        mainProductBinding.rvProducts.setAdapter(productAdapter);
        getProducts();

        mainProductBinding.btnAddNewProduct.setOnClickListener(this);

        if(role.equals("user")){
            mainProductBinding.lyBottom.setVisibility(View.INVISIBLE);
            mainProductBinding.guideline12.setGuidelinePercent(1);
        }
    }

    public void getProducts() {
        if(role.equals("seller")){
            db.collection("products").whereEqualTo("shopName", shopName)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Toast.makeText(getApplicationContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            for (DocumentChange dc : value.getDocumentChanges()){
                                if(dc.getType() == DocumentChange.Type.ADDED){
                                    productArrayList.add(dc.getDocument().toObject(Product.class));
                                }
                            }
                            productAdapter.notifyDataSetChanged();
                        }
                    });
        }
        else{
            db.collection("products")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Toast.makeText(getApplicationContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            for (DocumentChange dc : value.getDocumentChanges()){
                                if(dc.getType() == DocumentChange.Type.ADDED){
                                    productArrayList.add(dc.getDocument().toObject(Product.class));
                                }
                            }
                            productAdapter.notifyDataSetChanged();
                        }
                    });
        }
    }
   public void onClick(View view) {
        if (view.getId()==mainProductBinding.btnAddNewProduct.getId()){
                Intent intent = new Intent(this, AddProduct.class);
                startActivity(intent);
                finish();
        }
    }

    public void readUserPreferences(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        boolean status = sharedPref.getBoolean("status",false);
        role = sharedPref.getString("role","user");
        shopName = sharedPref.getString("shop","shop");
    }

}
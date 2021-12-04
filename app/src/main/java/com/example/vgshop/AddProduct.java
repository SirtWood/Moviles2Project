package com.example.vgshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddProduct extends AppCompatActivity {

    private FirebaseFirestore db;
    EditText jetProductName, jetDescription, jetCategory, jetPrice, jetStock;
    Button jbtnAddProduct;
    String name, description, category, price, stock, productNameValues, descriptionValues, categoryValues, priceValues, stockValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        jetProductName = findViewById(R.id.etName);
        jetDescription = findViewById(R.id.etDescription);
        jetCategory = findViewById(R.id.etCategory);
        jetPrice= findViewById(R.id.etPrice);
        jetStock= findViewById(R.id.etStock);
        jbtnAddProduct = findViewById(R.id.btnAddProduct);
        db = FirebaseFirestore.getInstance();
    }

    public void jbtnAddProduct(View view) {
        name = jetProductName.getText().toString();
        description = jetDescription.getText().toString();
        category = jetCategory.getText().toString();
        price = jetPrice.getText().toString();
        stock = jetStock.getText().toString();


        Map <String, Object> product = new HashMap<>();
        product.put("name", name);
        product.put("description", description);
        product.put("category", category);
        product.put("price", Double.parseDouble(price));
        product.put("stock", Integer.parseInt(stock));
        db.collection("products")
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(
                                getApplicationContext(), "Successfully added product",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainProduct.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(
                        getApplicationContext(), "Product could not be added",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
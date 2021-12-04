package com.example.vgshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vgshop.Adapters.UserAdapter;
import com.example.vgshop.Entities.Product;
import com.example.vgshop.databinding.ActivityProductAddToCartBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProductAddToCart extends AppCompatActivity implements View.OnClickListener{

    private ActivityProductAddToCartBinding productAddToCartBinding;
    private Product product;
    private FirebaseFirestore db;
    UserAdapter userAdapter;
    String email;
    int stock;
    int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add_to_cart);

        productAddToCartBinding = ActivityProductAddToCartBinding.inflate(getLayoutInflater());
        View view = productAddToCartBinding.getRoot();
        setContentView(view);
        productAddToCartBinding.btnProductCartProduct.setOnClickListener(this);
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");
        db = FirebaseFirestore.getInstance();
        readUserPreferences(this);
        productAddToCartBinding.etNameCartProduct.setText(product.getName());
        productAddToCartBinding.etDescriptionCartProduct.setText(product.getDescription());
        productAddToCartBinding.etStockCartProduct.setText(String.valueOf(product.getStock()));
        productAddToCartBinding.etPriceCartProduct.setText(String.valueOf(product.getPrice()));
        productAddToCartBinding.etCategoryCartProduct.setText(product.getCategory());
        stock = product.getStock();
    }

    @Override
    public void onClick(View view) {

        Map<String, Object> dataProduct = new HashMap<>();
        dataProduct.put("name", productAddToCartBinding.etNameCartProduct.getText().toString());
        dataProduct.put("description", productAddToCartBinding.etDescriptionCartProduct.getText().toString());
        //dataProduct.put("stock", Integer.parseInt(productAddToCartBinding.etCategoryCartProduct.getText().toString()));
        dataProduct.put("price", Double.parseDouble(productAddToCartBinding.etPriceCartProduct.getText().toString()));
        dataProduct.put("category", productAddToCartBinding.etStockCartProduct.getText().toString());
        dataProduct.put("amount", Double.parseDouble(productAddToCartBinding.etAmount.getText().toString()));
        dataProduct.put("email", email);
        //Hacer evento que reste el stock

            db.collection("shoppingCart")
                    .add(dataProduct)
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

    public void readUserPreferences(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        boolean status = sharedPref.getBoolean("status",false);
        email = sharedPref.getString("email","user");
    }
}
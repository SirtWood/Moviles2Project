package com.example.vgshop.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vgshop.EditProduct;
import com.example.vgshop.Entities.Product;
import com.example.vgshop.ProductAddToCart;
import com.example.vgshop.databinding.ProductItemBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private ProductItemBinding productItemBinding;
    //UserAdapter userAdapter;
    private ArrayList<Product> productArrayList;
    private FirebaseFirestore db;
    private String role;
    private String shopName;
    private String email;

    public ProductAdapter(Context context, ArrayList<Product> productArrayList,
                          FirebaseFirestore db, String role, String shopName, String email) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.db = db;
        this.role = role.toLowerCase();
        this.shopName = shopName;
        this.email = email;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        productItemBinding = ProductItemBinding.inflate(LayoutInflater.from(context));
        return new ProductViewHolder(productItemBinding);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readUserPreferences(getApplicationContext());
            }
        }, 3000);*/
    }

    public void readUserPreferences(Context context){

    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = productArrayList.get(position);
        holder.itemBinding.tvProductName0.setText(product.getName());
        holder.itemBinding.tvProductDescription0.setText(product.getDescription());
        holder.itemBinding.tvProductStock0.setText(String.valueOf(product.getStock()));
        holder.itemBinding.tvProductPrice0.setText(String.valueOf(product.getPrice()));
        holder.itemBinding.tvProductCategory0.setText(product.getCategory());
        /*SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        boolean status = sharedPref.getBoolean("status",false);
        String role = sharedPref.getString("role","user").toLowerCase(Locale.ROOT);*/
        if(role.equals("user")){
            holder.itemBinding.btnProductEdit.setVisibility(View.GONE);
            holder.itemBinding.btnProductDelete.setVisibility(View.GONE);
        }
        else{
            holder.itemBinding.btnProductAddToCart.setVisibility(View.GONE);
        }
        /*String role = "user".toLowerCase(Locale.ROOT);
        if(role.equals("user")){
            holder.itemBinding.btnProductEdit.setVisibility(View.GONE);
            holder.itemBinding.btnProductDelete.setVisibility(View.GONE);
        }*/
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.collection("products").document(product.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Data deleted",
                                        Toast.LENGTH_SHORT).show();
                                productArrayList.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Failed to delete item",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        holder.itemBinding.btnProductDelete.setOnClickListener(view -> {
            builder.setMessage("Are you sure to delete product?");
            builder.create().show();
        });
        holder.itemBinding.btnProductEdit.setOnClickListener(view ->{
            Intent intent = new Intent(context, EditProduct.class);
            intent.putExtra("product", product);
            context.startActivity(intent);
        });
        holder.itemBinding.btnProductAddToCart.setOnClickListener(view ->{
            Intent intent = new Intent(context, ProductAddToCart.class);
            intent.putExtra("product", product);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ProductItemBinding itemBinding;

        public ProductViewHolder(@NonNull ProductItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }


}

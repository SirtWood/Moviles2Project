package com.example.vgshop.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vgshop.Entities.Invoice;
import com.example.vgshop.databinding.InvoiceItemBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>{
    private Context context;
    private InvoiceItemBinding invoiceItemBinding;
    //UserAdapter userAdapter;
    private ArrayList<Invoice> invoiceArrayList;
    private FirebaseFirestore db;
    private String email;
    private double subtotal, price, total;
    private int amount;

    public InvoiceAdapter(Context context, ArrayList<Invoice> invoiceArrayList,
                          FirebaseFirestore db, String email) {
        this.context = context;
        this.invoiceArrayList = invoiceArrayList;
        this.db = db;
        this.email = email;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        invoiceItemBinding = InvoiceItemBinding.inflate(LayoutInflater.from(context));
        return new InvoiceViewHolder(invoiceItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceAdapter.InvoiceViewHolder holder, int position) {
        Invoice invoice = invoiceArrayList.get(position);
        holder.itemBinding.tvProductNameInvoice.setText(invoice.getName());
        holder.itemBinding.tvProductCategoryInvoice.setText(invoice.getCategory());
        //holder.itemBinding.tvProductDescriptionInvoice.setText(invoice.getDescription());
        holder.itemBinding.tvProductPriceInvoice.setText(String.valueOf(invoice.getPrice()));
        holder.itemBinding.tvProductAmountInvoice.setText(String.valueOf(invoice.getAmount()));
        price = invoice.getPrice();
        amount = invoice.getAmount();
        subtotal = price * amount;
        holder.itemBinding.tvProductTotalInvoice.setText(String.valueOf(subtotal));

        /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.collection("shoppingCart").document(invoice.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Data deleted",
                                        Toast.LENGTH_SHORT).show();
                                invoiceArrayList.remove(holder.getAdapterPosition());
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
        holder.itemBinding.btnInvoiceDelete.setOnClickListener(view -> {
            builder.setMessage("Are you sure to delete product?");
            builder.create().show();
        });
    }*/
    /*---------------------------------------*/

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.collection("shoppingCart").document(invoice.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Data deleted",
                                        Toast.LENGTH_SHORT).show();
                                invoiceArrayList.remove(holder.getAdapterPosition());
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
        holder.itemBinding.btnInvoiceDelete.setOnClickListener(view -> {
            builder.setMessage("Are you sure to delete product?");
            builder.create().show();
        });
    }

    @Override
    public int getItemCount() {
        return invoiceArrayList.size();
    }

    public class InvoiceViewHolder extends RecyclerView.ViewHolder {
        InvoiceItemBinding itemBinding;
        public InvoiceViewHolder(@NonNull InvoiceItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}

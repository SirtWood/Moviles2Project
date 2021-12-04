package com.example.vgshop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.vgshop.Adapters.InvoiceAdapter;
import com.example.vgshop.Entities.Invoice;
import com.example.vgshop.databinding.ActivityMainInvoiceBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainInvoice extends AppCompatActivity implements View.OnClickListener{

    private ActivityMainInvoiceBinding mainInvoiceBinding;
    private FirebaseFirestore db;
    ArrayList<Invoice> invoiceArrayList;
    InvoiceAdapter invoiceAdapter;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_invoice);

        mainInvoiceBinding = ActivityMainInvoiceBinding.inflate(getLayoutInflater());
        View view = mainInvoiceBinding.getRoot();
        setContentView(view);
        db = FirebaseFirestore.getInstance();
        invoiceArrayList = new ArrayList<>();
        readUserPreferences(this);
        //Log.d("UserData", role);
        invoiceAdapter = new InvoiceAdapter(this, invoiceArrayList, db, email);
        mainInvoiceBinding.rvInvoice.setHasFixedSize(true);
        mainInvoiceBinding.rvInvoice.setLayoutManager(new LinearLayoutManager(this));
        mainInvoiceBinding.rvInvoice.setAdapter(invoiceAdapter);
        getInvoices();

        mainInvoiceBinding.btnPay.setOnClickListener(this);
    }

    public void getInvoices() {
        db.collection("shoppingCart").whereEqualTo("email", email)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(getApplicationContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                invoiceArrayList.add(dc.getDocument().toObject(Invoice.class));
                            }
                        }
                        invoiceAdapter.notifyDataSetChanged();
                    }
                });
    }


    @Override
    public void onClick(View view) {

    }

    public void readUserPreferences(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        boolean status = sharedPref.getBoolean("status",false);
        email = sharedPref.getString("email","email");
    }
}
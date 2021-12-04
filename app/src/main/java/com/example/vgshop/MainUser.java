package com.example.vgshop;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.vgshop.Adapters.UserAdapter;
import com.example.vgshop.Entities.User;
import com.example.vgshop.databinding.ActivityMainUserBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainUser extends AppCompatActivity {

    private ActivityMainUserBinding mainUserBinding;
    private FirebaseFirestore db;
    ArrayList<User> userArrayList;
    UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainUserBinding = ActivityMainUserBinding.inflate(getLayoutInflater());
        View view = mainUserBinding.getRoot();
        setContentView(view);
        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<>();
        userAdapter = new UserAdapter(this, userArrayList);
        mainUserBinding.rvUsers.setHasFixedSize(true);
        mainUserBinding.rvUsers.setLayoutManager(new LinearLayoutManager(this));
        mainUserBinding.rvUsers.setAdapter(userAdapter);
        getUsers();

        //mainUserBinding.btnAddNewUser.setOnClickListener(this);
    }

    public void getUsers() {
        db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(getApplicationContext(), "Failed to retrieve data",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                userArrayList.add(dc.getDocument().toObject(User.class));
                            }
                        }
                        userAdapter.notifyDataSetChanged();
                    }
                });
    }
    /*public void onClick(View view) {
        if (view.getId()==mainUserBinding.btnAddNewUser.getId()){
            Intent intent = new Intent(this, AddProduct.class);
            startActivity(intent);
        }
    }*/
}

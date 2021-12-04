package com.example.vgshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class UserDashboardActivity extends AppCompatActivity implements View.OnClickListener{

    Button jbtnProductsList, jbtnShoppingCart, jbtnEditUserData, jbtnUserLogout;
    private FirebaseAuth mAuth;

    /* @Override
    public void onStart() {
        mAuth = FirebaseAuth.getInstance();
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    } */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readUserPreferences(getApplicationContext());
            }
        }, 3000);*/

        jbtnProductsList = findViewById(R.id.btnProductsList);
        jbtnShoppingCart = findViewById(R.id.btnShoppingCart);
        jbtnEditUserData = findViewById(R.id.btnEditUserData);
        jbtnUserLogout = findViewById(R.id.btnUserLogout);

        jbtnProductsList.setOnClickListener(this);
        jbtnShoppingCart.setOnClickListener(this);
        jbtnEditUserData.setOnClickListener(this);
        jbtnUserLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnProductsList:
                Intent intent1 = new Intent(this, MainProduct.class);
                startActivity(intent1);
                break;       //Boton para ir al Layout donde se van a listar los productos
             case R.id.btnShoppingCart:
                Intent intent2 = new Intent(this, MainInvoice.class);
                startActivity(intent2);
                break;      //Boton para ir al Layout donde se van a listar los productos agregados al carrito
            case R.id.btnEditUserData:
                Intent intent3 = new Intent(this, MainUser.class);
                startActivity(intent3);
                break;      //Boton para ir al Layout donde se pueden editar los datos del usuario
            case R.id.btnUserLogout:
                Intent intent4 = new Intent(this, MainActivity.class);
                startActivity(intent4);
                break;
        }
    }

    /*public void readUserPreferences(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        boolean status = sharedPref.getBoolean("status",false);
        String role = sharedPref.getString("role","user").toLowerCase(Locale.ROOT);
        if(status){
            Intent intent = new Intent(this, MainProduct2.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, LoginSelector.class);
            startActivity(intent);
        }
    }*/
}
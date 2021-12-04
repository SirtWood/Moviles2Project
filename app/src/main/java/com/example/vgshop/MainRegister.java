package com.example.vgshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainRegister extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    EditText jetUserName, jetEmail, jetCountry, jetCity, jetPassword, jetRole, jetShopName;
    Button jbtnRegisterGO;
    ImageView jivSettings;
    String userName, email, country, city, password, role, shopName, userNameValues, passValues, emailValues, countryValues, cityValues, roleValues, shopNameValues;
    RadioButton jrbUser, jrbSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        jetUserName = findViewById(R.id.etUserName);
        jetEmail = findViewById(R.id.etEmail);
        jetCountry = findViewById(R.id.etCountry);
        jetCity = findViewById(R.id.etCity);
        jetPassword = findViewById(R.id.etPassword2);
        jetRole = findViewById(R.id.etRole);
        jetShopName = findViewById(R.id.etShopName);
        jbtnRegisterGO = findViewById(R.id.btnRegisterGO);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        jrbUser = findViewById(R.id.rbUser);
        jrbSeller = findViewById(R.id.rbSeller);
    }

    public void shopName (View view) {
        if (jrbSeller.isChecked()) {
            jetShopName.setVisibility(View.VISIBLE);
            jetShopName.setText("");
        } else {
            jetShopName.setVisibility(View.GONE);
            jetShopName.setText("Not a Shop");
        }
    }

    public void jbtnRegisterGO(View view) {
        userName = jetUserName.getText().toString();
        email = jetEmail.getText().toString();
        country = jetCountry.getText().toString();
        city = jetCity.getText().toString();
        password = jetPassword.getText().toString();
        shopName = jetShopName.getText().toString();
        if (jrbUser.isChecked()) {
            role = "user";
        } else if (jrbSeller.isChecked()) {
            role = "seller";
        }

        userNameValues = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])(?=\\w*\\p{Punct})\\S{3,20}$";
        emailValues = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        countryValues = "^(\\w*){6,12}\\S$";
        cityValues = "^(\\w*){6,12}\\S$";
        passValues = "^(\\w*\\d)(\\w*[A-Z])(\\w*[a-z])(\\w*\\p{Punct})\\S{3,20}$";
        shopNameValues = "^(\\w*){6,12}\\S$";

        if (userName.isEmpty()) {
            Toast.makeText(this, "Required Field", Toast.LENGTH_LONG).show();
            jetUserName.requestFocus();
        } else if (userName.matches(userNameValues)) {
            Toast.makeText(this, "Please don't put special characters", Toast.LENGTH_LONG).show();
            jetUserName.requestFocus();
        } else if (userName.length() < 6 || userName.length() > 30) {
            Toast.makeText(this, "Name must be between 6 and 30 characters long", Toast.LENGTH_LONG).show();
            jetUserName.requestFocus();
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Required Field", Toast.LENGTH_LONG).show();
            jetEmail.requestFocus();
        } else if (!email.matches(emailValues)) {
            Toast.makeText(this, "Email format is not valid", Toast.LENGTH_LONG).show();
            jetEmail.requestFocus();
        } else if (country.isEmpty()) {
            Toast.makeText(this, "Required Field", Toast.LENGTH_LONG).show();
            jetCountry.requestFocus();
        } else if (!country.matches(countryValues)) {
            Toast.makeText(this, "Please don't put numbers or special characters", Toast.LENGTH_LONG).show();
            jetCountry.requestFocus();
        } else if (city.isEmpty()) {
            Toast.makeText(this, "Required Field", Toast.LENGTH_LONG).show();
            jetCity.requestFocus();
        } else if (!city.matches(cityValues)) {
            Toast.makeText(this, "Please don't put numbers or special characters", Toast.LENGTH_LONG).show();
            jetCity.requestFocus();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Required Field", Toast.LENGTH_LONG).show();
            jetPassword.requestFocus();
        } /*else if (!password.matches(passValues)) {
            Toast.makeText(this, "Password must have at least one capital letter, one number and one special character", Toast.LENGTH_LONG).show();
            jetPassword.requestFocus();
        }*/ else if (shopName.isEmpty()) {
            Toast.makeText(this, "Required Field", Toast.LENGTH_LONG).show();
            jetShopName.requestFocus();
        } else if (!shopName.matches(shopNameValues)) {
            Toast.makeText(this, "Please don't put numbers or special characters", Toast.LENGTH_LONG).show();
            jetShopName.requestFocus();
        /* } else if (role.equals("seller") || role.equals("Seller") || role.equals("SELLER")) {
            Toast.makeText(this, "Welcome, seller of: " + shopName, Toast.LENGTH_LONG).show(); */
        } else {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Map<String, Object> user = new HashMap<>();
                                user.put("userName", userName);
                                user.put("email", email);
                                user.put("country", country);
                                user.put("city", city);
                                user.put("role", role);
                                user.put("shopName", shopName);
                                db.collection("users")
                                        .add(user)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(
                                                        getApplicationContext(), "Successfully registered user",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(
                                                getApplicationContext(), "User could not be registered",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });

                                Toast.makeText(getApplicationContext(), "Account registered successfully, please try to Login", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(
                                        getApplicationContext(), "ERROR ERROR ERROR",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
}


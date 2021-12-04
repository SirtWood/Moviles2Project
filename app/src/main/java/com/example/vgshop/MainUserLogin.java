package com.example.vgshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vgshop.Adapters.UserAdapter;
import com.example.vgshop.Entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainUserLogin extends AppCompatActivity {

    EditText jetUser, jetPass;
    Button jbtnLoginGO;
    ImageView jivSettings;
    String user, pass, userValues, passValues, sp_character;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    ArrayList<User> userArrayList;
    UserAdapter userAdapter;

    //private ActivityLoginBinding loginBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        /*loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View v = loginBinding.getRoot();
        setContentView(v);
        saveUserPreferences(this);*/

        jetUser = findViewById(R.id.etUserName);
        jetPass = findViewById(R.id.etPassword2);
        jbtnLoginGO = findViewById(R.id.btnLoginGO);
        jivSettings = findViewById(R.id.ivSettings);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<>();
        //userAdapter = new UserAdapter(this, userArrayList);
        /*String role = userAdapter.getRole;
        String email = userAdapter.getEmail;
        String name = userAdapter.getName;*/
        //saveUserPreferences(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    public void jbtnLoginGO(View view) {
        user = jetUser.getText().toString();
        pass = jetPass.getText().toString();

        userValues = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"; //"^(\\w*){6,12}\\S$";
        passValues = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])(?=\\w*[@#$%^&+=*])\\S{8,20}$";
        sp_character = "^(?=\\w*\\p{Punct}\\S)$";

        if (!user.matches(userValues)) {
            Toast.makeText(this, "Please put your email, don't let blank, don't put special characters or spaces", Toast.LENGTH_LONG).show();
            jetUser.requestFocus();
        } else if (user.isEmpty()) {
            Toast.makeText(this, "Required Field", Toast.LENGTH_LONG).show();
            jetUser.requestFocus();
        } else if (pass.length() < 6 || pass.length() > 12) {
            Toast.makeText(this, "Name must be between 6 and 30 characters long", Toast.LENGTH_LONG).show();
            jetPass.requestFocus();
        } else if (user.matches(sp_character)) {
            Toast.makeText(this, "Please don't put special characters", Toast.LENGTH_LONG).show();
            jetUser.requestFocus();
        } else if (pass.isEmpty()) {
            Toast.makeText(this, "Required Field", Toast.LENGTH_LONG).show();
            jetPass.requestFocus();
        } else if (!pass.matches(passValues)) {
            Toast.makeText(this, "Password must have at least one capital letter, one number and one special character", Toast.LENGTH_LONG).show();
            jetPass.requestFocus();
        } else {
            /* Intent intent = new Intent(this, UserDashboardActivity.class);
            startActivity(intent);

             Toast.makeText(this, "GO AHEAD DUDE!", Toast.LENGTH_LONG).show();*/



            mAuth.signInWithEmailAndPassword(user, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                db.collection("users").whereEqualTo("email", user)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        User user = document.toObject(User.class);

                                                        saveUserPreferences(getApplicationContext(),user);
                                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getApplicationContext(), UserDashboardActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                } else {

                                                }
                                            }
                                        });
                                //saveUserPreferences(getApplicationContext());

                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to Login, check your data and try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void saveUserPreferences(Context context, User user){
        // se obtiene la data del usuario desde firestore
        String email = user.getEmail();
        String role = user.getRole();
        String name = user.getUserName();
        String shopName = user.getShopName();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        // permite escribir data en las shared preferences
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("status", true);
        editor.putString("email", email);
        editor.putString("role", role);
        editor.putString("name", name);
        editor.putString("shop", shopName);
        editor.commit();
    }

    private void reload() { }
}






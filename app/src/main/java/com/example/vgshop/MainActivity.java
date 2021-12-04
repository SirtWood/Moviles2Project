package com.example.vgshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView jimgSettings;
    Button jbtnLogin, jbtnRegister;

    //private ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*getSupportActionBar().hide();*/
        jbtnLogin = findViewById(R.id.btnLogin);
        jbtnRegister = findViewById(R.id.btnRegister);
        jimgSettings = findViewById(R.id.ivSettings);
        jbtnLogin.setOnClickListener(this);
        jbtnRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                Intent intent1=new Intent(this, MainUserLogin.class);
                startActivity(intent1);
                break;
            case R.id.btnRegister:
                Intent intent2=new Intent(this,  MainRegister.class);
                startActivity(intent2);
                break;
        }
    }
}

/*jtvRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, MainRegister.class);
                startActivity(intent);
            }
        });*/
package com.example.indreshprakash.testing.backend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.indreshprakash.testing.R;

public class home extends AppCompatActivity {
    Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn1=(Button)findViewById(R.id.adminbtn);
        btn2=(Button)findViewById(R.id.userbtn);
    }
    public void adminclick(View view){
        Intent i = new Intent(this,adminactivity.class);
        startActivity(i);
    }
    public void userclick(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}

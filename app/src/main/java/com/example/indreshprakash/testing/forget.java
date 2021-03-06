package com.example.indreshprakash.testing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forget extends AppCompatActivity {


    EditText forgetemail;
    Button btn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        mAuth=FirebaseAuth.getInstance();
        forgetemail=(EditText)findViewById(R.id.forgetemail);
        btn=(Button)findViewById(R.id.forgotbtn);
    }

    public void onforgot(View view){
        String useremail = forgetemail.getText().toString().trim();

        if(useremail.equals("")){
            Toast.makeText(forget.this, "Please Enter Email-Id", Toast.LENGTH_SHORT).show();

        }else
        {
            mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(forget.this, "Reset Password email sent", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(forget.this, login1.class));
                    }
                    else{
                        Toast.makeText(forget.this, "Error in Sending Reset Email", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}



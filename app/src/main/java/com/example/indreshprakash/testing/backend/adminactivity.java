package com.example.indreshprakash.testing.backend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.indreshprakash.testing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class adminactivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    Button btn;
    EditText adminemail, adminpass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminactivity);
        mAuth = FirebaseAuth.getInstance();

        btn = (Button) findViewById(R.id.adminsign);
        adminemail = (EditText) findViewById(R.id.adminemail);
        adminpass = (EditText) findViewById(R.id.adminpass);
        progressDialog=new ProgressDialog(this);
    }
    public void onadminsign(View v)
    {
        login();
    }
    private void login() {
        String useremail=adminemail.getText().toString().trim();
        String userpassword=adminpass.getText().toString().trim();
        if (TextUtils.isEmpty(adminemail.getText().toString().trim()) && TextUtils.isEmpty(adminpass.getText().toString().trim())) {
            adminemail.setError("Required");
            adminpass.setError("Required");
        } else if (!emailValidator(adminemail.getText().toString())) {
            adminemail.setError("Please Enter Valid Email Address");
        }else if(TextUtils.isEmpty(adminpass.getText().toString().trim()))
        {
            adminpass.setError("Required");
        }
        else {
            progressDialog.setMessage("Logining in....Please Wait");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(useremail,userpassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressDialog.dismiss();

                            if(task.isSuccessful())
                            {
                                finish();
                                startActivity(new Intent(adminactivity.this,admin.class));
                                Toast.makeText(adminactivity.this, "logged IN", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(adminactivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    public static boolean emailValidator(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}

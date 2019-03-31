package com.example.indreshprakash.testing.login;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.indreshprakash.testing.R;
import com.example.indreshprakash.testing.backend.MainActivity;
import com.example.indreshprakash.testing.backend.SmartDrive;
import com.example.indreshprakash.testing.backend.admin;
import com.example.indreshprakash.testing.forget;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class LoginFragment extends Fragment implements OnLoginListener{
    private static final String TAG = "LoginFragment";
    private EditText logemail,logpass;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    String useremail,userpass;




    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        progressDialog=new ProgressDialog(getContext());
        mAuth=FirebaseAuth.getInstance();
        Intent i =new Intent(getContext(), forget.class);

        logemail=(EditText)view.findViewById(R.id.logemail);

        logpass=(EditText)view.findViewById(R.id.logpass);

        view.findViewById(R.id.forgotpass).setOnClickListener(v ->
                startActivity(i));
        return view;
    }

    @Override
    public void login()
    {
        log();
        Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
    }
    private void log()
    {
        useremail=logemail.getText().toString().trim();
        userpass=logpass.getText().toString().trim();

        if(useremail.equals("admin12@gmail.com") && userpass.equals("admin1234")){
            Intent i=new Intent(getContext(), admin.class);
            startActivity(i);
        }
        else if(TextUtils.isEmpty(logemail.getText().toString().trim()) && TextUtils.isEmpty(logpass.getText().toString().trim()))
        {
            logemail.setError("Required");
            logpass.setError("Required");
            Toast.makeText(getContext(), "Please Enter the Details", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(logemail.getText().toString().trim())){
            logemail.setError("Required");
        }else if(!emailValidator(logemail.getText().toString())){
            logemail.setError("Please Enter Valid Email Address");
        }else if(TextUtils.isEmpty(logpass.getText().toString().trim()))
        {
            logpass.setError("Required");
        }
        else if(logpass.length()<8){
            logpass.setError("Password must be minimum 8 characters");
        }
        else {
            progressDialog.setMessage("Logining in....Please Wait");
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(useremail,userpass)
                    .addOnCompleteListener((Activity) Objects.requireNonNull(getContext()), task -> {
                        progressDialog.dismiss();

                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(getContext(), SmartDrive.class));
                            Toast.makeText(getContext(), "logged IN", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Log.i("Response","Failed to create user:"+task.getException().getMessage());

                            logpass.getText().clear();
                            Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });        }


    }

    public static boolean emailValidator(String email)
    {
        return(!TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public void start()
    {
        Intent j=new Intent(getContext(), MainActivity.class);
        startActivity(j);

    }
}

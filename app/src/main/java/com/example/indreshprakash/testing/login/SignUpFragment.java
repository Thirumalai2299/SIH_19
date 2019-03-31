package com.example.indreshprakash.testing.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.indreshprakash.testing.R;
import com.example.indreshprakash.testing.backend.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUpFragment extends Fragment implements OnSignUpListener{
    private static final String TAG = "SignUpFragment";
    private EditText regemail,regpass,regconfirm;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    String useremail,userpass,userconfirm;
    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        progressDialog=new ProgressDialog(getContext());

        mAuth=FirebaseAuth.getInstance();
        regemail=(EditText)view.findViewById(R.id.regemail);

        regpass=(EditText)view.findViewById(R.id.regpass);

        regconfirm=(EditText)view.findViewById(R.id.regconfirm);

        return view;
    }
    @Override
    public void signUp() {
        reg();
        Toast.makeText(getContext(), "Sign up", Toast.LENGTH_SHORT).show();
    }
    private void reg(){
        useremail=regemail.getText().toString().trim();
        userpass=regpass.getText().toString().trim();
        userconfirm=regconfirm.getText().toString().trim();
        if(TextUtils.isEmpty(regemail.getText().toString().trim()) && TextUtils.isEmpty(regpass.getText().toString().trim()) &&
                TextUtils.isEmpty(regconfirm.getText().toString().trim())) {
            regemail.setError("Required");
            regpass.setError("Required");
            regconfirm.setError("Required");
            Toast.makeText(getContext(), "Please Enter the Details", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(regemail.getText().toString().trim())){
            regemail.setError("Required");
        }else if(!emailValidator(regemail.getText().toString())){
            regemail.setError("Please Enter Valid Email Address");
        }else if(TextUtils.isEmpty(regpass.getText().toString().trim()))
        {
            regpass.setError("Required");
        }
        else if(regpass.length()<8){
            regpass.setError("Password must be minimum 8 characters");
        }
        else if(TextUtils.isEmpty(regconfirm.getText().toString().trim()))
        {
            regpass.setError("Required");
        }
        else if(!userpass.equals(userconfirm)){
            Toast.makeText(getContext(), "Password doesnot matches!!!!", Toast.LENGTH_SHORT).show();
        }
        else{
            progressDialog.setMessage("Registering....Please Wait");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(useremail,userpass)
                    .addOnCompleteListener((Activity) Objects.requireNonNull(getContext()), task -> {

                        progressDialog.dismiss();


                        if(task.isSuccessful())
                        {

                            startActivity(new Intent(getContext(),MainActivity.class));
                            Toast.makeText(getContext(), "logged IN", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }

        });



    }
        }
    public static boolean emailValidator(String email)
    {
        return(!TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}

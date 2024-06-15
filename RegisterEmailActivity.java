package com.example.olx_clone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.olx_clone.databinding.ActivityRegisterEmailBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterEmailActivity extends AppCompatActivity {

    private ActivityRegisterEmailBinding binding;

    private static final String TAG= "REGISTER_TAG";

    private FirebaseAuth auth;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityRegisterEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.toolbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.haveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }
    private String email,pass,cpass;

    private void validateData(){
        email=binding.emailEt.getText().toString().trim();
        pass=binding.passwordEt.getText().toString();
        cpass=binding.cPasswordEt.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //email pattern is invalid, show error
            binding.emailEt.setError("Invalid Email Pattern");
            binding.emailEt.requestFocus();
        } else if (pass.isEmpty()) {
            //password is not entered, show error
            binding.passwordEt.setError("Enter Password");
            binding.passwordEt.requestFocus();
        } else if (!pass.equals(cpass)) {
            //password and confirm password is not same, show error
            binding.cPasswordEt.setError("Password doesn't match");
            binding.cPasswordEt.requestFocus();
        } else {
            //all data is valid, start sign-up
            registerUser();
        }
    }

    private void registerUser(){
            progressDialog.setMessage("Creating Account");
            progressDialog.show();
            auth.createUserWithEmailAndPassword(email, pass)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            // User successfully registered
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            // Registration failed
                            Log.w("TAG", "createUserWithEmail:failure", e);
                            Utilis.toast(RegisterEmailActivity.this,"Failed due to" +e.getMessage());
                        }
                    });
        }

        private void updateUserInfo(){
        progressDialog.setMessage("Saving User Info");
        long timeStamp = Utilis.timeTamp();

        String registerUserEmail = auth.getCurrentUser().getEmail();
        String registerUserUid = auth.getUid();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", "");
            hashMap.put("phoneCode", "");
            hashMap.put("phoneNumber", "");
            hashMap.put("profileImagedirl", "");
            hashMap.put("dob", "");
            hashMap.put("userType", "Email");
            hashMap.put("typingTo", "");
            hashMap.put("timestamp", timeStamp);
            hashMap.put("onlinestatus", true);
            hashMap.put("email", registerUserEmail);
            hashMap.put("uid", registerUserUid);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(registerUserUid)
                    .setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG,"onSuccess: Info Saved");
                            progressDialog.dismiss();
                            startActivity(new Intent(RegisterEmailActivity.this,MainActivity.class));
                            finishAffinity();
                        }
                        })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG,"On Failure:",e);
                            progressDialog.dismiss();
                            Utilis.toast(RegisterEmailActivity.this,"Failed to save info due to");
                        }
                    });
        }
    }
package com.example.olx_clone;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.olx_clone.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showHomeFragments();


        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null)
        {
            startLogin();
        }
            binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.menu_home) {

                    showHomeFragments();
                    return true;

                } else if (itemId == R.id.menu_chats) {
                    if (firebaseAuth.getCurrentUser()==null){
                        Utilis.toast(MainActivity.this,"Login  Required");
                        return false;
                    }
                    else{
                        showChatFragments();
                        return true;
                    }

                } else if (itemId == R.id.menu_myAds) {

                    if (firebaseAuth.getCurrentUser()==null){
                        Utilis.toast(MainActivity.this,"Login  Required");
                        return false;
                    }
                    else{
                        showMyAdsFragments();
                        return true;
                    }

                } else if (itemId == R.id.menu_accounts) {

                    if (firebaseAuth.getCurrentUser()==null){
                        Utilis.toast(MainActivity.this,"Login  Required");
                        return false;
                    }
                    else{
                        showAccFragments();
                        return true;
                    }
                } else {
                    return false;
                }
            }
        });
    }


    private void showHomeFragments() {
        binding.toolbarTitleTv.setText("Home");

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), fragment, "HomeFragment");
        fragmentTransaction.commit();
    }

    private void showChatFragments() {
        binding.toolbarTitleTv.setText("Chats");

        ChatFragment fragment = new ChatFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentsFl.getId(), fragment, "ChatFragment");
        fragmentTransaction.commit();

    }

    private void showMyAdsFragments() {
        binding.toolbarTitleTv.setText("My-ADS");
        MyAdsFragment fragment = new MyAdsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FragmentTransaction replace = fragmentTransaction.replace(binding.fragmentsFl.getId(), fragment, "MyAdsFragments");
        fragmentTransaction.commit();


    }

    private void showAccFragments() {
        binding.toolbarTitleTv.setText("Accounts");
        AccFragment fragment = new AccFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FragmentTransaction replace = fragmentTransaction.replace(binding.fragmentsFl.getId(), fragment, "AccFragments");
        fragmentTransaction.commit();


    }

    private void startLogin(){
        startActivity(new Intent(this,LoginOptionsActivity.class));
    }
}

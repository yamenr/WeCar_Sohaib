package com.example.wecar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

// TODO: Add user extended data
   // included number for contact in whatsapp
// TODO: multi photo
// TODO: photo size
// TODO: back stack for fragments
// TODO: Check favourits, details
// TODO: Search fragment - recyclerview crash issue

public class MainActivity extends AppCompatActivity {

    private FirebaseServices fbs;
    private BottomNavigationView bottomNavigationView;
    private ListFragmentType listType;

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //gotoLoginFragment();
        //gotoCarList();
    }

    private void init()
    {
        fbs = FirebaseServices.getInstance();
        listType = ListFragmentType.Regular;
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
            if (item.getItemId() == R.id.action_home)
            {
                selectedFragment = new CarsListFragment();
            }
            else if (item.getItemId() == R.id.action_fav) {
                selectedFragment = new CarsListFragment(); // Favourits
            }
            else if (item.getItemId() == R.id.action_add) {
                selectedFragment = new AddCarFragment();
            }
            else if (item.getItemId() == R.id.action_search) { // Add search bar
                selectedFragment = new SearchFragment();
            }
            else if (item.getItemId() == R.id.action_signout) {
                signout();
            }
                /*
            }
                switch (item.getItemId()) {
                    case R.id.action_home:

                        break;
                    case R.id.action_fav:
                        selectedFragment = new CarsListFragment();
                        break;
                    case R.id.action_add:
                        selectedFragment = new AddCarFragment();
                        break;
                    case R.id.action_search:
                        selectedFragment = new CarsListFragment();
                        break;
                    case R.id.action_signout:
                        signout(); // TODO: hide or disable navigation bar, exit firebase
                        break;
                }
                */
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, selectedFragment)
                            .commit();
                }
                return true;
            }});

        if (fbs.getAuth().getCurrentUser() == null)
        {
            bottomNavigationView.setVisibility(View.INVISIBLE);
            gotoLoginFragment();
        }
        else
        {
            bottomNavigationView.setVisibility(View.VISIBLE);
            gotoCarList();
        }

    }

    private void signout() {
        fbs.getAuth().signOut();
        bottomNavigationView.setVisibility(View.INVISIBLE);
        gotoLoginFragment();
    }


    private void gotoLoginFragment() {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new LoginFragment());
        ft.commit();
    }
    public void gotoCarList() {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new CarsListFragment());
        ft.commit();
    }

}

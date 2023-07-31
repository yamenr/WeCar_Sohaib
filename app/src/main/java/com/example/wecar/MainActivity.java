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

public class MainActivity extends AppCompatActivity {

    private final int actionHome = 1000026; // R.id.action_home
    private final int actionFav = 1000035; // R.id.action_fav
    private final int actionAdd = 1000044; // R.id.action_add
    private final int actionSearch = 1000021; // R.id.action_search
    private final int actionSignout = 1000022; // R.id.action_signout
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
                selectedFragment = new CarsListFragment();
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
                            .replace(R.id.frameLayoutMain, selectedFragment)
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
        ft.replace(R.id.frameLayoutMain,new LoginFragment());
        ft.commit();
    }
    public void gotoCarList() {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain,new CarsListFragment());
        ft.commit();
    }

}

package com.example.wecar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.wecar.data.FirebaseServices;
import com.example.wecar.data.ListFragmentType;
import com.example.wecar.fragments.AddCarFragment;
import com.example.wecar.fragments.CarsListFragment;
import com.example.wecar.fragments.LoginFragment;
import com.example.wecar.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Stack;

// TODO: multi photo
// TODO: Check favourites, details
// TODO: Search fragment - recyclerview crash issue

public class MainActivity extends AppCompatActivity {

    private FirebaseServices fbs;
    private BottomNavigationView bottomNavigationView;
    private ListFragmentType listType;
    private Stack<Fragment> fragmentStack = new Stack<>();
    private FrameLayout fragmentContainer;
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
        fragmentContainer = findViewById(R.id.frameLayout);
        if (fbs.getAuth().getCurrentUser() == null)
        {
            bottomNavigationView.setVisibility(View.GONE);
            gotoLoginFragment();
            pushFragment(new LoginFragment());
        }
        else
        {
            bottomNavigationView.setVisibility(View.VISIBLE);
            gotoCarList();
            pushFragment(new CarsListFragment());
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

    @Override
    public void onBackPressed() {
        if (fragmentStack.size() > 1) {
            fragmentStack.pop(); // Remove the current fragment from the stack
            Fragment previousFragment = fragmentStack.peek(); // Get the previous fragment

            // Replace the current fragment with the previous one
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, previousFragment)
                    .commit();
        } else {
            super.onBackPressed(); // If there's only one fragment left, exit the app
        }
    }

    // Method to add a new fragment to the stack
    public void pushFragment(Fragment fragment) {
        fragmentStack.push(fragment);
        /*
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit(); */
    }
}

package com.example.wecar.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.wecar.data.FirebaseServices;
import com.example.wecar.R;
import com.example.wecar.data.Car;
import com.example.wecar.utilities.myAdapter1;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarsListFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseServices fbs;
    myAdapter1 myAdapter;
    ArrayList<Car> list, filteredList;
    FloatingActionButton btnAdd;
    SearchView srchView;

        // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarsListFragment newInstance(String param1, String param2) {
        CarsListFragment fragment = new CarsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {

        recyclerView = getView().findViewById(R.id.rvCarlist);
        btnAdd = getView().findViewById(R.id.floatingButtonAddCarList);
        fbs = FirebaseServices.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list= new ArrayList<>(); filteredList = new ArrayList<>();
        myAdapter= new myAdapter1(getActivity(),list);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new myAdapter1.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                String selectedItem = list.get(position).getNameCar();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });
        fbs.getFire().collection("cars").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){
                    Car car= dataSnapshot.toObject(Car.class);
                    list.add(car);
                }


                myAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        srchView = getView().findViewById(R.id.srchViewCarListFragment);
        srchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                applyFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //applyFilter(newText);
                return false;
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddCarFragment();
            }
        });
        btnAdd.setVisibility(View.INVISIBLE); // currently hidden
    }

    private void applyFilter(String query) {
        // TODO: add onBackspace - old and new query
        if (query.trim().isEmpty())
        {
            myAdapter = new myAdapter1(getContext(), list);
            recyclerView.setAdapter(myAdapter);
            //myAdapter.notifyDataSetChanged();
            return;
        }
        filteredList.clear();
        for(Car car : list)
        {
            if (car.getCar_model().toLowerCase().contains(query.toLowerCase()) ||
                    car.getCar_num().toLowerCase().contains(query.toLowerCase()) ||
                    car.getColor().toLowerCase().contains(query.toLowerCase()) ||
                    car.getKilometre().toLowerCase().contains(query.toLowerCase()) ||
                    car.getEngine_capacity().toLowerCase().contains(query.toLowerCase()) ||
                    car.getHorse_power().toLowerCase().contains(query.toLowerCase()) ||
                    car.getManufacturer().toLowerCase().contains(query.toLowerCase()) ||
                    car.getNameCar().toLowerCase().contains(query.toLowerCase()) ||
                    car.getOwners().toLowerCase().contains(query.toLowerCase()) ||
                    car.getTest().toLowerCase().contains(query.toLowerCase()) ||
                    car.getYear().toLowerCase().contains(query.toLowerCase()) ||
                    car.getPrice().toLowerCase().contains(query.toLowerCase()) ||
                    car.getGear_shifting_model().toLowerCase().contains(query.toLowerCase()))
            {
                filteredList.add(car);
            }
        }
        if (filteredList.size() == 0)
        {
            showNoDataDialogue();
            return;
        }
        myAdapter = new myAdapter1(getContext(), filteredList);
        recyclerView.setAdapter(myAdapter);
    }

    private void showNoDataDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("No Results");
        builder.setMessage("Try again!");
        builder.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cars_list, container, false);
    }

    public void gotoAddCarFragment() {
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new AddCarFragment());
        ft.commit();
    }

}
package com.example.wecar.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wecar.MainActivity;
import com.example.wecar.data.FirebaseServices;
import com.example.wecar.R;
import com.example.wecar.data.Car;
import com.example.wecar.utilities.myAdapter1;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private Spinner manufacturerSpinner;
    private Spinner carModelSpinner;
    private Spinner startYearSpinner;
    private Spinner endYearSpinner;
    private Button searchButton;
    private Button clearButton;
    RecyclerView recyclerView;
    FirebaseServices fbs;
    myAdapter1 myAdapter;
    ArrayList<Car> list, filteredList;

    private String[] manufacturerList = {"Select Manufacturer", "Toyota", "Honda", "Ford", "BMW"};
    private String[] carModelList = {"Select Car Model", "Corolla", "Civic", "Focus", "3 Series"};
    private String[] yearList = {"Select Year", "2010", "2015", "2020", "2023"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        manufacturerSpinner = view.findViewById(R.id.searchManufacturerSpinner);
        carModelSpinner = view.findViewById(R.id.searchCarModelSpinner);
        startYearSpinner = view.findViewById(R.id.searchStartYearSpinner);
        endYearSpinner = view.findViewById(R.id.searchEndYearSpinner);
        searchButton = view.findViewById(R.id.btnSearch);
        clearButton = view.findViewById(R.id.btnClear);

        ArrayAdapter<String> manufacturerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, manufacturerList);
        manufacturerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        manufacturerSpinner.setAdapter(manufacturerAdapter);

        ArrayAdapter<String> carModelAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, carModelList);
        carModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carModelSpinner.setAdapter(carModelAdapter);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startYearSpinner.setAdapter(yearAdapter);
        endYearSpinner.setAdapter(yearAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelections();
            }
        });

        return view;
    }

    private void performSearch() {
        String selectManDefaultStr = manufacturerList[0];
        String selectCarDefaultStr = carModelList[0];
        String selectStartYearDefaultStr = yearList[0];
        String selectEndYearDefaultStr = yearList[0];

        String selectedManufacturer = manufacturerSpinner.getSelectedItem().toString();
        String selectedCarModel = carModelSpinner.getSelectedItem().toString();
        String selectedStartYear = startYearSpinner.getSelectedItem().toString();
        String selectedEndYear = endYearSpinner.getSelectedItem().toString();

        boolean manFlag, modelFlag, yearStartFlag, yearEndFlag;
        manFlag = modelFlag = yearStartFlag = yearEndFlag = false;

        if (!selectedManufacturer.equals(selectManDefaultStr))
            manFlag = true;
        if (!selectedCarModel.equals(selectCarDefaultStr))
            modelFlag = true;
        if (!selectedStartYear.equals(selectStartYearDefaultStr))
            yearStartFlag = true;
        if (!selectedEndYear.equals(selectEndYearDefaultStr))
            yearEndFlag = true;

        filteredList.clear();
        for(Car car : list) {
            boolean manFound = false, modelFound = false, yearFound = false;

            if (manFlag) {
                if (car.getManufacturer().toLowerCase().contains(selectedManufacturer.toLowerCase()))
                    manFound = true;
            }

            if (modelFlag) {
                if (car.getCar_model().toLowerCase().contains(selectedCarModel.toLowerCase()))
                    modelFound = true;
            }

            if (yearStartFlag && yearEndFlag) {
                if (Integer.parseInt(car.getYear().toLowerCase()) >= Integer.parseInt(selectedStartYear) &&
                        Integer.parseInt(car.getYear().toLowerCase()) <= Integer.parseInt(selectedEndYear)) {
                    yearFound = true;
                }
            }
            else if (yearStartFlag && !yearEndFlag)
            {
                if (Integer.parseInt(car.getYear().toLowerCase()) >= Integer.parseInt(selectedStartYear) &&
                        Integer.parseInt(car.getYear().toLowerCase()) <= Integer.parseInt(yearList[yearList.length - 1])) {
                    yearFound = true;
                }
            }
            else if (!yearStartFlag && yearEndFlag)
            {
                if (Integer.parseInt(car.getYear().toLowerCase()) >= Integer.parseInt(yearList[1]) &&
                        Integer.parseInt(car.getYear().toLowerCase()) <= Integer.parseInt(yearList[yearList.length - 1])) {
                    yearFound = true;
                }
            }

            if ((!manFlag) || (manFlag && manFound))
            {
                if ((!modelFlag) || (modelFlag && modelFound) ) {
                    if ((!yearStartFlag && !yearEndFlag) ||
                            (((yearStartFlag && !yearEndFlag) || (!yearStartFlag && yearEndFlag)) && yearFound))
                    {
                        filteredList.add(car);
                    }
                }
            }

        }

        myAdapter= new myAdapter1(getActivity(),filteredList);
        recyclerView.setAdapter(myAdapter);
    }

    private void clearSelections() {
        manufacturerSpinner.setSelection(0);
        carModelSpinner.setSelection(0);
        startYearSpinner.setSelection(0);
        endYearSpinner.setSelection(0);
        myAdapter= new myAdapter1(getActivity(),list);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView = getView().findViewById(R.id.rvCarsSearchFragment);
        fbs = FirebaseServices.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list= new ArrayList<>(); filteredList = new ArrayList<>();
        myAdapter= new myAdapter1(getActivity(),list);
        recyclerView.setAdapter(myAdapter);
        getCarList();
        ((MainActivity)getActivity()).pushFragment(new SearchFragment());
    }

    private void getCarList() {
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
    }
}

/*
            if ((!manFlag) || (manFlag && manFound))
            {
                if ((!yearFlag) || (yearFlag && yearFound)) {
                    if ((!modelFlag) || (modelFlag && modelFound) )
                    {
                        filteredList.add(car);
                    }
                }
            }

            if ((!modelFlag) || (modelFlag && modelFound))
            {
                if ((!manFlag) || (manFlag && manFound))
                {
                    if ((!yearFlag) || (yearFlag && yearFound))
                    {
                        filteredList.add(car);
                    }
                }
            }

            if ((!modelFlag) || (modelFlag && modelFound))
            {
                if ((!yearFlag) || (yearFlag && yearFound))
                {
                    if ((!manFlag) || (manFlag && manFound))
                    {
                        filteredList.add(car);
                    }
                }
            }

            if ((!yearFlag) || (yearFlag && yearFound))
            {
                if ((!modelFlag) || (modelFlag && modelFound))
                {
                    if ((!manFlag) || (manFlag && manFound))
                    {
                        filteredList.add(car);
                    }
                }
            }

            if ((!yearFlag) || (yearFlag && yearFound))
            {
                if ((!manFlag) || (manFlag && manFound))
                {
                    if ((!modelFlag) || (modelFlag && modelFound))
                    {
                        filteredList.add(car);
                    }
                }
            } */

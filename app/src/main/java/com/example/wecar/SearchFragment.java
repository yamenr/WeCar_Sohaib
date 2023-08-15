package com.example.wecar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {

    private Spinner manufacturerSpinner;
    private Spinner carModelSpinner;
    private Spinner startYearSpinner;
    private Spinner endYearSpinner;
    private Button searchButton;
    private Button clearButton;

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
        String selectedManufacturer = manufacturerSpinner.getSelectedItem().toString();
        String selectedCarModel = carModelSpinner.getSelectedItem().toString();
        String selectedStartYear = startYearSpinner.getSelectedItem().toString();
        String selectedEndYear = endYearSpinner.getSelectedItem().toString();

        // Perform search logic with selected values
        // You can display results, show a toast, etc.
    }

    private void clearSelections() {
        manufacturerSpinner.setSelection(0);
        carModelSpinner.setSelection(0);
        startYearSpinner.setSelection(0);
        endYearSpinner.setSelection(0);
    }
}

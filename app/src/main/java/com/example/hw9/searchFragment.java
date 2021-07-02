package com.example.hw9;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Spinner spinner = (Spinner) view.findViewById(R.id.categoryInput);
        Spinner unitSpinner = (Spinner) view.findViewById(R.id.distUnitInput);
        RadioGroup groupRadio = (RadioGroup) view.findViewById(R.id.radioGroup);
        RadioButton currButton = (RadioButton) view.findViewById(R.id.currButton);
        ((MainActivity) getActivity()).createCategories(spinner, unitSpinner, groupRadio,
                currButton, view);

        // Inflate the layout for this fragment
        return view;
    }

}
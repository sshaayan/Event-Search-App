package com.example.hw9;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link venueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class venueFragment extends Fragment {

    String name = "";
    String address = "";
    String city = "";
    String phone = "";
    String hours = "";
    String genRule = "";
    String childRule = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        name = bundle.getString("name");
        address = bundle.getString("address");
        city = bundle.getString("city");
        phone = bundle.getString("phone");
        hours = bundle.getString("hours");
        genRule = bundle.getString("genRule");
        childRule = bundle.getString("childRule");

        View view = inflater.inflate(R.layout.fragment_venue, container, false);
        TableLayout table = (TableLayout) view.findViewById(R.id.venueTable);
        if (name != null && name.length() != 0) {
            ((TextView) view.findViewById(R.id.nameDetail)).setText(name);
        }
        else {
            table.removeView(view.findViewById(R.id.nameRow));
        }
        if (address != null && address.length() != 0) {
            ((TextView) view.findViewById(R.id.addrDetail)).setText(address);
        }
        else {
            table.removeView(view.findViewById(R.id.addrRow));
        }
        if (city != null && city.length() != 0) {
            ((TextView) view.findViewById(R.id.cityDetail)).setText(city);
        }
        else {
            table.removeView(view.findViewById(R.id.cityRow));
        }
        if (phone != null && phone.length() != 0) {
            ((TextView) view.findViewById(R.id.phoneDetail)).setText(phone);
        }
        else {
            table.removeView(view.findViewById(R.id.phoneRow));
        }
        if (hours != null && hours.length() != 0) {
            ((TextView) view.findViewById(R.id.hoursDetail)).setText(hours);
        }
        else {
            table.removeView(view.findViewById(R.id.hoursRow));
        }
        if (genRule != null && genRule.length() != 0) {
            ((TextView) view.findViewById(R.id.genDetail)).setText(genRule);
        }
        else {
            table.removeView(view.findViewById(R.id.genRow));
        }
        if (childRule != null && childRule.length() != 0) {
            ((TextView) view.findViewById(R.id.childDetail)).setText(childRule);
        }
        else {
            table.removeView(view.findViewById(R.id.childRow));
        }

        // Inflate the layout for this fragment
        return view;
    }
}
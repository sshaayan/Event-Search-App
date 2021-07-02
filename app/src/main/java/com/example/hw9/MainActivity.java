package com.example.hw9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    EditText searchField, distField, otherField;
    Spinner categoryField, unitField;
    RadioGroup radioGroup;
    AwesomeValidation validationForm;
    LocationManager locationManager;
    String currLat;
    String currLng;
    SearchResults searchResultsObj = new SearchResults();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the "Search" and "Favourites" tab
        tabLayout = findViewById(R.id.mainTabLayout);
        viewPager = findViewById(R.id.viewpager1);

        tabLayout.setupWithViewPager(viewPager);
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new searchFragment(), "SEARCH");
        adapter.addFragment(new favouritesFragment(), "FAVOURITES");
        viewPager.setAdapter(adapter);
    }

    // Add the respective items to each spinner
    public void createCategories(Spinner spinner, Spinner unitSpinner, RadioGroup groupRadio,
                                 RadioButton currButton, View searchView) {
        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("All");
        categoryList.add("Music");
        categoryList.add("Sports");
        categoryList.add("Arts & Theatre");
        categoryList.add("Film");
        categoryList.add("Miscellaneous");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoryList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        ArrayList<String> unitList = new ArrayList<>();
        unitList.add("miles");
        unitList.add("kilometers");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                unitList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(arrayAdapter);

        // Check "Current" radio button by default
        groupRadio.check(currButton.getId());

        // Get the input form elements
        searchField = (EditText) searchView.findViewById(R.id.keywordInput);
        categoryField = (Spinner) searchView.findViewById(R.id.categoryInput);
        distField = (EditText) searchView.findViewById(R.id.distInput);
        unitField = (Spinner) searchView.findViewById(R.id.distUnitInput);
        radioGroup = groupRadio;
        otherField = (EditText) searchView.findViewById(R.id.otherInput);

        // Remove any errors from "otherField" when the other radio button is checked
        ((RadioButton) searchView.findViewById(R.id.currButton)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            otherField.setError(null);
                        }
                    }
                });

        // Get location from the phone's GPS
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        currLat = String.valueOf(locationGPS.getLatitude());
        currLng = String.valueOf(locationGPS.getLongitude());
    }

    public void processData(View view) {
        String keyword = searchField.getText().toString();
        String category = categoryField.getSelectedItem().toString();
        String distance = distField.getText().toString();
        String units = unitField.getSelectedItem().toString();
        String otherLoc = otherField.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        Intent i = new Intent(this, SearchResults.class);
        queue.start();

        boolean inputError = false;
        if (keyword.replaceAll("\\s+", "").isEmpty()) {
            searchField.requestFocus();
            searchField.setError(getString(R.string.validation_error));
            inputError = true;
        }
        if (radioGroup.getCheckedRadioButtonId() == R.id.otherButton) {
            if (otherLoc.replaceAll("\\s+", "").isEmpty()) {
                otherField.requestFocus();
                otherField.setError(getString(R.string.validation_error));
                inputError = true;
            }
            else {
                String geoUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=";
                otherLoc = otherLoc.replaceAll("\\s+","+");
                geoUrl += otherLoc + "&key=AIzaSyBBhJ4BGqKrObAIHsXPzhuTYTs9FUvL2g8";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        geoUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Process response
                        try {
                            currLat = String.valueOf(response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").opt("lat"));
                            currLng = String.valueOf(response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").opt("lng"));
                        } catch (JSONException e) {
                            Log.d("ERROR", String.valueOf(e));
                            return;
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                        Log.d("ERROR", String.valueOf(error));
                        return;
                    }
                });
                queue.add(jsonObjectRequest);
            }
        }
        if (inputError) {
            return;
        }

        // Get JSON using the input values
        String dataUrl = "https://csci571hw8-0001.wl.r.appspot.com/searchEvents?keyword=";
        dataUrl += keyword.replaceAll("\\s+", "+") + "&unit=" + units +
                    "&radius=" + distance + "&lat=" + currLat + "&lng=" + currLng;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                dataUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Process response
                i.putExtra("resultExists", 1);
                i.putExtra("data", response.toString());
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error
                Log.d("TEST", "ERROR or EMPTY");
                i.putExtra("resultExists", 0);

                startActivity(i);
                return;
            }
        });
        queue.add(jsonArrayRequest);
    }

    // Clears and resets the input forms
    public void clearData(View view) {
        searchField.setText("");
        categoryField.setSelection(0);
        distField.setText("10");
        unitField.setSelection(0);
        radioGroup.check(R.id.currButton);
        otherField.setText("");
        otherField.setError(null);
        searchField.setError(null);
    }

    public void test3Click(View view) {
        Intent i = new Intent(this, EventDetails.class);
        startActivity(i);
    }
}
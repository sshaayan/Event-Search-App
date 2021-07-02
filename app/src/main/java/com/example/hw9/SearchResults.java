package com.example.hw9;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw9.databinding.ActivitySearchResultsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySearchResultsBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<EventsList> eventsLists;
    private Integer resultExists;

    TextView resultText;
    JSONArray responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchResultsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        resultText = (TextView) this.findViewById(R.id.emptyResults);
        resultExists = getIntent().getIntExtra("resultExists", -1);
        if (savedInstanceState != null) {
            resultExists = savedInstanceState.getInt("exists");
        }
        if (resultExists == 1) {
            existResults();
            try {
                if (savedInstanceState != null) {
                    responseData = new JSONArray((savedInstanceState.getString("allData")));
                }
                responseData = new JSONArray(getIntent().getStringExtra("data"));
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }

            eventsLists = new ArrayList<>();
            for (int i = 0; i < responseData.length(); i++) {
                JSONObject entry;
                try {
                    entry = responseData.getJSONObject(i);
                    String currCategory = entry.getString("category");
                    String currName = entry.getString("event");
                    if (currName.length() > 25) {
                        currName = currName.substring(0, 25) + "...";
                    }
                    String currVenue = entry.getString("venue");
                    String currDate = entry.getString("date");
                    // Get favourited status
                    boolean currFav = false;

                    EventsList newEntry = new EventsList(currCategory, currName, currVenue,
                            currDate, currFav, entry);
                    eventsLists.add(newEntry);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }

            adapter = new EventsAdapter(eventsLists, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        else if (resultExists == 0){
            noResults();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("allData", responseData.toString());
        savedInstanceState.putInt("exists", resultExists);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        resultExists = savedInstanceState.getInt("exists");
        try {
            responseData = new JSONArray(savedInstanceState.getString("allData"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void noResults() {
        resultText.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(),  "An error has occurred",
                Toast.LENGTH_SHORT).show();
        return;
    }

    public void existResults() {
        resultText.setVisibility(View.INVISIBLE);
        return;
    }
}
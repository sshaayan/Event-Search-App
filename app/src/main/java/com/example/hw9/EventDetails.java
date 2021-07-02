package com.example.hw9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hw9.databinding.ActivitySearchResultsBinding;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventDetails extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ActivitySearchResultsBinding binding;

    JSONObject eventData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        tabLayout = findViewById(R.id.eventTabLayout);
        viewPager = findViewById(R.id.viewpager2);

        // Get data needed for the Event Fragment
        String artists = "";
        String venue = "";
        String date = "";
        String category = "";
        String price = "";
        String status = "";
        String buyLink = "";
        String mapLink = "";
        String eventName = "";
        try {
            eventData = new JSONObject(getIntent().getStringExtra("allDetails"));

            JSONArray artistArray = eventData.getJSONArray("artists");
            for (int i = 0; i < artistArray.length(); i++) {
                if (i == 0) {
                    artists += artistArray.getString(i);
                }
                else {
                    artists += " | " + artistArray.getString(i);
                }
            }
            venue = eventData.getString("venue");
            date = eventData.getString("date");
            category = eventData.getString("category");
            price = eventData.getString("priceRange");
            status = eventData.getString("ticketStatus");
            buyLink = eventData.getString("ticketURL");
            mapLink = eventData.getString("seatmap");
            eventName = eventData.getString("event");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Bundle eventBundle = new Bundle();
        eventBundle.putString("artists", artists);
        eventBundle.putString("venue", venue);
        eventBundle.putString("date", date);
        eventBundle.putString("category", category);
        eventBundle.putString("price", price);
        eventBundle.putString("status", status);
        eventBundle.putString("buyLink", buyLink);
        eventBundle.putString("mapLink", mapLink);
        eventFragment eventFrag = new eventFragment();
        eventFrag.setArguments(eventBundle);

        // Get data needed for the Artists Fragment
        artistsFragment artistFrag = new artistsFragment();
        try {
            JSONArray artistInfo = eventData.getJSONArray("artistInfo");
            JSONArray artistNames = eventData.getJSONArray("artists");

            Bundle artistBundle = new Bundle();
            eventBundle.putString("artistInfo", artistInfo.toString());
            eventBundle.putString("artistNames", artistNames.toString());
            artistFrag.setArguments(artistBundle);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Get data needed for the Venu Fragment
        venueFragment venueFrag = new venueFragment();
        String address = "";
        String city = "";
        String phone = "";
        String hours = "";
        String genRule = "";
        String childRule = "";
        try {
            address = eventData.getString("address");
            city = eventData.getString("city");
            phone = eventData.getString("phone");
            hours = eventData.getString("hours");
            genRule = eventData.getString("generalRule");
            childRule = eventData.getString("childRule");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Bundle venueBundle = new Bundle();
        venueBundle.putString("name", eventName);
        venueBundle.putString("address", address);
        venueBundle.putString("city", city);
        venueBundle.putString("phone", phone);
        venueBundle.putString("hours", hours);
        venueBundle.putString("genRule", genRule);
        venueBundle.putString("childRule", childRule);
        venueFrag.setArguments(venueBundle);

        setTitle(eventName);
        tabLayout.setupWithViewPager(viewPager);
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(eventFrag, "EVENTS");
        adapter.addFragment(artistFrag, "ARTIST(S)");
        adapter.addFragment(venueFrag, "VENUE");
        viewPager.setAdapter(adapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.info_outline);
        tabLayout.getTabAt(1).setIcon(R.drawable.artist);
        tabLayout.getTabAt(2).setIcon(R.drawable.venue);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
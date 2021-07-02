package com.example.hw9;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link artistsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class artistsFragment extends Fragment {

    int i = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artists, container, false);

        /*
        Bundle bundle = getArguments();
        try {
            JSONArray artistInfo = new JSONArray(bundle.getString("artistInfo"));
            JSONArray artistNames = new JSONArray(bundle.getString("artistNames"));
            if (artistInfo.length() == 0) {
                return view;
            }
            else {
                ((TextView) view.findViewById(R.id.noRecords)).setVisibility(View.INVISIBLE);
            }

            for (i = 0; i < artistInfo.length() && i < 5; i++) {
                String query = artistNames.getString(i).replaceAll("\\s+", "+");
                String url = "https://csci571hw8-0001.wl.r.appspot.com/getArtistInfo?artist=" + query;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Process response
                        try {
                            if (response.getString("name").length() == 0) {
                                // Mention that this artist has no records
                                String rowID = "row" + i;

                            }
                            else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                        Log.d("ERROR", String.valueOf(error));
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return view;
        }
        */

        // Inflate the layout for this fragment
        return view;
    }
}
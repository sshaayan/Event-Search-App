package com.example.hw9;

import org.json.JSONObject;

public class EventsList {

    private String category;
    private String eventName;
    private String venue;
    private String date;
    private boolean favourited;
    private JSONObject details;

    public String getCategory() { return category; }

    public String getName() { return eventName; }

    public String getVenue() { return venue; }

    public String getDate() { return date; }

    public boolean getFavourited() { return favourited; }

    public JSONObject getDetails() { return details; }

    public EventsList(String catInput, String nameInput, String venueInput, String dateInput,
                      boolean favInput, JSONObject objDetails) {
        category = catInput;
        eventName = nameInput;
        venue = venueInput;
        date = dateInput;
        favourited = favInput;
        details = objDetails;
    }

}

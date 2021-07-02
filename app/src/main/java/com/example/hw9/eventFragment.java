package com.example.hw9;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link eventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class eventFragment extends Fragment {

    String artists = "";
    String venue = "";
    String date = "";
    String category = "";
    String price = "";
    String status = "";
    String buyLink = "";
    String mapLink = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        artists = bundle.getString("artists");
        venue = bundle.getString("venue");
        date = bundle.getString("date");
        category = bundle.getString("category");
        price = bundle.getString("price");
        status = bundle.getString("status");
        buyLink = bundle.getString("buyLink");
        mapLink = bundle.getString("mapLink");

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        TableLayout table = (TableLayout) view.findViewById(R.id.eventTable);
        if (artists != null && artists.length() != 0) {
            ((TextView) view.findViewById(R.id.artistList)).setText(artists);
        }
        else {
            table.removeView(view.findViewById(R.id.artistRow));
        }
        if (venue != null && venue.length() != 0) {
            ((TextView) view.findViewById(R.id.venueDetail)).setText(venue);
        }
        else {
            table.removeView(view.findViewById(R.id.venueRow));
        }
        if (date != null && date.length() != 0) {
            ((TextView) view.findViewById(R.id.dateDetail)).setText(date);
        }
        else {
            table.removeView(view.findViewById(R.id.dateRow));
        }
        if (category != null && category.length() != 0) {
            ((TextView) view.findViewById(R.id.categoryDetail)).setText(category);
        }
        else {
            table.removeView(view.findViewById(R.id.categoryRow));
        }
        if (price != null && price.length() != 0) {
            ((TextView) view.findViewById(R.id.priceDetail)).setText(price);
        }
        else {
            table.removeView(view.findViewById(R.id.priceRow));
        }
        if (status != null && status.length() != 0) {
            ((TextView) view.findViewById(R.id.statusDetail)).setText(status);
        }
        else {
            table.removeView(view.findViewById(R.id.statusRow));
        }
        if (buyLink != null && buyLink.length() != 0) {
            String setLink = "<a href='" + buyLink + "'>TicketMaster</a>";
            TextView linkView = (TextView) view.findViewById(R.id.buyDetail);
            linkView.setText(Html.fromHtml(setLink));
            linkView.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else {
            table.removeView(view.findViewById(R.id.buyRow));
        }
        if (mapLink != null && mapLink.length() != 0) {
            String setLink = "<a href='" + mapLink + "'>View Seat Map Here</a>";
            TextView linkView = (TextView) view.findViewById(R.id.mapDetail);
            linkView.setText(Html.fromHtml(setLink));
            linkView.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else {
            table.removeView(view.findViewById(R.id.mapRow));
        }

        // Inflate the layout for this fragment
        return view;
    }
}
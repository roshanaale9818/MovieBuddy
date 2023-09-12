package com.example.moviebuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomCinemaAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;

    public CustomCinemaAdapter(Context context, ArrayList<String> v) {
        super(context, R.layout.cinemarow, v);
        this.context = context;
        this.values = v;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.cinemarow, parent, false);
        TextView nameView = rowView.findViewById(R.id.cineName);
        Button btn = rowView.findViewById(R.id.cinBtn);
        String itemText = getItem(position);
//        System.out.println("RESULT==>"+itemText.toLowerCase()+itemText.toLowerCase()=="no records found");
//        if(itemText.toLowerCase()=="no records found"){
//            btn.setVisibility(View.GONE);
//
//        }
        nameView.setText(itemText);


        // Set a click listener for the button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform actions when the button is clicked
//                TextView id = rowView.findViewById(values.get(0))
                Intent intent = new Intent(context
                        ,CinemaView.class);
                String cinemaId = getItem(position).split(",",2)[0];

                intent.putExtra("cinemaId",cinemaId);
                System.out.println("PASSING ID IS:"+ getItem(0));
                Toast.makeText(getContext(), "Button Clicked for item: " + cinemaId, Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}

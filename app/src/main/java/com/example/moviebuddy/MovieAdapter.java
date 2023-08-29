package com.example.moviebuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


public class MovieAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public MovieAdapter(Context context, String[] v) {
        super(context, R.layout.movierow, v);
        this.context = context;
        this.values = v;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.movierow, parent, false);

        TextView nameView = rowView.findViewById(R.id.movieNameView);
//        ImageView imageView = rowView.findViewById(R.id.icon);
        nameView.setText(values[position]);
        TextView directorView = rowView.findViewById(R.id.directorView);
//        ImageView imageView = rowView.findViewById(R.id.icon);
        directorView.setText(values[position]);
        TextView castView = rowView.findViewById(R.id.castsView);
//        ImageView imageView = rowView.findViewById(R.id.icon);
        castView.setText(values[position]);
        TextView releaseDateView = rowView.findViewById(R.id.releaseDateView);
//        ImageView imageView = rowView.findViewById(R.id.icon);
        releaseDateView.setText(values[position]);
        // Change the icon for Windows and iPhone
        String s = values[position];
//        if (s.startsWith("Windows7") || s.startsWith("iPhone") || s.startsWith("Ubuntu")) {
//            imageView.setImageResource(R.drawable.ham);
//        } else if(s.startsWith("WebOS")|| s.startsWith("Solaris") ) {
//            imageView.setImageResource(R.drawable.android);
//        }
//        else {
//            imageView.setImageResource(R.drawable.android);
//        }

        return rowView;
    }
}

package com.zybooks.myapplication.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.myapplication.R;

public class CustomAdapater extends RecyclerView.Adapter<CustomAdapater.ViewHolder> {

    private final String [] type_array;
    private final String [] weight_array;
    private final String [] reps_array;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView type_textView;
        private final TextView weight_textView;
        private final TextView rep_textView;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            type_textView = (TextView) view.findViewById(R.id.workName);
            weight_textView = (TextView) view.findViewById(R.id.weight);
            rep_textView = (TextView) view.findViewById(R.id.reps);
        }

        public TextView getType() {
            return type_textView;
        }
        public TextView getWeight()
        {
            return weight_textView;
        }
        public TextView getReps()
        {
            return rep_textView;
        }
    }

    public CustomAdapater(String [] ty, String [] wei, String [] rep)
    {
        type_array = ty;
        weight_array = wei;
        reps_array = rep;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lift_widget, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getType().setText(type_array[position]);
        viewHolder.getWeight().setText(weight_array[position]);
        viewHolder.getReps().setText(reps_array[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return type_array.length;
    }
}





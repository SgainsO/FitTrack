package com.zybooks.myapplication.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.myapplication.R;
import com.zybooks.myapplication.ui.lifts.LiftWidget;
import com.zybooks.myapplication.ui.weight.WeightWidget;

public class WeightAdapter extends ListAdapter<WeightWidget, WeightAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView weight_textView;
        private final TextView date_textView;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            date_textView = (TextView) view.findViewById(R.id.date);
            weight_textView = (TextView) view.findViewById(R.id.curr_weight);
        }

        public void bind(String date, String weight) {
            date_textView.setText(date);
            weight_textView.setText(weight);
        }

        static ViewHolder create(ViewGroup parent)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.weight_widget, parent, false);
            return new ViewHolder(view);
        }
        public TextView getWeight()
        {
            return weight_textView;
        }
        public TextView getDate(){
            return date_textView;
        }
    }

    public WeightAdapter(@NonNull DiffUtil.ItemCallback<WeightWidget> difCallback) {
        super(difCallback);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.create(parent);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        WeightWidget current = getItem(position);
        viewHolder.bind(current.date, current.weight);
    }

    // Return the size of your dataset (invoked by the layout manager)
    public static class WordDiff extends DiffUtil.ItemCallback<WeightWidget> {

        @Override
        public boolean areItemsTheSame(@NonNull WeightWidget oldItem, @NonNull WeightWidget newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull WeightWidget oldItem, @NonNull WeightWidget newItem) {
            return oldItem.date.equals(newItem.date) && oldItem.weight.equals(newItem.weight);
        }
    }

}





package com.zybooks.myapplication.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.myapplication.R;
import com.zybooks.myapplication.ui.home.HomeWidget;
import com.zybooks.myapplication.ui.lifts.LiftWidget;

import java.util.List;

public class HomeAdapter extends ListAdapter<HomeWidget, HomeAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView latestData;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            latestData = (TextView) view.findViewById(R.id.latest);
        }

        public void bind(String data) {
            latestData.setText(data);
        }

        static ViewHolder create(ViewGroup parent)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_widget, parent, false);
            return new ViewHolder(view);
        }
        public TextView getLatestData() {
            return latestData;
        }

    }

    public HomeAdapter(@NonNull DiffUtil.ItemCallback<HomeWidget> diffCallback) {
        super(diffCallback);
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
        HomeWidget current = getItem(position);
        viewHolder.bind(current.data);
    }

    // Return the size of your dataset (invoked by the layout manager)
    public static class WordDiff extends DiffUtil.ItemCallback<HomeWidget> {

        @Override
        public boolean areItemsTheSame(@NonNull HomeWidget oldItem, @NonNull HomeWidget newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull HomeWidget oldItem, @NonNull HomeWidget newItem) {
            return oldItem.data.equals(newItem.data);
        }
    }

}


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
import com.zybooks.myapplication.ui.lifts.LiftWidget;

import java.util.List;

public class CustomAdapater extends ListAdapter<LiftWidget, CustomAdapater.ViewHolder> {


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

        public void bind(String type, String weight, String rep) {
            type_textView.setText(type);
            weight_textView.setText(weight);
            rep_textView.setText(rep);
        }

        static ViewHolder create(ViewGroup parent)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lift_widget, parent, false);
            return new ViewHolder(view);
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

    public CustomAdapater(@NonNull DiffUtil.ItemCallback<LiftWidget> diffCallback) {
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
        LiftWidget current = getItem(position);
        viewHolder.bind(current.name, current.weight, current.reps);
    }

    // Return the size of your dataset (invoked by the layout manager)
    public static class WordDiff extends DiffUtil.ItemCallback<LiftWidget> {

        @Override
        public boolean areItemsTheSame(@NonNull LiftWidget oldItem, @NonNull LiftWidget newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull LiftWidget oldItem, @NonNull LiftWidget newItem) {
            return oldItem.name.equals(newItem.name) && oldItem.reps.equals(newItem.reps)
                    && oldItem.weight.equals(newItem.weight);
        }
    }

}





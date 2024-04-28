package com.zybooks.myapplication.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.myapplication.R;
import com.zybooks.myapplication.ui.lifts.DatabaseToUiModel;
import com.zybooks.myapplication.ui.lifts.LiftWidget;

import java.util.List;

public class CustomAdapter extends ListAdapter<LiftWidget, CustomAdapter.ViewHolder> {

    private ViewModelStoreOwner mOwner;
    private Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView type_textView;
        private final TextView weight_textView;
        private final TextView rep_textView;
        private ViewModelStoreOwner mOwn;
        private DatabaseToUiModel dum;
        private final Button removeButton;

        public ViewHolder(View view, ViewModelStoreOwner mO) {
            super(view);
            // Define click listener for the ViewHolder's View

            mOwn = mO;

            dum = new ViewModelProvider(mO).get(DatabaseToUiModel.class);

            removeButton = (Button) view.findViewById(R.id.EditButton);
            type_textView = (TextView) view.findViewById(R.id.workName);
            weight_textView = (TextView) view.findViewById(R.id.weight);
            rep_textView = (TextView) view.findViewById(R.id.reps);

        }

        public void bind(String type, String weight, String rep) {
            type_textView.setText(type);
            weight_textView.setText(weight);
            rep_textView.setText(rep);

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dum.delete(type);
                }
            });
        }


        static ViewHolder create(ViewGroup parent, ViewModelStoreOwner vm)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lift_widget, parent, false);
            return new ViewHolder(view, vm);
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

    public CustomAdapter(@NonNull DiffUtil.ItemCallback<LiftWidget> diffCallback, ViewModelStoreOwner vs) {
        super(diffCallback);
        mOwner = vs;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.create(parent, mOwner);
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





package com.zybooks.myapplication.ui.lifts;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zybooks.myapplication.MainActivity;
import com.zybooks.myapplication.R;
import com.zybooks.myapplication.databinding.FragmentLiftsBinding;
import com.zybooks.myapplication.ui.CustomAdapater;

public class LiftsFragment extends Fragment {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private FragmentLiftsBinding binding;
    RecyclerView recyclerView;
    private DatabaseToUiModel dum;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LiftsViewModel liftsViewModel =
                new ViewModelProvider(this).get(LiftsViewModel.class);

        dum = new ViewModelProvider(this).get(DatabaseToUiModel.class);


        binding = FragmentLiftsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        final TextView textView = binding.textLifts;
        liftsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        String[] testType = {"Bicep Curls", "Tricep Curls"};
        String[] testPounds = {"10 Pounds", "100 Pounds"};
        String[] testReps = {"10 Reps", "100 Reps"};

        CustomAdapater adapter = new CustomAdapater(new CustomAdapater.WordDiff());

        dum.getAll().observe(getViewLifecycleOwner(), words ->
        {
            adapter.submitList(words);
        });

/*
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener( view -> {
            dum.insert(new LiftWidget("Test", "Test", "Test"));
        });
*/

        recyclerView.setAdapter(adapter);
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
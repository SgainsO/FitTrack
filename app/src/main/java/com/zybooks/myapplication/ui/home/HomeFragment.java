package com.zybooks.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.myapplication.R;
import com.zybooks.myapplication.databinding.FragmentHomeBinding;
import com.zybooks.myapplication.ui.CustomAdapter;
import com.zybooks.myapplication.ui.HomeAdapter;
import com.zybooks.myapplication.ui.WeightAdapter;

public class HomeFragment extends Fragment {

    private HomeDatabaseToUiModel data;
    private FragmentHomeBinding binding;

    private RecyclerView recycleLift;
    private RecyclerView recycleWeight;
    private RecyclerView recycleProgress;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recycleLift = root.findViewById(R.id.latest_lift_value);
        recycleLift.setLayoutManager(new LinearLayoutManager(requireContext()));

        recycleWeight = root.findViewById(R.id.recent_weight_value);
        recycleWeight.setLayoutManager(new LinearLayoutManager(requireContext()));

        recycleProgress = root.findViewById(R.id.progress_weight_value);
        recycleProgress.setLayoutManager(new LinearLayoutManager(requireContext()));

        data = new ViewModelProvider(this).get(HomeDatabaseToUiModel.class);

        CustomAdapter cadapter = new CustomAdapter(new CustomAdapter.WordDiff());
        WeightAdapter wadapter = new WeightAdapter(new WeightAdapter.WordDiff());

        data.getAllLifts().observe(getViewLifecycleOwner(), words ->
        {
            cadapter.submitList(words);
        });

        data.getAllWeights().observe(getViewLifecycleOwner(), words ->
        {
            wadapter.submitList(words);
        });

        final TextView welcomeText = binding.welcome;
        homeViewModel.welcomeGetText().observe(getViewLifecycleOwner(), welcomeText::setText);

        final TextView progressText = binding.progress;
        homeViewModel.progressGetText().observe(getViewLifecycleOwner(), progressText::setText);

        final TextView recentWeightText = binding.recentWeight;
        homeViewModel.recWeightGetText().observe(getViewLifecycleOwner(), recentWeightText::setText);

        final TextView latestLiftText = binding.latestLift;
        homeViewModel.latLiftGetText().observe(getViewLifecycleOwner(), latestLiftText::setText);

        final TextView progressWeightText = binding.progressWeight;
        homeViewModel.progWeightGetText().observe(getViewLifecycleOwner(), progressWeightText::setText);

        //TextView latestLiftValue = binding.latestLiftValue;
        //homeViewModel.latLiftValueGetText().observe(getViewLifecycleOwner(), latestLiftValue::setText);

        //TextView recentWeightValue = binding.recentWeightValue;
        //homeViewModel.recWeightValueGetText().observe(getViewLifecycleOwner(), recentWeightValue::setText);

        //TextView progressWeightValue = binding.progressWeightValue;
        //homeViewModel.progWeightValueGetText().observe(getViewLifecycleOwner(), progressWeightValue::setText);

        recycleLift.setAdapter(cadapter);
        recycleWeight.setAdapter(wadapter);
        recycleProgress.setAdapter(wadapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
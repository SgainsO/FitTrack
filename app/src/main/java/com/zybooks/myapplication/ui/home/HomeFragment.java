package com.zybooks.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.zybooks.myapplication.databinding.FragmentHomeBinding;
import com.zybooks.myapplication.ui.lifts.DatabaseToUiModel;
import com.zybooks.myapplication.ui.lifts.LiftWidget;
import com.zybooks.myapplication.ui.weight.W_DatabaseToUiModel;
import com.zybooks.myapplication.ui.weight.WeightWidget;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class HomeFragment extends Fragment {
    private DatabaseToUiModel lData;
    private W_DatabaseToUiModel wData;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lData = new ViewModelProvider(this).get(DatabaseToUiModel.class);
        wData = new ViewModelProvider(this).get(W_DatabaseToUiModel.class);

        TextView recentWeightValue = binding.recentWeightValue;
        wData.getLatestWeight().observe(getViewLifecycleOwner(), new Observer<List<WeightWidget>>() {
            @Override
            public void onChanged(List<WeightWidget> weightWidgets) {
                if(weightWidgets.size() != 0) {
                    recentWeightValue.setText(weightWidgets.get(0).getWeight());
                }
            }
        });

        TextView latestLiftValue = binding.latestLiftValue;
        lData.getLatestLift().observe(getViewLifecycleOwner(), new Observer<List<LiftWidget>>() {
            @Override
            public void onChanged(List<LiftWidget> liftWidgets) {
                if(liftWidgets.size() != 0) {
                    latestLiftValue.setText(liftWidgets.get(0).getName());
                }
            }
        });

        TextView progressWeightValue = binding.progressWeightValue;
        wData.getLatestWeight().observe(getViewLifecycleOwner(), new Observer<List<WeightWidget>>() {
            @Override
            public void onChanged(List<WeightWidget> weightWidgets) {
                if(weightWidgets.size() != 0) {
                    String curWeight = weightWidgets.get(0).getWeight();
                    String realWeight = "";
                    for(int i = 0; i < curWeight.length(); i++) {
                        char s = curWeight.charAt(i);
                        if (Character.isDigit(s)) {
                            realWeight += s;
                        }
                    }

                    double currentWeight = Double.parseDouble(realWeight);
                    double goalWeight = getLatestGoal();
                    double difference = Math.abs(goalWeight - currentWeight);

                    String progress = Double.toString(difference) + " lbs to go!";
                    progressWeightValue.setText(progress);
                }
            }
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

        return root;
    }

    private double getLatestGoal() {
        double goalWeight = 0;
        try {
            FileInputStream fis = requireActivity().openFileInput("goals.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
            br.close();
            goalWeight = Double.parseDouble(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goalWeight;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
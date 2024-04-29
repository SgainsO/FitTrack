package com.zybooks.myapplication.ui.weight;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.zybooks.myapplication.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zybooks.myapplication.databinding.FragmentWeightBinding;
import com.zybooks.myapplication.ui.WeightAdapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class WeightFragment extends Fragment {

    RecyclerView recyclerView;
    private W_DatabaseToUiModel dum;
    private Context mcon;

    private TextView goalTextView;
    private EditText goalEditText;

    FloatingActionButton insertButton;

    private FragmentWeightBinding binding;

    @Override
    public void onAttach(Context con) {
        super.onAttach(con);
        mcon = con;
    }

    @Override
    public void onResume(){
        super.onResume();
        displayLatestGoal();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WeightViewModel weightViewModel =
                new ViewModelProvider(this).get(WeightViewModel.class);
        // Might need to change this //



        dum = new ViewModelProvider(this).get(W_DatabaseToUiModel.class);


        binding = FragmentWeightBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        final TextView textView = binding.currentWeightGoal;
        weightViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        WeightAdapter w_adapter = new WeightAdapter(new WeightAdapter.WordDiff());

        dum.getAll().observe(getViewLifecycleOwner(), words ->
        {
            w_adapter.submitList(words);
        });
        goalTextView = root.findViewById(R.id.current_weight_goal);

        root.findViewById(R.id.add_weight_goal_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showAddGoalDialog();
            }
        });
        displayLatestGoal();

        insertButton = root.findViewById(R.id.insert_butt);

        insertButton.setOnClickListener( view -> {
            LayoutInflater infl = LayoutInflater.from(mcon);
            View dialView = infl.inflate(R.layout.addnewweight, null);

            EditText weight = dialView.findViewById(R.id.input_weight);
            EditText date = dialView.findViewById(R.id.input_date);

            AlertDialog.Builder builder = new AlertDialog.Builder(mcon);
            builder.setView(dialView)
                    .setTitle("Record Weight")
                    .setPositiveButton("Add", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dia, int which){
                            String w = weight.getText().toString();

                            String d = date.getText().toString();

                            if (d.length() > 10){
                                String text = String.format("Date is not valid, input in mm/dd/yyyy form");
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(root.getContext(), text, duration);
                                toast.show();
                            }
                            else if (w.length() > 4){
                                String text = String.format("Weight is above maximum limit (>9999 lbs)");
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(root.getContext(), text, duration);
                                toast.show();
                            }
                            else{
                                w += " lbs";
                                dum.insert(new WeightWidget(d, w));
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        });

        // Calculate BMI logic
        EditText editHeight = root.findViewById(R.id.edit_height);
        EditText editWeight = root.findViewById(R.id.edit_weight);
        Button btnCalculateBMI = root.findViewById(R.id.btn_calculate_bmi);

        btnCalculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editHeight.getText().toString().isEmpty() || editWeight.getText().toString().isEmpty()) {

                }
                else {
                    double height = Double.parseDouble(editHeight.getText().toString());
                    double weight = Double.parseDouble(editWeight.getText().toString());

                    // Calculate BMI (BMI formula: weight (kg) / (height (m) * height (m)))
                    double bmi = (weight / (height * height)) * 703;

                    // Display the result
                    String text = String.format("Your BMI: %.2f", bmi);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(root.getContext(), text, duration);
                    toast.show();
                }
            }
        });

        recyclerView.setAdapter(w_adapter);
        return root;
    }

    private void showAddGoalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.addnewgoal, null);
        EditText goalEditText = dialogView.findViewById(R.id.input_goal);
        builder.setView(dialogView);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String goalString = goalEditText.getText().toString();
                if (!TextUtils.isEmpty(goalString)) {
                    double goal = Double.parseDouble(goalString);
                    saveGoalToFile(goal);
                    displayLatestGoal();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void saveGoalToFile(double goal) {
        Context context = getContext();
        if (context != null){
            try {
                FileOutputStream fos = context.openFileOutput("goals.txt", Context.MODE_PRIVATE);
                fos.write(String.valueOf(goal).getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayLatestGoal() {

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
            goalTextView.setText(stringBuilder.toString() + " lbs");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
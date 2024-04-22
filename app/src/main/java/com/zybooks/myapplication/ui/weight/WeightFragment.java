package com.zybooks.myapplication.ui.weight;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.zybooks.myapplication.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zybooks.myapplication.databinding.FragmentWeightBinding;
import com.zybooks.myapplication.ui.WeightAdapter;

public class WeightFragment extends Fragment {

    RecyclerView recyclerView;
    private W_DatabaseToUiModel dum;
    private Context mcon;

    FloatingActionButton insertButton;

    private FragmentWeightBinding binding;

    @Override
    public void onAttach(Context con) {
        super.onAttach(con);
        mcon = con;
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


        final TextView textView = binding.textWeight;
        weightViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        WeightAdapter w_adapter = new WeightAdapter(new WeightAdapter.WordDiff());

        dum.getAll().observe(getViewLifecycleOwner(), words ->
        {
            w_adapter.submitList(words);
        });

        insertButton = root.findViewById(R.id.insert_butt);

        insertButton.setOnClickListener( view -> {
            LayoutInflater infl = LayoutInflater.from(mcon);
            View dialView = infl.inflate(R.layout.addnewweight, null);

            EditText weight = dialView.findViewById(R.id.input_weight);
            EditText date = dialView.findViewById(R.id.input_date);

            AlertDialog.Builder builder = new AlertDialog.Builder(mcon);
            builder.setView(dialView)
                    .setTitle("Add Workout")
                    .setPositiveButton("Add", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dia, int which){
                            String w = weight.getText().toString();
                            String d = date.getText().toString();

                            dum.insert(new WeightWidget(d, w));
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        });

        recyclerView.setAdapter(w_adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.exercise.app30day.features.report;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.exercise.app30day.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.shawnlin.numberpicker.NumberPicker;

public class UpdateMetricsBottomSheet extends BottomSheetDialogFragment {

    private NumberPicker weightPicker;
    private NumberPicker heightPicker;
    private Button btnCancel;
    private Button btnSave;

    private double currentWeight = 70.0;
    private double currentHeight = 170.0;

    private OnMetricsUpdatedListener listener;

    public interface OnMetricsUpdatedListener {
        void onMetricsUpdated(double weight, double height);
    }

    public static UpdateMetricsBottomSheet newInstance(double currentWeight, double currentHeight) {
        UpdateMetricsBottomSheet fragment = new UpdateMetricsBottomSheet();
        Bundle args = new Bundle();
        args.putDouble("current_weight", currentWeight);
        args.putDouble("current_height", currentHeight);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnMetricsUpdatedListener) {
            listener = (OnMetricsUpdatedListener) context;
        } else if (getParentFragment() instanceof OnMetricsUpdatedListener) {
            listener = (OnMetricsUpdatedListener) getParentFragment();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);

        if (getArguments() != null) {
            currentWeight = getArguments().getDouble("current_weight", 70.0);
            currentHeight = getArguments().getDouble("current_height", 170.0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_update_metrics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupPickers();
        setupListeners();
    }

    private void initViews(View view) {
        weightPicker = view.findViewById(R.id.weight_picker);
        heightPicker = view.findViewById(R.id.height_picker);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnSave = view.findViewById(R.id.btn_save);
    }

    private void setupPickers() {
        // Setup weight picker
        weightPicker.setMinValue(30);
        weightPicker.setMaxValue(150);
        weightPicker.setValue((int) currentWeight);

        // For displaying decimal values in weight picker
        final String[] weightValues = new String[121]; // 30.0 to 150.0
        for (int i = 0; i < weightValues.length; i++) {
            double value = 30.0 + (i * 0.1);
            weightValues[i] = String.format("%.1f", value);
        }
        weightPicker.setDisplayedValues(weightValues);

        // Setup height picker
        heightPicker.setMinValue(120);
        heightPicker.setMaxValue(220);
        heightPicker.setValue((int) currentHeight);
    }

    private void setupListeners() {
        btnCancel.setOnClickListener(v -> dismiss());

        btnSave.setOnClickListener(v -> {
            double selectedWeight = Double.parseDouble(weightPicker.getDisplayedValues()[weightPicker.getValue() - 30]);
            double selectedHeight = heightPicker.getValue();

            if (listener != null) {
                listener.onMetricsUpdated(selectedWeight, selectedHeight);
            }

            dismiss();
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
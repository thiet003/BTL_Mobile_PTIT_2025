package com.exercise.app30day.features.report;

import static com.exercise.app30day.config.AppConfig.MAX_HEIGHT;
import static com.exercise.app30day.config.AppConfig.MAX_WEIGHT;
import static com.exercise.app30day.config.AppConfig.MIN_HEIGHT;
import static com.exercise.app30day.config.AppConfig.MIN_WEIGHT;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.exercise.app30day.R;
import com.exercise.app30day.config.AppConfig;
import com.exercise.app30day.databinding.DialogUserMetricsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.Serializable;

public class UserMetricsBottomSheet extends BottomSheetDialogFragment {

    private DialogUserMetricsBinding binding;

    private double currentWeight;
    private double currentHeight;

    private OnMetricsUpdatedListener listener;

    private String[] displayedWeights;

    private static final String CURRENT_WEIGHT_KEY = "current_weight";
    private static final String CURRENT_HEIGHT_KEY = "current_height";
    private static final String METRICS_UPDATE_LISTENER_KEY = "metrics_update_listener";

    public interface OnMetricsUpdatedListener extends Serializable {
        void onMetricsUpdated(double weight, double height);
    }

    private UserMetricsBottomSheet(){
    }

    public static UserMetricsBottomSheet newInstance(double currentWeight, double currentHeight, OnMetricsUpdatedListener listener) {
        UserMetricsBottomSheet fragment = new UserMetricsBottomSheet();
        Bundle args = new Bundle();
        args.putDouble(CURRENT_WEIGHT_KEY, currentWeight);
        args.putDouble(CURRENT_HEIGHT_KEY, currentHeight);
        args.putSerializable(METRICS_UPDATE_LISTENER_KEY, listener);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);

        if (getArguments() != null) {
            currentWeight = getArguments().getDouble(CURRENT_WEIGHT_KEY, 70.0);
            currentHeight = getArguments().getDouble(CURRENT_HEIGHT_KEY, 170.0);
            listener = (OnMetricsUpdatedListener) getArguments().getSerializable(METRICS_UPDATE_LISTENER_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogUserMetricsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int weightRange = (AppConfig.MAX_WEIGHT - AppConfig.MIN_WEIGHT) * 10 + 1;
        displayedWeights = new String[weightRange];
        binding.weightPicker.setMinValue(0);
        binding.weightPicker.setMaxValue(weightRange - 1);
        for (int i = 0; i < weightRange; i++) {
            float weight = AppConfig.MIN_WEIGHT + i * 0.1f;
            if(weight == currentWeight){
                binding.weightPicker.setValue(i);
            }
            displayedWeights[i] = String.format("%.1f", weight);
        }
        binding.weightPicker.setDisplayedValues(displayedWeights);

        binding.heightPicker.setMinValue(MIN_HEIGHT);
        binding.heightPicker.setMaxValue(MAX_HEIGHT);
        binding.heightPicker.setValue((int) currentHeight);

        binding.btnCancel.setOnClickListener(v -> dismiss());

        binding.btnSave.setOnClickListener(v->{
            double selectedWeight = Double.parseDouble(displayedWeights[binding.weightPicker.getValue()]);
            double selectedHeight = binding.heightPicker.getValue();

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
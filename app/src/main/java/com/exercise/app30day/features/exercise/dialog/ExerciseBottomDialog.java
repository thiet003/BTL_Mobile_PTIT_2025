package com.exercise.app30day.features.exercise.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.exercise.app30day.R;
import com.exercise.app30day.databinding.BottomDialogExerciseBinding;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.utils.ScreenUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExerciseBottomDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    BottomDialogExerciseBinding binding;
    ExerciseViewPagerAdapter exerciseViewPagerAdapter;
    private List<ExerciseItem> listExerciseItem;
    private int startPosition;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static ExerciseBottomDialog newInstance(List<ExerciseItem> listExerciseItem, int startPosition) {
        ExerciseBottomDialog fragment = new ExerciseBottomDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) listExerciseItem);
        args.putInt(ARG_PARAM2, startPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listExerciseItem = (List<ExerciseItem>) getArguments().getSerializable(ARG_PARAM1);
            startPosition = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomDialogExerciseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FrameLayout parent = (FrameLayout) view.getParent();
        view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (ScreenUtils.getScreenHeight() * 0.85)));
        parent.setBackgroundResource(R.drawable.bg_primary_top_corner);
        BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(parent);
        behavior.setPeekHeight((int) (ScreenUtils.getScreenHeight() * 0.85));

        exerciseViewPagerAdapter = new ExerciseViewPagerAdapter(requireActivity());
        exerciseViewPagerAdapter.setData(listExerciseItem);
        binding.vpExercise.setAdapter(exerciseViewPagerAdapter);
        binding.vpExercise.setCurrentItem(startPosition, false);
        binding.tvExerciseName.setText(listExerciseItem.get(startPosition).getName());
        binding.vpExercise.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tvPage.setText(requireContext().getString(R.string.page, position + 1, exerciseViewPagerAdapter.getItemCount()));
                binding.tvExerciseName.setText(listExerciseItem.get(position).getName());
            }
        });
        binding.tvPage.setText(requireContext().getString(R.string.page, startPosition + 1, exerciseViewPagerAdapter.getItemCount()));
        binding.btnNext.setOnClickListener(this);
        binding.btnPrevious.setOnClickListener(this);
        binding.btnClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.btnClose) {
            dismiss();
        }else if(v == binding.btnNext) {
            if(binding.vpExercise.getCurrentItem() == exerciseViewPagerAdapter.getItemCount() - 1) return;
            binding.vpExercise.setCurrentItem(binding.vpExercise.getCurrentItem() + 1);
            binding.tvPage.setText(requireContext().getString(R.string.page, binding.vpExercise.getCurrentItem() + 1, exerciseViewPagerAdapter.getItemCount()));
        }else{
            if(binding.vpExercise.getCurrentItem() == 0) return;
            binding.vpExercise.setCurrentItem(binding.vpExercise.getCurrentItem() - 1);
            binding.tvPage.setText(requireContext().getString(R.string.page, binding.vpExercise.getCurrentItem() + 1, exerciseViewPagerAdapter.getItemCount()));
        }
    }
}

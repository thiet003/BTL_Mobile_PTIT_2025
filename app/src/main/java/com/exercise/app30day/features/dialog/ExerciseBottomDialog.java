package com.exercise.app30day.features.dialog;

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

import java.util.ArrayList;
import java.util.List;

public class ExerciseBottomDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    BottomDialogExerciseBinding binding;

    private final List<ExerciseFragment> listFragment;
    
    private final int startPosition;

    public ExerciseBottomDialog(List<ExerciseItem> listExerciseItem, int startPosition) {
        this.startPosition = startPosition;
        listFragment = new ArrayList<>();
        for(ExerciseItem item : listExerciseItem){
            listFragment.add(new ExerciseFragment(item));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomDialogExerciseBinding.inflate(inflater, container, false);
        ExerciseViewPagerAdapter exerciseViewPagerAdapter = new ExerciseViewPagerAdapter(requireActivity() , listFragment);
        binding.vpExercise.setAdapter(exerciseViewPagerAdapter);
        binding.vpExercise.setCurrentItem(startPosition);
        binding.vpExercise.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tvPage.setText(requireContext().getString(R.string.page, position + 1, listFragment.size()));
            }
        });
        binding.tvPage.setText(requireContext().getString(R.string.page, startPosition + 1, listFragment.size()));
        binding.btnNext.setOnClickListener(this);
        binding.btnPrevious.setOnClickListener(this);
        binding.btnClose.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            FrameLayout parent = (FrameLayout) view.getParent();
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (ScreenUtils.getScreenHeight() * 0.85)));
            parent.setBackgroundResource(R.drawable.bg_primary_top_corner);
            BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(parent);
            behavior.setPeekHeight((int) (ScreenUtils.getScreenHeight() * 0.85));
        }
    }

    @Override
    public void onClick(View v) {
        if(v == binding.btnClose) {
            dismiss();
        }else if(v == binding.btnNext) {
            if(binding.vpExercise.getCurrentItem() == listFragment.size() - 1) return;
            binding.vpExercise.setCurrentItem(binding.vpExercise.getCurrentItem() + 1);
            binding.tvPage.setText(requireContext().getString(R.string.page, binding.vpExercise.getCurrentItem() + 1, listFragment.size()));
        }else{
            if(binding.vpExercise.getCurrentItem() == 0) return;
            binding.vpExercise.setCurrentItem(binding.vpExercise.getCurrentItem() - 1);
            binding.tvPage.setText(requireContext().getString(R.string.page, binding.vpExercise.getCurrentItem() + 1, listFragment.size()));
        }
    }
}

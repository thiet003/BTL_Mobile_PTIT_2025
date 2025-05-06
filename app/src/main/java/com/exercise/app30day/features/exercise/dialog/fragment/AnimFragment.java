package com.exercise.app30day.features.exercise.dialog.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.exercise.app30day.base.BaseFragment;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.databinding.FragmentAnimBinding;

public class AnimFragment extends BaseFragment<FragmentAnimBinding, NoneViewModel>{

    private String url;

    private static final String ARG_PARAM1 = "param1";


    public static AnimFragment newInstance(String url) {
        AnimFragment fragment = new AnimFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    protected void initView() {
        binding.mediaPlayer.load(url);
    }

    @Override
    protected void initListener() {

    }
}
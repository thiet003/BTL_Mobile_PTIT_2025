package com.exercise.app30day.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

import java.util.Objects;

public abstract class BaseFragment<VB extends ViewBinding, VM extends ViewModel> extends Fragment {

    public BaseFragment() {
        // Required empty public constructor
    }

    protected VB binding;
    protected VM viewModel;

    protected abstract void initView();

    protected abstract void initListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BindingReflex.reflexViewBinding(getClass(), inflater);
        return Objects.requireNonNull(binding).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelHelper.createViewModel(this, getClass());
        initView();
        initListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

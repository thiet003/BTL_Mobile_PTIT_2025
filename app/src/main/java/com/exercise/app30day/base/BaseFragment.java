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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BindingReflex.reflexViewBinding(getClass(), inflater);
        viewModel = ViewModelHelper.createViewModel(this, getClass());
        initView();
        initListener();
        return Objects.requireNonNull(binding).getRoot();
    }

    protected abstract void initView();

    protected abstract void initListener();

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

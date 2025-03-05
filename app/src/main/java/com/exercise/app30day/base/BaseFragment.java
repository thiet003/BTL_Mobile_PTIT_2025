package com.exercise.app30day.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
        binding = createBinding(inflater, container);
        viewModel = createViewModel();
        initView();
        initListener();
        return Objects.requireNonNull(binding).getRoot();
    }

    protected abstract void initView();

    protected abstract void initListener();

    @SuppressWarnings("unchecked")
    private VB createBinding(LayoutInflater inflater, ViewGroup container) {
        try {
            Type superclass = getClass().getGenericSuperclass();
            if (superclass instanceof ParameterizedType) {
                Class<VB> bindingClass = (Class<VB>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
                Method inflateMethod = bindingClass.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
                return (VB) inflateMethod.invoke(null, inflater, container, false);
            }
        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize ViewBinding", e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private VM createViewModel() {
        try {
            Class<VM> viewModelClass = (Class<VM>) ((ParameterizedType) Objects.requireNonNull(getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[1];

            return new ViewModelProvider(this).get(viewModelClass);
        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize ViewModel", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

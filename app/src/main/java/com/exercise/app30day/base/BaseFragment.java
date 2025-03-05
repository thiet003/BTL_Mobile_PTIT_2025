package com.exercise.app30day.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {

    public BaseFragment() {
        // Required empty public constructor
    }

    protected VB binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = createBinding(inflater, container);
        initView();
        initListener();
        return Objects.requireNonNull(binding).getRoot();
    }

    protected abstract void initView();

    protected abstract void initListener();

    @SuppressWarnings("unchecked")
    private VB createBinding(LayoutInflater inflater, ViewGroup container) {
        try {
            // Lấy class ViewBinding từ generic type
            Type superclass = getClass().getGenericSuperclass();
            if (superclass instanceof ParameterizedType) {
                Class<VB> bindingClass = (Class<VB>) ((ParameterizedType) superclass).getActualTypeArguments()[0];

                // Gọi phương thức inflate(LayoutInflater, ViewGroup, boolean) của ViewBinding
                Method inflateMethod = bindingClass.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
                return (VB) inflateMethod.invoke(null, inflater, container, false);
            }
        } catch (Exception e) {
            throw new RuntimeException("Cannot initial ViewBinding", e);
        }
        return null;
    }
}

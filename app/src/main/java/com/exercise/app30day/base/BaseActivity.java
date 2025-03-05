package com.exercise.app30day.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.exercise.app30day.R;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public abstract class BaseActivity<VB extends ViewBinding, VM extends ViewModel> extends AppCompatActivity {

    protected VB binding;
    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = createBinding();
        viewModel = createViewModel();
        setContentView(Objects.requireNonNull(binding).getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        initListener();
    }

    protected abstract void initView();

    protected abstract void initListener();

    @Override
    protected void onStart() {
        super.onStart();
        hideNavigationBar();
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @SuppressWarnings("unchecked")
    private VB createBinding() {
        try {
            Type superclass = getClass().getGenericSuperclass();
            if (superclass instanceof ParameterizedType) {
                Class<VB> bindingClass = (Class<VB>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
                Method inflateMethod = bindingClass.getMethod("inflate", LayoutInflater.class);
                return (VB) inflateMethod.invoke(null, getLayoutInflater());
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
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

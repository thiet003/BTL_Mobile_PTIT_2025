package com.exercise.app30day.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

import com.exercise.app30day.utils.LanguageUtils;

public abstract class BaseActivity<VB extends ViewBinding, VM extends ViewModel> extends AppCompatActivity {

    protected VB binding;
    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BindingReflex.reflexViewBinding(getClass(), getLayoutInflater());
        viewModel = ViewModelHelper.createViewModel(this, getClass());
        View rootView = binding.getRoot();
        setContentView(rootView);
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

    protected void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageUtils.setLocale(newBase));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

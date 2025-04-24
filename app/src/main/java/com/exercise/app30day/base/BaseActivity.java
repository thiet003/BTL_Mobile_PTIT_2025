package com.exercise.app30day.base;

import static com.exercise.app30day.utils.HawkKeys.LANGUAGE_CODE_SNIP_KEY;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

import com.orhanobut.hawk.Hawk;

import java.util.Locale;

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

    protected Context setLocale(Context context) {
        String languageCode = Hawk.get(LANGUAGE_CODE_SNIP_KEY, "en");
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(setLocale(newBase));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

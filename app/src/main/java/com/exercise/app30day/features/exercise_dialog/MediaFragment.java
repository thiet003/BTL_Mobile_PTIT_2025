package com.exercise.app30day.features.exercise_dialog;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.exercise.app30day.base.BaseFragment;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.databinding.FragmentMediaBinding;

public class MediaFragment extends BaseFragment<FragmentMediaBinding, NoneViewModel> {

    private final int type;

    private final String url;
    public MediaFragment(String url, int type) {
        this.url = url;
        this.type = type;

    }
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        switch (type){
            case 0:
                binding.mediaPlayer.setVisibility(View.VISIBLE);
                binding.webView.setVisibility(View.GONE);
                binding.mediaPlayer.display(url);
                break;
            case 1:
                binding.mediaPlayer.setVisibility(View.GONE);
                binding.webView.setVisibility(View.VISIBLE);
                WebView webView = binding.webView;

                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setDomStorageEnabled(true);
                webView.setWebViewClient(new WebViewClient());

                webView.loadUrl(url);
                break;
        }
    }

    @Override
    protected void initListener() {

    }
}

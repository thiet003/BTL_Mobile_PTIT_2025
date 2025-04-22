package com.exercise.app30day.features.setting;

import static com.exercise.app30day.utils.HawkKeys.LANGUAGE_CODE_SNIP_KEY;
import static com.exercise.app30day.utils.IntentKeys.EXTRA_LANGUAGE_CHANGED;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exercise.app30day.R;
import com.exercise.app30day.features.splash.SplashActivity;
import com.exercise.app30day.items.LanguageItem;
import com.exercise.app30day.utils.IntentKeys;
import com.orhanobut.hawk.Hawk;

import java.util.List;

public class LanguageDialog {

    private Dialog dialog;

    
    public LanguageDialog(Activity activity, List<LanguageItem> languageItems){
        initView(activity, languageItems);
    }

    private void initView(Activity activity, List<LanguageItem> languageItems) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_language);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_language);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }

        RecyclerView rvLanguage = dialog.findViewById(R.id.rv_language);
        LanguageAdapter languageAdapter = new LanguageAdapter(Hawk.get(LANGUAGE_CODE_SNIP_KEY, "en"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rvLanguage.setLayoutManager(layoutManager);
        rvLanguage.setAdapter(languageAdapter);
        languageAdapter.setData(languageItems);
        languageAdapter.setOnItemClickListener((data, position) -> {
            languageAdapter.setCodeSnip(data.getCodeSnip());
            Hawk.put(LANGUAGE_CODE_SNIP_KEY, data.getCodeSnip());
            Intent intent = new Intent(activity, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(EXTRA_LANGUAGE_CHANGED, true);
            activity.startActivity(intent);
            activity.finish();
        });
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }

    public void show(){
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public void hide(){
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}

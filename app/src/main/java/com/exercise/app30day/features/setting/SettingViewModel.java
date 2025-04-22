package com.exercise.app30day.features.setting;

import static com.exercise.app30day.utils.HawkKeys.LANGUAGE_CODE_SNIP_KEY;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.lifecycle.ViewModel;

import com.exercise.app30day.R;
import com.exercise.app30day.items.LanguageItem;
import com.orhanobut.hawk.Hawk;

import java.util.List;

public class SettingViewModel extends ViewModel {

    public List<LanguageItem> getLanguageItems() {
        return List.of(
                new LanguageItem(1, R.string.vietnamese, R.drawable.ic_flag_vietnamese, "vi"),
                new LanguageItem(2, R.string.english, R.drawable.ic_flag_english, "en")
        );
    }

    public LanguageItem getLanguageItem(){
        String languageCode = Hawk.get(LANGUAGE_CODE_SNIP_KEY, "en");
        for (LanguageItem item : getLanguageItems()) {
            if (item.getCodeSnip().equals(languageCode)) {
                return item;
            }
        }
        return null;
    }

    public String getVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
    }
}

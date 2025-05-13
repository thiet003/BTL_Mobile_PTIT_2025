package com.exercise.app30day.utils;

import static com.exercise.app30day.utils.HawkKeys.LANGUAGE_CODE_SNIP_KEY;

import android.content.Context;
import android.content.res.Configuration;

import com.exercise.app30day.R;
import com.exercise.app30day.items.LanguageItem;
import com.orhanobut.hawk.Hawk;

import java.util.List;
import java.util.Locale;

public final class LanguageUtils {

    public static List<LanguageItem> getLanguages(){
        return List.of(
                new LanguageItem(1, R.string.vietnamese, R.drawable.ic_flag_vietnamese, "vi"),
                new LanguageItem(2, R.string.english, R.drawable.ic_flag_english, "en")
        );
    }

    public static Context setLocale(Context context) {
        String languageCode = Hawk.get(LANGUAGE_CODE_SNIP_KEY, "en");
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    public static LanguageItem getLanguage(){
        String languageCode = Hawk.get(LANGUAGE_CODE_SNIP_KEY, "en");
        for (LanguageItem item : getLanguages()) {
            if (item.getCodeSnip().equals(languageCode)) {
                return item;
            }
        }
        return null;
    }

    public static void setLanguage(String languageCode) {
        Hawk.put(LANGUAGE_CODE_SNIP_KEY, languageCode);
    }
}

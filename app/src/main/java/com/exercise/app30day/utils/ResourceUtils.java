package com.exercise.app30day.utils;

import android.content.Context;

public final class ResourceUtils {
    public static int getDrawableId(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public static int getStringId(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

    public static int getLayoutId(Context context, String name) {
        return context.getResources().getIdentifier(name, "layout", context.getPackageName());
    }

    public static int getRawId(Context context, String name) {
        return context.getResources().getIdentifier(name, "raw", context.getPackageName());
    }
}

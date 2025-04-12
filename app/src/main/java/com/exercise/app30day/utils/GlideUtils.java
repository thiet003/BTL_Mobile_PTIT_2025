package com.exercise.app30day.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public final class GlideUtils {

    public static void loadImage(Context context, ImageView imageView, String data) {
        if(data.startsWith("http")) {
            Glide.with(context)
                    .load(data)
                    .into(imageView);
        } else{
            int resourceId = ResourceUtils.getDrawableId(context, data);
            if(data.startsWith("gif_")){
                Glide.with(context)
                        .asGif()
                        .load(resourceId)
                        .into(imageView);
            }else{
                Glide.with(context)
                        .load(resourceId)
                        .into(imageView);
            }
        }


    }
}

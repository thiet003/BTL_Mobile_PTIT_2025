package com.exercise.app30day.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public final class GlideUtils {

    public static void loadImage(Context context, ImageView imageView, String data) {
        if(data == null){
            return;
        }
        if(data.startsWith("http")) {
            Glide.with(context)
                    .load(data)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        } else{
            int resourceId = ResourceUtils.getDrawableId(context, data);
            if(data.startsWith("gif_")){
                Glide.with(context)
                        .asGif()
                        .load(resourceId)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageView);
            }else{
                Glide.with(context)
                        .load(resourceId)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageView);
            }
        }


    }
}

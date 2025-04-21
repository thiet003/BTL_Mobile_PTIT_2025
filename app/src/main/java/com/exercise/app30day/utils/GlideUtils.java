package com.exercise.app30day.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.exercise.app30day.R;

public final class GlideUtils {

    public static void loadImage(Context context, ImageView imageView, String url) {
        if(url == null){
            return;
        }
        if(url.endsWith(".mp4")) {
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .placeholder(R.drawable.img_circle_loading)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }else if(url.endsWith(".gif")){
            Glide.with(context)
                    .asGif()
                    .load(url)
                    .placeholder(R.drawable.img_circle_loading)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        }else if(url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".png")) {
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .placeholder(R.drawable.img_circle_loading)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        }
    }
}

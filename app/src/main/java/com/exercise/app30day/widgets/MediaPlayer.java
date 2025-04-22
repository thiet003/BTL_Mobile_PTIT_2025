package com.exercise.app30day.widgets;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.core.content.ContextCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.bumptech.glide.Glide;
import com.exercise.app30day.R;

public class MediaPlayer extends FrameLayout implements ExoPlayer.Listener {


    private Context context;

    private ExoPlayer player;

    private ProgressBar progressBar;

    private PlayerView playerView;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    public MediaPlayer(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public MediaPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MediaPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public MediaPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
    }

    @SuppressLint("ResourceAsColor")
    @OptIn(markerClass = UnstableApi.class)
    public void display(String url) {

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        removeAllViews();
        onDestroy();
        if(url.endsWith(".mp4")){
            progressBar = new ProgressBar(context);
            progressBar.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER));
            progressBar.getIndeterminateDrawable().setColorFilter(
                    ContextCompat.getColor(context, R.color.blue),
                    android.graphics.PorterDuff.Mode.SRC_IN);
            playerView = new PlayerView(context);
            playerView.setClipChildren(true);
            playerView.setClipToPadding(true);
            playerView.setClipToOutline(true);
            playerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            playerView.setLayoutParams(layoutParams);
            playerView.setUseController(false);
            playerView.setShutterBackgroundColor(android.R.color.transparent);
            player = new ExoPlayer.Builder(context).build();
            playerView.setPlayer(player);
            player.addListener(this);
            player.setRepeatMode(Player.REPEAT_MODE_ONE);
            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(url));
            player.setMediaItem(mediaItem);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
            player.prepare();
            addView(playerView);
            addView(progressBar);
        }else if(url.endsWith(".jpg") || url.endsWith(".png")){
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(url).placeholder(R.drawable.img_circle_loading).into(imageView);
            addView(imageView);
        }else if(url.endsWith(".gif")){
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).asGif().load(url).placeholder(R.drawable.img_circle_loading).into(imageView);
            addView(imageView);
        }
    }

    public void onStart() {
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }

    public void onPause() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }

    public void onResume() {
        if (player != null) {
            player.setPlayWhenReady(playWhenReady);
        }
    }

    public void onDestroy() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentMediaItemIndex();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDestroy();
    }

    @Override
    public void onPlaybackStateChanged(int playbackState) {
        Player.Listener.super.onPlaybackStateChanged(playbackState);
        if (playbackState == Player.STATE_READY && player.getPlayWhenReady()) {
            progressBar.setVisibility(View.GONE);
            playerView.setVisibility(View.VISIBLE);
        } else if (playbackState == Player.STATE_BUFFERING) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}

package com.example.flashcard;
import android.app.Activity;
import android.app.Application;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.reflect.Field;

public class MediaPlayerApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activity.getWindow().getDecorView().post(() -> {
                    MediaPlayer mediaPlayer = MediaPlayer.create(activity, R.raw.click);
                    addClickSoundToButtons(activity.getWindow().getDecorView().getRootView(), mediaPlayer);
                });
            }

            // Other lifecycle methods are unused
            @Override public void onActivityStarted(Activity activity) {}
            @Override public void onActivityResumed(Activity activity) {}
            @Override public void onActivityPaused(Activity activity) {}
            @Override public void onActivityStopped(Activity activity) {}
            @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
            @Override public void onActivityDestroyed(Activity activity) {}

            private void addClickSoundToButtons(View view, MediaPlayer mediaPlayer) {
                if (view instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) view;
                    for (int i = 0; i < group.getChildCount(); i++) {
                        addClickSoundToButtons(group.getChildAt(i), mediaPlayer);
                    }
                } else if (view instanceof Button) {
                    Button button = (Button) view;

                    // Use reflection to get existing click listener (if any)
                    View.OnClickListener existingListener = getOnClickListener(button);

                    button.setOnClickListener(v -> {
                        // Play click sound
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.seekTo(0);
                        }
                        mediaPlayer.start();

                        // Call existing listener if it exists
                        if (existingListener != null) {
                            existingListener.onClick(v);
                        }
                    });
                }
            }

            // Use reflection to get existing OnClickListener
            private View.OnClickListener getOnClickListener(View view) {
                try {
                    Field listenerInfoField = View.class.getDeclaredField("mListenerInfo");
                    listenerInfoField.setAccessible(true);
                    Object listenerInfo = listenerInfoField.get(view);
                    if (listenerInfo == null) return null;

                    Field onClickListenerField = listenerInfo.getClass().getDeclaredField("mOnClickListener");
                    onClickListenerField.setAccessible(true);
                    return (View.OnClickListener) onClickListenerField.get(listenerInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }
}
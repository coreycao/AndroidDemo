package com.corey.customview.devart;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by sycao on 06/03/2018.
 */

public class BitmapSample {

    public static void decodeSampleBitmapFromRes(Resources res, int resId, int reqWidth, int reqHeight) {

        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        LruCache<String, Bitmap> mBitmapCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 2014;
            }
        };

    }
}

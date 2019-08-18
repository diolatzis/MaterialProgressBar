package com.diol.widget.materialprogressbar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

/**
 * Created by stavros.diolatzis on 20/2/2017.
 */

public class Utils {

    public static void runAfter(long i, Runnable runnable) {
        Handler handler = new Handler();
        handler.postDelayed(runnable,i);
    }

    public static int dpToPx(DisplayMetrics displayMetrics, int dp) {
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static void doOnceOnGlobalLayoutOfView(final View v, final Runnable r){
        if(r!=null && v!=null) {
            v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    r.run();
                }
            });
        }
    }

    public static void setDrawableColorFilter(Drawable drawable, int color)
    {
        drawable.setColorFilter(new
                PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
    }

    public static int getStatusBarHeight(Window window)
    {
        Rect rectangle = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }

    public static Bitmap resizeMapIcons(Activity activity, String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(activity.getResources(),activity.getResources().getIdentifier(iconName, "drawable", activity.getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
}

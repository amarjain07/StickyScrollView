package com.amar.library.provider;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

import com.amar.library.provider.interfaces.IScreenInfoProvider;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Created by Amar Jain on 17/03/17.
 */

@ParametersAreNonnullByDefault
public class ScreenInfoProvider implements IScreenInfoProvider{

    private final Context mContext;

    public ScreenInfoProvider(Context context) {
        mContext = context;
    }

    @Override
    public int getScreenHeight() {
        return getDeviceDimension().y;
    }

    @Override
    public int getScreenWidth() {
        return getDeviceDimension().x;
    }

    Point getDeviceDimension() {
        Point lPoint = new Point();
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        lPoint.x = metrics.widthPixels;
        lPoint.y = metrics.heightPixels;
        return lPoint;
    }
}

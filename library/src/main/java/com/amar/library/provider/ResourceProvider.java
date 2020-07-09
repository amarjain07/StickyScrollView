package com.amar.library.provider;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.StyleableRes;
import android.util.AttributeSet;

import com.amar.library.provider.interfaces.IResourceProvider;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Created by Amar Jain on 17/03/17.
 */

@ParametersAreNonnullByDefault
public class ResourceProvider implements IResourceProvider {

    private final TypedArray mTypeArray;

    public ResourceProvider(Context context, AttributeSet attrs, @StyleableRes int[] styleRes) {
        mTypeArray = context.obtainStyledAttributes(attrs, styleRes);
    }

    @Override
    public int getResourceId(@StyleableRes int styleResId) {
        return mTypeArray.getResourceId(styleResId, 0);
    }

    @Override
    public void recycle() {
        mTypeArray.recycle();
    }
}

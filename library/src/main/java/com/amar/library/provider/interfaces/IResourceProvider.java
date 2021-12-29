package com.amar.library.provider.interfaces;

import androidx.annotation.StyleableRes;

/**
 * Created by Amar Jain on 17/03/17.
 */

public interface IResourceProvider {
    int getResourceId(@StyleableRes final int styleResId);
    void recycle();
}

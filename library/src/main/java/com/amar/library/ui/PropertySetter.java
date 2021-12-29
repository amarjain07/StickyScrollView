package com.amar.library.ui;

import android.os.Build;
import androidx.core.view.ViewCompat;
import android.view.View;

/**
 * Created by Amar Jain on 28/03/17.
 */

public class PropertySetter {
    public static void setTranslationZ(View view, float translationZ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTranslationZ(view, translationZ);
        } else if (translationZ != 0) {
            view.bringToFront();
            if (view.getParent() != null) {
                ((View) view.getParent()).invalidate();
            }
        }
    }
}

package com.amar.library.ui

import android.os.Build
import android.view.View
import androidx.core.view.ViewCompat

internal object PropertySetter {
    fun setTranslationZ(view: View?, translationZ: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view?.let {
                ViewCompat.setTranslationZ(it, translationZ)
            }
        } else if (translationZ != 0f) {
            view?.bringToFront()
            if (view?.parent != null) {
                (view.parent as View).invalidate()
            }
        }
    }
}
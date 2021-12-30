package com.amar.library.provider

import android.content.Context
import android.graphics.Point
import com.amar.library.provider.interfaces.IScreenInfoProvider

internal class ScreenInfoProvider(private val mContext: Context) : IScreenInfoProvider {
    private val deviceDimension: Point
        get() {
            val lPoint = Point()
            val metrics = mContext.resources.displayMetrics
            lPoint.x = metrics.widthPixels
            lPoint.y = metrics.heightPixels
            return lPoint
        }
    override val screenHeight: Int
        get() = deviceDimension.y
    override val screenWidth: Int
        get() = deviceDimension.x

    override val navigationBarHeight: Int
        get() {
            val resourceId: Int =
                mContext.resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return if (resourceId > 0) {
                mContext.resources.getDimensionPixelSize(resourceId)
            } else 0
        }
}
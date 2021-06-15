package com.amar.library.provider

import android.content.Context
import android.graphics.Point
import com.amar.library.provider.interfaces.IScreenInfoProvider
import javax.annotation.ParametersAreNonnullByDefault


@ParametersAreNonnullByDefault
class ScreenInfoProvider(private val mContext: Context) : IScreenInfoProvider {
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
}
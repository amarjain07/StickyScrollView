package com.amar.library.ui.interfaces

interface IScrollViewListener {
    fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int){}
    fun onScrollStopped(isStopped: Boolean){}
}
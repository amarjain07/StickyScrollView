package com.amar.library.ui.presenter

import androidx.annotation.StyleableRes
import com.amar.library.provider.interfaces.IResourceProvider
import com.amar.library.provider.interfaces.IScreenInfoProvider
import com.amar.library.ui.presentation.IStickyScrollPresentation


class StickyScrollPresenter(
    stickyScrollPresentation: IStickyScrollPresentation,
    screenInfoProvider: IScreenInfoProvider,
    typedArrayResourceProvider: IResourceProvider
) {
    private val mTypedArrayResourceProvider: IResourceProvider = typedArrayResourceProvider
    private val mStickyScrollPresentation: IStickyScrollPresentation = stickyScrollPresentation
    private val mDeviceHeight: Int = screenInfoProvider.screenHeight
    private var mStickyFooterHeight = 0
    private var mStickyFooterInitialTranslation = 0
    private var mStickyFooterInitialLocation = 0
    private var mStickyHeaderInitialLocation = 0
    var isFooterSticky = false
        private set
    var isHeaderSticky = false
        private set
    var mScrolled = false
    fun onGlobalLayoutChange(@StyleableRes headerRes: Int, @StyleableRes footerRes: Int) {
        val headerId = mTypedArrayResourceProvider.getResourceId(headerRes)
        if (headerId != 0) {
            mStickyScrollPresentation.initHeaderView(headerId)
        }
        val footerId = mTypedArrayResourceProvider.getResourceId(footerRes)
        if (footerId != 0) {
            mStickyScrollPresentation.initFooterView(footerId)
        }
        mTypedArrayResourceProvider.recycle()
    }

    fun initStickyFooter(measuredHeight: Int?, initialStickyFooterLocation: Int) {
        measuredHeight?.let {
            mStickyFooterHeight = it
        }
        mStickyFooterInitialLocation = initialStickyFooterLocation
        mStickyFooterInitialTranslation =
            mDeviceHeight - initialStickyFooterLocation - mStickyFooterHeight
        if (mStickyFooterInitialLocation > mDeviceHeight - mStickyFooterHeight) {
            mStickyScrollPresentation.stickFooter(mStickyFooterInitialTranslation)
            isFooterSticky = true
        }
    }

    fun initStickyHeader(headerTop: Int?) {
        if (headerTop == null) {
            mStickyHeaderInitialLocation = 0
            return
        }
        mStickyHeaderInitialLocation = headerTop
    }

    fun onScroll(scrollY: Int) {
        mScrolled = true
        handleFooterStickiness(scrollY)
        handleHeaderStickiness(scrollY)
    }

    private fun handleFooterStickiness(scrollY: Int) {
        if (scrollY > mStickyFooterInitialLocation - mDeviceHeight + mStickyFooterHeight) {
            mStickyScrollPresentation.freeFooter()
            isFooterSticky = false
        } else {
            mStickyScrollPresentation.stickFooter(mStickyFooterInitialTranslation + scrollY)
            isFooterSticky = true
        }
    }

    private fun handleHeaderStickiness(scrollY: Int) {
        if (scrollY > mStickyHeaderInitialLocation) {
            mStickyScrollPresentation.stickHeader(scrollY - mStickyHeaderInitialLocation)
            isHeaderSticky = true
        } else {
            mStickyScrollPresentation.freeHeader()
            isHeaderSticky = false
        }
    }

    fun recomputeFooterLocation(footerTop: Int) {
        if (mScrolled) {
            mStickyFooterInitialTranslation = mDeviceHeight - footerTop - mStickyFooterHeight
            mStickyFooterInitialLocation = footerTop
        } else {
            initStickyFooter(mStickyFooterHeight, footerTop)
        }
        handleFooterStickiness(mStickyScrollPresentation.currentScrollYPos)
    }

    fun recomputeHeaderLocation(headerTop: Int) {
        initStickyHeader(headerTop)
        handleHeaderStickiness(mStickyScrollPresentation.currentScrollYPos)
    }

}
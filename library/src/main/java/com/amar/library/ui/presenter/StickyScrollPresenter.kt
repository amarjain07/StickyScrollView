package com.amar.library.ui.presenter

import androidx.annotation.StyleableRes
import com.amar.library.provider.interfaces.IResourceProvider
import com.amar.library.provider.interfaces.IScreenInfoProvider
import com.amar.library.ui.presentation.IStickyScrollPresentation

internal class StickyScrollPresenter(
    stickyScrollPresentation: IStickyScrollPresentation,
    private val screenInfoProvider: IScreenInfoProvider,
    typedArrayResourceProvider: IResourceProvider
) {
    private val mTypedArrayResourceProvider: IResourceProvider = typedArrayResourceProvider
    private val mStickyScrollPresentation: IStickyScrollPresentation = stickyScrollPresentation
    private val mDeviceHeight: Int = screenInfoProvider.screenHeight
    private var mStickyFooterHeight = 0
    private var mStickyFooterInitialTranslation = 0
    private var mStickyFooterInitialLocation = 0
    private var mStickyHeaderInitialLocation = 0
    private var navBarHeightDiff = 0
    var mNavigationBarInitialHeight = screenInfoProvider.navigationBarHeight
    var isFooterSticky = false
        private set
    var isHeaderSticky = false
        private set
    var mScrolled = false
    fun onGlobalLayoutChange(@StyleableRes headerRes: Int, @StyleableRes footerRes: Int) {
        val currentNavBarHeight = screenInfoProvider.navigationBarHeight
        navBarHeightDiff =  mNavigationBarInitialHeight - currentNavBarHeight
        mNavigationBarInitialHeight = currentNavBarHeight
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
        mStickyFooterInitialLocation = initialStickyFooterLocation - navBarHeightDiff
        mStickyFooterInitialTranslation =
            mDeviceHeight - mStickyFooterInitialLocation - mStickyFooterHeight
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
        isFooterSticky = if (scrollY > mStickyFooterInitialLocation - mDeviceHeight + mStickyFooterHeight) {
            mStickyScrollPresentation.freeFooter()
            false
        } else {
            mStickyScrollPresentation.stickFooter(mStickyFooterInitialTranslation + scrollY)
            true
        }
    }

    private fun handleHeaderStickiness(scrollY: Int) {
        isHeaderSticky = if (scrollY > mStickyHeaderInitialLocation) {
            mStickyScrollPresentation.stickHeader(scrollY - mStickyHeaderInitialLocation)
            true
        } else {
            mStickyScrollPresentation.freeHeader()
            false
        }
    }

    fun recomputeFooterLocation(footerTop: Int) {
        if (mScrolled) {
            mStickyFooterInitialLocation = footerTop - navBarHeightDiff
            mStickyFooterInitialTranslation = mDeviceHeight - mStickyFooterInitialLocation - mStickyFooterHeight
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
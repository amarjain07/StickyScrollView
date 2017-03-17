package com.amar.library.ui.presenter;

import android.support.annotation.StyleableRes;

import com.amar.library.provider.interfaces.IResourceProvider;
import com.amar.library.ui.presentation.IStickyScrollPresentation;
import com.amar.library.provider.interfaces.IScreenInfoProvider;

/**
 * Created by Amar Jain on 17/03/17.
 */

public class StickyScrollPresenter {

    private final IResourceProvider mTypedArrayResourceProvider;
    private IStickyScrollPresentation mStickyScrollPresentation;

    private int mDeviceHeight;

    private int mStickyFooterHeight;
    private int mStickyFooterInitialTranslation;
    private int[] mStickyFooterInitialLocation;

    private int mStickyHeaderInitialLocation;
    private boolean mIsFooterSticky;
    private boolean mIsHeaderSticky;

    public StickyScrollPresenter(IScreenInfoProvider screenInfoProvider, IResourceProvider typedArrayResourceProvider) {
        mDeviceHeight = screenInfoProvider.getScreenHeight();
        mTypedArrayResourceProvider = typedArrayResourceProvider;
    }

    public void onGlobalLayoutChange(IStickyScrollPresentation stickyScrollPresentation, @StyleableRes int headerRes, @StyleableRes int footerRes){
        mStickyScrollPresentation = stickyScrollPresentation;
        int headerId = mTypedArrayResourceProvider.getResourceId(headerRes);
        if(headerId != 0) {
            mStickyScrollPresentation.initHeaderView(headerId);
        }
        int footerId = mTypedArrayResourceProvider.getResourceId(footerRes);
        if(footerId != 0){
            mStickyScrollPresentation.initFooterView(footerId);
        }
        mTypedArrayResourceProvider.recycle();
    }

    public void initStickyFooter(int measuredHeight, int[] initialStickyFooterLocation) {
        mStickyFooterHeight = measuredHeight;
        mStickyFooterInitialLocation = initialStickyFooterLocation;

        if (mStickyFooterInitialLocation[1] > mDeviceHeight - mStickyFooterHeight) {
            mStickyFooterInitialTranslation = mDeviceHeight - initialStickyFooterLocation[1] - mStickyFooterHeight;
            mStickyScrollPresentation.stickFooter(mStickyFooterInitialTranslation);
            mIsFooterSticky = true;
        }
    }

    public void initStickyHeader(int headerTop){
        mStickyHeaderInitialLocation = headerTop;
    }

    public void onScroll(int scrollY){
        handleFooterStickiness(scrollY);
        handleHeaderStickiness(scrollY);
    }

    private void handleFooterStickiness(int scrollY) {
        if (scrollY > mStickyFooterInitialLocation[1] - mDeviceHeight + mStickyFooterHeight) {
            mStickyScrollPresentation.freeFooter();
            mIsFooterSticky = false;
        } else {
            mStickyScrollPresentation.stickFooter(mStickyFooterInitialTranslation + scrollY);
            mIsFooterSticky = true;
        }
    }

    private void handleHeaderStickiness(int scrollY) {
        if (scrollY > mStickyHeaderInitialLocation) {
            mStickyScrollPresentation.stickHeader(scrollY - mStickyHeaderInitialLocation);
            mIsHeaderSticky = true;
        } else {
            mStickyScrollPresentation.freeHeader();
            mIsHeaderSticky = false;
        }
    }

    public boolean isFooterSticky() {
        return mIsFooterSticky;
    }

    public boolean isHeaderSticky() {
        return mIsHeaderSticky;
    }
}

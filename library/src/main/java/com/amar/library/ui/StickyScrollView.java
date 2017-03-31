package com.amar.library.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.amar.library.R;
import com.amar.library.provider.ResourceProvider;
import com.amar.library.provider.interfaces.IResourceProvider;
import com.amar.library.ui.interfaces.IScrollViewListener;
import com.amar.library.ui.presentation.IStickyScrollPresentation;
import com.amar.library.ui.presenter.StickyScrollPresenter;
import com.amar.library.provider.ScreenInfoProvider;
import com.amar.library.provider.interfaces.IScreenInfoProvider;

public class StickyScrollView extends ScrollView implements IStickyScrollPresentation {
    private static final String TAG = StickyScrollView.class.getSimpleName();
    private IScrollViewListener scrollViewListener;

    private View stickyFooterView;
    private View stickyHeaderView;

    private static final String SCROLL_STATE = "scroll_state";
    private static final String SUPER_STATE = "super_state";


    private StickyScrollPresenter mStickyScrollPresenter;
    int[] updatedFooterLocation = new int[2];

    public StickyScrollView(Context context) {
        this(context, null);
    }

    public StickyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        IScreenInfoProvider screenInfoProvider = new ScreenInfoProvider(context);
        IResourceProvider resourceProvider = new ResourceProvider(context, attrs, R.styleable.StickyScrollView);
        mStickyScrollPresenter = new StickyScrollPresenter(this, screenInfoProvider, resourceProvider);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mStickyScrollPresenter.onGlobalLayoutChange(R.styleable.StickyScrollView_stickyHeader, R.styleable.StickyScrollView_stickyFooter);
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(stickyFooterView != null && !changed) {
            stickyFooterView.getLocationInWindow(updatedFooterLocation);
            mStickyScrollPresenter.recomputeFooterLocation(getRelativeTop(stickyFooterView), updatedFooterLocation[1]);
        }
    }

    private int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView()) {
            return myView.getTop();
        } else {
            return myView.getTop() + getRelativeTop((View) myView.getParent());
        }
    }

    @Override
    public void initHeaderView(int id) {
        stickyHeaderView = findViewById(id);
        mStickyScrollPresenter.initStickyHeader(stickyHeaderView.getTop());
    }

    @Override
    public void initFooterView(int id) {
        stickyFooterView = findViewById(id);
        mStickyScrollPresenter.initStickyFooter(stickyFooterView.getMeasuredHeight(), getRelativeTop(stickyFooterView));
    }

    @Override
    public void freeHeader() {
        if (stickyHeaderView != null) {
            stickyHeaderView.setTranslationY(0);
            PropertySetter.setTranslationZ(stickyHeaderView, 0);
        }
    }

    @Override
    public void freeFooter() {
        if (stickyFooterView != null) {
            stickyFooterView.setTranslationY(0);
        }
    }

    @Override
    public void stickHeader(int translationY) {
        if (stickyHeaderView != null) {
            stickyHeaderView.setTranslationY(translationY);
            PropertySetter.setTranslationZ(stickyHeaderView, 1);
        }
    }

    @Override
    public void stickFooter(int translationY) {
        if (stickyFooterView != null) {
            stickyFooterView.setTranslationY(translationY);
        }
    }

    @Override
    protected void onScrollChanged(int mScrollX, int mScrollY, int oldX, int oldY) {
        super.onScrollChanged(mScrollX, mScrollY, oldX, oldY);
        mStickyScrollPresenter.onScroll(mScrollY);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(mScrollX, mScrollY, oldX, oldY);
        }
    }

    public IScrollViewListener getScrollViewListener() {
        return scrollViewListener;
    }

    public void setScrollViewListener(IScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public boolean isFooterSticky() {
        return mStickyScrollPresenter.isFooterSticky();
    }

    public boolean isHeaderSticky() {
        return mStickyScrollPresenter.isHeaderSticky();
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollStopped(clampedY);
        }
    }

    @Override
    public Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SUPER_STATE, super.onSaveInstanceState());
        bundle.putBoolean(SCROLL_STATE, mStickyScrollPresenter.mScrolled);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state)
    {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mStickyScrollPresenter.mScrolled = bundle.getBoolean(SCROLL_STATE);
            state = bundle.getParcelable(SUPER_STATE);
        }
        super.onRestoreInstanceState(state);
    }

}
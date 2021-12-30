package com.amar.library.ui

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.widget.NestedScrollView
import com.amar.library.R
import com.amar.library.provider.ResourceProvider
import com.amar.library.provider.ScreenInfoProvider
import com.amar.library.provider.interfaces.IResourceProvider
import com.amar.library.provider.interfaces.IScreenInfoProvider
import com.amar.library.ui.interfaces.IScrollViewListener
import com.amar.library.ui.presentation.IStickyScrollPresentation
import com.amar.library.ui.presenter.StickyScrollPresenter

class StickyScrollView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : NestedScrollView(context, attributeSet, defStyleAttr), IStickyScrollPresentation {
    private var scrollViewListener: IScrollViewListener? = null
    private var stickyFooterView: View? = null
    private var stickyHeaderView: View? = null
    private var mStickyScrollPresenter: StickyScrollPresenter

    init {
        val screenInfoProvider: IScreenInfoProvider = ScreenInfoProvider(context)
        val resourceProvider: IResourceProvider =
            ResourceProvider(context, attributeSet, R.styleable.StickyScrollView)
        mStickyScrollPresenter = StickyScrollPresenter(this, screenInfoProvider, resourceProvider)
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mStickyScrollPresenter.onGlobalLayoutChange(
                    R.styleable.StickyScrollView_stickyHeader,
                    R.styleable.StickyScrollView_stickyFooter
                )
                viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        stickyFooterView?.let {
            mStickyScrollPresenter.recomputeFooterLocation(getRelativeTop(it))
        }
        stickyHeaderView?.let {
            mStickyScrollPresenter.recomputeHeaderLocation(it.top)
        }
    }

    private fun getRelativeTop(myView: View): Int {
        return if (myView.parent === myView.rootView) {
            myView.top
        } else {
            myView.top + getRelativeTop(myView.parent as View)
        }
    }

    override fun initHeaderView(id: Int) {
        stickyHeaderView = findViewById(id)
        mStickyScrollPresenter.initStickyHeader(stickyHeaderView?.top)
    }

    override fun initFooterView(id: Int) {
        stickyFooterView = findViewById(id)
        stickyFooterView?.let {
            mStickyScrollPresenter.initStickyFooter(it.measuredHeight, getRelativeTop(it))
        }
    }

    override fun freeHeader() {
        stickyHeaderView?.let {
            it.translationY = 0f
            PropertySetter.setTranslationZ(it, 0f)
        }
    }

    override fun freeFooter() {
        stickyFooterView?.translationY = 0f
    }

    override fun stickHeader(translationY: Int) {
        stickyHeaderView?.let {
            it.translationY = translationY.toFloat()
            PropertySetter.setTranslationZ(it, 1f)
        }
    }

    override fun stickFooter(translationY: Int) {
        stickyFooterView?.translationY = translationY.toFloat()
    }

    override val currentScrollYPos: Int
        get() = scrollY

    override fun onScrollChanged(mScrollX: Int, mScrollY: Int, oldX: Int, oldY: Int) {
        super.onScrollChanged(mScrollX, mScrollY, oldX, oldY)
        mStickyScrollPresenter.onScroll(mScrollY)
        scrollViewListener?.onScrollChanged(mScrollX, mScrollY, oldX, oldY)
    }

    val isFooterSticky: Boolean
        get() = mStickyScrollPresenter.isFooterSticky
    val isHeaderSticky: Boolean
        get() = mStickyScrollPresenter.isHeaderSticky

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        scrollViewListener?.onScrollStopped(clampedY)
    }

    public override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState())
        bundle.putBoolean(STATE_SCROLL, mStickyScrollPresenter.mScrolled)
        bundle.putInt(STATE_NAV_BAR_HEIGHT, mStickyScrollPresenter.mNavigationBarInitialHeight)
        return bundle
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {
            mStickyScrollPresenter.mNavigationBarInitialHeight = state.getInt(STATE_NAV_BAR_HEIGHT)
            mStickyScrollPresenter.mScrolled = state.getBoolean(STATE_SCROLL)
            super.onRestoreInstanceState(state.getParcelable(STATE_SUPER))
            return
        }
        super.onRestoreInstanceState(state)
    }

    companion object {
        private const val STATE_SCROLL = "scroll_state"
        private const val STATE_SUPER = "super_state"
        private const val STATE_NAV_BAR_HEIGHT = "nav_bar_height_state"
    }
}
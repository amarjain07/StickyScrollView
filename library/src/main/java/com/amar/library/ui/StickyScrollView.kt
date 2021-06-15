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

class StickyScrollView : NestedScrollView, IStickyScrollPresentation {

    private var scrollViewListener: IScrollViewListener? = null
    private var stickyFooterView: View? = null
    private var stickyHeaderView: View? = null
    private lateinit var mStickyScrollPresenter: StickyScrollPresenter


    constructor(context: Context) : this(context, null) {
        initLayout()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        val screenInfoProvider: IScreenInfoProvider = ScreenInfoProvider(context)
        val resourceProvider: IResourceProvider =
            ResourceProvider(context, attrs, R.styleable.StickyScrollView)
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

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initLayout()
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (stickyFooterView != null && !changed) {
            mStickyScrollPresenter.recomputeFooterLocation(getRelativeTop(stickyFooterView))
        }
        if (stickyHeaderView != null) {
            mStickyScrollPresenter.recomputeHeaderLocation(stickyHeaderView!!.top)
        }
    }

    private fun getRelativeTop(myView: View?): Int {
        return if (myView!!.parent === myView!!.rootView) {
            myView!!.top
        } else {
            myView!!.top + getRelativeTop(myView.parent as View)
        }
    }

    override fun initHeaderView(id: Int) {
        stickyHeaderView = findViewById(id)
        mStickyScrollPresenter.initStickyHeader(stickyHeaderView?.top)
    }

    override fun initFooterView(id: Int) {
        stickyFooterView = findViewById(id)
        mStickyScrollPresenter.initStickyFooter(
            stickyFooterView?.measuredHeight,
            getRelativeTop(stickyFooterView)
        )
    }

    override fun freeHeader() {
        if (stickyHeaderView != null) {
            stickyHeaderView!!.translationY = 0f
            PropertySetter.setTranslationZ(stickyHeaderView, 0f)
        }
    }

    override fun freeFooter() {
        if (stickyFooterView != null) {
            stickyFooterView!!.translationY = 0f
        }
    }

    override fun stickHeader(translationY: Int) {
        if (stickyHeaderView != null) {
            stickyHeaderView!!.translationY = translationY.toFloat()
            PropertySetter.setTranslationZ(stickyHeaderView, 1f)
        }
    }

    override fun stickFooter(translationY: Int) {
        if (stickyFooterView != null) {
            stickyFooterView!!.translationY = translationY.toFloat()
        }
    }

    override val currentScrollYPos: Int
        get() = scrollY

    override fun onScrollChanged(mScrollX: Int, mScrollY: Int, oldX: Int, oldY: Int) {
        super.onScrollChanged(mScrollX, mScrollY, oldX, oldY)
        mStickyScrollPresenter.onScroll(mScrollY)
        if (scrollViewListener != null) {
            scrollViewListener!!.onScrollChanged(mScrollX, mScrollY, oldX, oldY)
        }
    }

    val isFooterSticky: Boolean
        get() = mStickyScrollPresenter.isFooterSticky
    val isHeaderSticky: Boolean
        get() = mStickyScrollPresenter.isHeaderSticky

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        if (scrollViewListener != null) {
            scrollViewListener!!.onScrollStopped(clampedY)
        }
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable(SUPER_STATE, super.onSaveInstanceState())
        bundle.putBoolean(SCROLL_STATE, mStickyScrollPresenter.mScrolled)
        return bundle
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        var state: Parcelable? = state
        if (state is Bundle) {
            val bundle = state
            mStickyScrollPresenter.mScrolled = bundle.getBoolean(SCROLL_STATE)
            state = bundle.getParcelable(SUPER_STATE)
        }
        super.onRestoreInstanceState(state)
    }

    companion object {
        private const val SCROLL_STATE = "scroll_state"
        private const val SUPER_STATE = "super_state"
    }

    fun initLayout() {

    }
}
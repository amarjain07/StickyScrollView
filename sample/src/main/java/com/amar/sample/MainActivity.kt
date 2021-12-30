package com.amar.sample

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amar.library.ui.StickyScrollView
import com.amar.library.ui.interfaces.IScrollViewListener

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var scrollView: StickyScrollView? = null
    private var mainShoeView: ImageView? = null
    private var redShoeVisible = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scrollView = findViewById<View>(R.id.scrollView) as StickyScrollView
        val titleHeader = findViewById<View>(R.id.title)
        scrollView?.setScrollViewListener(object: IScrollViewListener{
            override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
                if(scrollView?.isHeaderSticky == true) {
                    titleHeader.setBackgroundResource(android.R.color.white)
                } else {
                    titleHeader.setBackgroundResource(android.R.color.transparent)
                }
            }
        })
        findViewById<View>(R.id.buy).setOnClickListener(this)
        findViewById<View>(R.id.save).setOnClickListener(this)
        findViewById<View>(R.id.title).setOnClickListener(this)
        findViewById<View>(R.id.other_product).setOnClickListener(this)
        mainShoeView = findViewById(R.id.main_shoe_picture)
        scrollView?.setFooterView(R.id.buttons)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buy -> {
                startActivity(Intent(this, ViewPagerActivity::class.java))
            }
            R.id.save -> Toast.makeText(
                this,
                if (scrollView!!.isFooterSticky) "Footer is Sticky" else "Footer is not sticky",
                Toast.LENGTH_SHORT
            ).show()
            R.id.title -> Toast.makeText(
                this,
                if (scrollView!!.isHeaderSticky) "Header is Sticky" else "Header is not sticky",
                Toast.LENGTH_SHORT
            ).show()
            R.id.other_product -> switchShoeViews()
        }
    }

    private fun switchShoeViews() {
        if (redShoeVisible) {
            redShoeVisible = false
            mainShoeView!!.setImageResource(R.drawable.similar_1)
            val params = mainShoeView!!.layoutParams as LinearLayout.LayoutParams
            params.height = dpToPixel(420).toInt()
            mainShoeView!!.layoutParams = params
        } else {
            redShoeVisible = true
            mainShoeView!!.setImageResource(R.drawable.nike)
            val params = mainShoeView!!.layoutParams as LinearLayout.LayoutParams
            params.height = dpToPixel(520).toInt()
            mainShoeView!!.layoutParams = params
        }
    }

    private fun dpToPixel(pixel: Int): Float {
        val r = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            pixel.toFloat(),
            r.displayMetrics
        )
    }
}
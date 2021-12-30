package com.amar.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amar.library.ui.StickyScrollView

class PageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.activity_main, container, false)
        view.findViewById<StickyScrollView>(R.id.scrollView).setFooterView(R.id.buttons)
        return view
    }
}
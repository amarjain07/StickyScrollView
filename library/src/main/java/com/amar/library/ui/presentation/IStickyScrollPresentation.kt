package com.amar.library.ui.presentation

interface IStickyScrollPresentation {
    val currentScrollYPos: Int
    fun freeHeader()
    fun freeFooter()
    fun stickHeader(translationY: Int)
    fun stickFooter(translationY: Int)
    fun initHeaderView(id: Int)
    fun initFooterView(id: Int)
}
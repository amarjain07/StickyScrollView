package com.amar.library.ui.presentation

internal interface IStickyScrollPresentation {
    fun freeHeader()
    fun freeFooter()
    fun stickHeader(translationY: Int)
    fun stickFooter(translationY: Int)
    fun initHeaderView(id: Int)
    fun initFooterView(id: Int)
    val currentScrollYPos: Int
}
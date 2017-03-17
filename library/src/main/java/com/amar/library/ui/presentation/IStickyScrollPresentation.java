package com.amar.library.ui.presentation;

/**
 * Created by Amar Jain on 17/03/17.
 */

public interface IStickyScrollPresentation {
    void freeHeader();
    void freeFooter();
    void stickHeader(int translationY);
    void stickFooter(int translationY);

    void initHeaderView(int id);
    void initFooterView(int id);

}

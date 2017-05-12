package de.uni_muenster.wi.md2library.controller.eventhandler.implementation;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import de.uni_muenster.wi.md2library.view.widgets.implementation.Md2Button;

/**
 * Abstract super class for swipe event handlers.
 * Extended by Md2OnLeftSwipeHandler and Md2OnRightSwipeHandler
 * <p/>
 * Created on 11/08/2015
 *
 * @author Fabian Wrede
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractMd2OnSwipeHandler extends AbstractMd2WidgetEventHandler implements View.OnTouchListener {

    private float xtouch;
    private float xmove;
    private boolean rightSwipe = false;
    private boolean leftSwipe = false;


    /**
     * Instantiates a new Abstract md 2 on swipe handler.
     *
     */
    public AbstractMd2OnSwipeHandler() {
        super();
    }

    /**
     * On swipe right boolean.
     *
     * @return the boolean
     */
// these must be overwritten by concrete onLeftSwipe and onRightSwipe Handlers
    public abstract boolean onSwipeRight();

    /**
     * On swipe left boolean.
     *
     * @return the boolean
     */
    public abstract boolean onSwipeLeft();

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
             //first Touch
            case MotionEvent.ACTION_DOWN: {
                //disable scroll
                view.getParent().requestDisallowInterceptTouchEvent(true);
                //set xtouch and reset xmove
                xtouch = event.getX();
                xmove = xtouch;
                break;
            }

            //release
            case MotionEvent.ACTION_UP: {
                //decide if swiped
                float width = view.getWidth();
                float abs = Math.abs(xmove - xtouch);
                if (abs > (width / 2.5)) {
                    if (xmove > xtouch) {
                        rightSwipe = true;
                    } else if (xmove < xtouch) {
                        leftSwipe = true;
                    }
                }

                //perform action on swipe
                if (leftSwipe) {
                    leftSwipe = false;
                    return onSwipeLeft();
                }
                if (rightSwipe) {
                    rightSwipe = false;
                    return onSwipeRight();
                } else {
                    // den Klick an den OnClickHandler weiterleiten
                    if (view instanceof Md2Button){
                        Md2Button b = (Md2Button) view;
                        b.getOnClickHandler().execute();
                    }
                }
                break;
            }

            //swipe: change xmove
            case MotionEvent.ACTION_MOVE: {
                xmove = event.getX();
                break;
            }
        }
        return true;
    }

}




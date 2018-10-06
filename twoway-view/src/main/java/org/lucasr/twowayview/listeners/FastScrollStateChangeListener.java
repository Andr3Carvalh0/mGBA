package org.lucasr.twowayview.listeners;

import org.lucasr.twowayview.decorator.FastScroller;

/**
 * Created by André Carvalho on 13/08/2018
 */
public interface FastScrollStateChangeListener {
    /**
     * Called when fast scrolling begins
     */
    void onFastScrollStart(FastScroller fastScroller);

    /**
     * Called when fast scrolling ends
     */
    void onFastScrollStop(FastScroller fastScroller);
}

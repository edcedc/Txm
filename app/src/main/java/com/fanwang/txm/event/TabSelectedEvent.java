package com.fanwang.txm.event;

/**
 * Created by YoKeyword on 16/6/5.
 */
public class TabSelectedEvent {

    public boolean isTab = false;

    public TabSelectedEvent(boolean isTab) {
        this.isTab = isTab;
    }

    public int position;

    public TabSelectedEvent(int position) {
        this.position = position;
    }
}

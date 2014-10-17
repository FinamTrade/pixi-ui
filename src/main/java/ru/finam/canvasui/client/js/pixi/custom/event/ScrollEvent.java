package ru.finam.canvasui.client.js.pixi.custom.event;

import com.google.web.bindery.event.shared.Event;

/**
 * Created by ikusch on 16.10.2014.
 */
public class ScrollEvent extends Event {

    private double newScrollPosition;

    public ScrollEvent(double newScrollPosition) {
        this.newScrollPosition = newScrollPosition;
    }

    @Override
    public Type getAssociatedType() {
        return new Type();
    }

    @Override
    protected void dispatch(Object o) {
    }

    public double getNewScrollPosition() {
        return newScrollPosition;
    }

}

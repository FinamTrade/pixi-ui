package ru.finam.canvasui.client.js.pixi.custom.event;

import com.google.web.bindery.event.shared.Event;

/**
 * Created by ikusch on 28.08.2014.
 */
public class ComponentUpdateEvent extends Event {

    @Override
    public Type getAssociatedType() {
        return new Type();
    }

    @Override
    protected void dispatch(Object o) {
    }

}

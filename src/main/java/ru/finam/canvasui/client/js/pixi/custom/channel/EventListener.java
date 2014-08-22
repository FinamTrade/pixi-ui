package ru.finam.canvasui.client.js.pixi.custom.channel;

import com.google.gwt.event.dom.client.DomEvent;

/**
 * Created by ikusch on 21.08.14.
 */
public interface EventListener<T extends DomEvent> {

    void onEvent(T event);

}

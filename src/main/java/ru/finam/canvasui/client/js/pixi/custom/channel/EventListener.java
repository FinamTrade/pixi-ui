package ru.finam.canvasui.client.js.pixi.custom.channel;


import com.google.web.bindery.event.shared.Event;

/**
 * Created by ikusch on 21.08.14.
 */
public interface EventListener<T extends Event> {

    void onEvent(T event);

}

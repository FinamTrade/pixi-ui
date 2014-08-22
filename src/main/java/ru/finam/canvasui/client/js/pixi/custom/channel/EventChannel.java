package ru.finam.canvasui.client.js.pixi.custom.channel;

import com.google.gwt.event.dom.client.DomEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Created by ikusch on 21.08.14.
 */
public class EventChannel<T extends DomEvent> {


    //private Set<EventListener<T>> listeners = Collections.newSetFromMap(new WeakHashMap<EventListener<T>, Boolean>());
    private Set<EventListener<T>> listeners = new HashSet<>();

    protected EventChannel() {}

    public void fire(T event) {
        for (EventListener listener:listeners) {
            listener.onEvent(event);
        }
    }

    public void addListener(EventListener<T> listener) {
        listeners.add(listener);
    }

    public void removeListener(EventListener<T> listener) {
        listeners.remove(listener);
    }

    public void clearListeners() {
        listeners.clear();
    }

}
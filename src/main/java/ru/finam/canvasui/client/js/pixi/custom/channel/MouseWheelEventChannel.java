package ru.finam.canvasui.client.js.pixi.custom.channel;

import com.google.gwt.event.dom.client.MouseWheelEvent;

/**
 * Created by ikusch on 21.08.14.
 */
public class MouseWheelEventChannel extends EventChannel<MouseWheelEvent> {

    private static MouseWheelEventChannel instance;

    protected MouseWheelEventChannel() {}

    public static void fireNew(MouseWheelEvent event) {
        getInstance().fire(event);
    }

    public static void addNewListener(EventListener<MouseWheelEvent> event) {
        getInstance().addListener(event);
    }

    public static synchronized MouseWheelEventChannel getInstance() {
        if (instance == null) {
            instance = new MouseWheelEventChannel();
        }
        return instance;
    }

}
package ru.finam.canvasui.client.js.pixi.custom.channel;

import ru.finam.canvasui.client.js.pixi.custom.event.ScrollEvent;

/**
 * Created by ikusch on 16.10.2014.
 */
public class ScrollEventChannel extends EventChannel<ScrollEvent> {

    private static ScrollEventChannel instance;

    protected ScrollEventChannel() {}

    public static void fireNew(ScrollEvent event) {
        getInstance().fire(event);
    }

    public static void addNewListener(EventListener<ScrollEvent> event) {
        getInstance().addListener(event);
    }

    public static synchronized ScrollEventChannel getInstance() {
        if (instance == null) {
            instance = new ScrollEventChannel();
        }
        return instance;
    }

}

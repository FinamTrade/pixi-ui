package ru.finam.canvasui.client.js.pixi.custom.channel;

import ru.finam.canvasui.client.js.pixi.custom.event.ComponentUpdateEvent;

/**
 * Created by ikusch on 28.08.2014.
 */
public class ComponentUpdateEventChannel extends EventChannel<ComponentUpdateEvent> {

    private static ComponentUpdateEventChannel instance;

    protected ComponentUpdateEventChannel() {}

    public static synchronized ComponentUpdateEventChannel newInstance() {
        return instance = new ComponentUpdateEventChannel();
    }

}

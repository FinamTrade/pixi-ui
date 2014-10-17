package ru.finam.canvasui.client.js.pixi.custom.panel;

import ru.finam.canvasui.client.js.pixi.custom.channel.ComponentUpdateEventChannel;

/**
 * Created by ikusch on 28.08.2014.
 *
 * Component, which fires update event after self state change.
 *
 */
public interface UpdatableComponent {

    ComponentUpdateEventChannel componentUpdateEventChannel();

}

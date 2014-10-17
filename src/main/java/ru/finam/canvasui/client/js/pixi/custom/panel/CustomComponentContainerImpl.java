package ru.finam.canvasui.client.js.pixi.custom.panel;

import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;

/**
 * Created by ikusch on 29.09.2014.
 */
public class CustomComponentContainerImpl extends BaseCustomComponentContainerImpl<DisplayObjectContainer> implements CustomComponentContainer {

    public CustomComponentContainerImpl(DisplayObjectContainer mainComponent) {
        super(mainComponent);
    }

    protected CustomComponentContainerImpl() {
        this(DisplayObjectContainer.Factory.newInstance());
    }

}

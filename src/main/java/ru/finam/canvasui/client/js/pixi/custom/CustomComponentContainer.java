package ru.finam.canvasui.client.js.pixi.custom;

import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;

/**
 * Created by ikusch on 29.09.2014.
 */
public class CustomComponentContainer extends BaseCustomComponentContainer<DisplayObjectContainer> {

    protected CustomComponentContainer(DisplayObjectContainer mainComponent) {
        super(mainComponent);
    }

    protected CustomComponentContainer() {
        this(DisplayObjectContainer.Factory.newInstance());
    }

}

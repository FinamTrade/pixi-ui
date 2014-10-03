package ru.finam.canvasui.client.js.pixi.custom;

import ru.finam.canvasui.client.js.pixi.DisplayObject;

/**
 * Created by ikusch on 28.08.2014.
 */
public class SimplePixiPanel extends CustomComponentContainer {

    public SimplePixiPanel() {
    }

    public SimplePixiPanel(DisplayObject content) {
        super();
        addChild(content);
    }

}

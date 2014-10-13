package ru.finam.canvasui.client.js.pixi.custom.panel;

import ru.finam.canvasui.client.js.pixi.DisplayObject;
import ru.finam.canvasui.client.js.pixi.custom.panel.CustomComponentContainer;

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

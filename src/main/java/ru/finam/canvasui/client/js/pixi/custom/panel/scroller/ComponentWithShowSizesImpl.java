package ru.finam.canvasui.client.js.pixi.custom.panel.scroller;

import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.js.pixi.custom.panel.SimplePixiPanel;

/**
 * Created by ikusch on 14.10.2014.
 */
public class ComponentWithShowSizesImpl extends SimplePixiPanel implements ComponentWithShowSizes<DisplayObjectContainer> {

    private DisplayObjectContainer content;

    public ComponentWithShowSizesImpl(DisplayObjectContainer content) {
        super(content);
        this.content = content;
    }

    @Override
    public double showWidth() {
        return content.getWidth();
    }

    @Override
    public double showHeight() {
        return content.getHeight();
    }
}

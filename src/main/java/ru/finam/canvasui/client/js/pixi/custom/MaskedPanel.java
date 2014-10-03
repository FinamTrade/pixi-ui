package ru.finam.canvasui.client.js.pixi.custom;

import ru.finam.canvasui.client.js.pixi.Graphics;
import ru.finam.canvasui.client.js.pixi.Rectangle;

/**
 * Created by ikusch on 26.09.2014.
 */
public class MaskedPanel extends CustomComponentContainer {

    public MaskedPanel(Rectangle rectangle) {
        this(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public MaskedPanel(double visibleAreaX, double visibleAreaY, double visibleAreaWidth, double visibleAreaHeight) {
        Graphics mask = Graphics.Factory.newInstance();
        mask.beginFill(1, 0);
        mask.drawRect(visibleAreaX, visibleAreaY, visibleAreaWidth, visibleAreaHeight);
        mask.endFill();
        addChild(mask);
        setMask(mask);
    }

}
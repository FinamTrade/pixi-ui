package ru.finam.canvasui.client.js.pixi.custom.panel.masked;

import com.google.gwt.core.client.GWT;
import ru.finam.canvasui.client.js.pixi.Graphics;
import ru.finam.canvasui.client.js.pixi.Rectangle;
/**
 * Created by ikusch on 10.10.2014.
 */
public class MaskObject {

    private Rectangle maskBounds;
    private Graphics graphics;

    public MaskObject(double visibleAreaX, double visibleAreaY, double visibleAreaWidth, double visibleAreaHeight) {
        this(Rectangle.Factory.newInstance(visibleAreaX, visibleAreaY, visibleAreaWidth, visibleAreaHeight));
    }

    public MaskObject(Rectangle bounds) {
        graphics = Graphics.Factory.newInstance();
        maskBounds = Rectangle.Factory.newInstance(0, 0, 0, 0);
        drawMask(bounds);
    }

    public void drawMask(Rectangle bounds) {
        this.maskBounds.setX(bounds.getX());
        this.maskBounds.setY(bounds.getY());
        this.maskBounds.setWidth(bounds.getWidth());
        this.maskBounds.setHeight(bounds.getHeight());
        mainComponent().clear();
        mainComponent().beginFill(1, 0);
        mainComponent().drawRect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        mainComponent().endFill();
    }

    public void drawMask(double visibleAreaX, double visibleAreaY, double visibleAreaWidth, double visibleAreaHeight) {
        drawMask(Rectangle.Factory.newInstance(visibleAreaX, visibleAreaY, visibleAreaWidth, visibleAreaHeight));
    }

    public Rectangle getMaskBounds() {
        return maskBounds;
    }

    public Graphics mainComponent() {
        return graphics;
    }
}

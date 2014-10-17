package ru.finam.canvasui.client.js.pixi.custom.panel.masked;

import ru.finam.canvasui.client.js.pixi.PointFactory;
import ru.finam.canvasui.client.js.pixi.Rectangle;
import ru.finam.canvasui.client.js.pixi.custom.panel.CustomComponentContainerImpl;

/**
 * Created by ikusch on 26.09.2014.
 */
public class MaskedPanel extends CustomComponentContainerImpl implements HasMask {

    private MaskObject maskObject;
    private double visibleAreaX;
    private double visibleAreaY;
    private double visibleAreaWidth;
    private double visibleAreaHeight;

    public MaskedPanel(Rectangle rectangle) {
        this(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public MaskedPanel(double visibleAreaX, double visibleAreaY, double visibleAreaWidth, double visibleAreaHeight) {
        this.visibleAreaX = visibleAreaX;
        this.visibleAreaY = visibleAreaY;
        this.visibleAreaWidth = visibleAreaWidth;
        this.visibleAreaHeight = visibleAreaHeight;
        maskObject = new MaskObject(visibleAreaX, visibleAreaY, visibleAreaWidth, visibleAreaHeight);
        addChild(maskObject.mainComponent());
        maskObject.mainComponent().setPosition(PointFactory.newInstance(0, 0));
        setMask(maskObject.mainComponent());
    }

    public void updateMaskHeight(double height) {
        maskObject.drawMask(visibleAreaX, visibleAreaY, visibleAreaWidth, height);
    }

    public void updateMaskWidth(double width) {
        maskObject.drawMask(visibleAreaX, visibleAreaY, width, visibleAreaHeight);
    }

    public void updateMaskX(double x) {
        maskObject.drawMask(x, visibleAreaY, visibleAreaWidth, visibleAreaHeight);
    }

    public void updateMaskY(double y) {
        maskObject.drawMask(visibleAreaX, y, visibleAreaWidth, visibleAreaHeight);
    }

}
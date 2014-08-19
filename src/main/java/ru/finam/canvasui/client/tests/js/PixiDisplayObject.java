package ru.finam.canvasui.client.tests.js;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

/**
 * Created by ikusch on 15.08.14.
 */
@JsType(prototype = "PIXI.DisplayObject")
public interface PixiDisplayObject extends JsObject {

    PixiRectangle getBounds();

    PixiRectangle getLocalBounds();

    @JsProperty
    void setMask(PixiGraphics m);

    @JsProperty
    PixiGraphics getMask();

    /*

    @JsProperty
    void setPosition(Point p);
    */

    /*
    @JsProperty
    void setInteractive(boolean b);

    @JsProperty
    void setButtonMode(boolean b);

    @JsProperty
    void setAlpha(double a);

    @JsProperty
    void setHitArea(PixiRectangle rect);

    @JsProperty
    void setPosition(double x, double y);
    */

    /*
    public final double getBoundedWidth() {
        return getBounds() == null ? 0 : ( getBounds().getWidth() + 2 * getBounds().getX() );
    }

    public final double getBoundedHeight() {
        return getBounds() == null ? 0 : ( getBounds().getHeight() + 2 * getBounds().getY() );
    }
    */

}

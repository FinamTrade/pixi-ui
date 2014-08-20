package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

/**
 * Created by ikusch on 19.08.14.
 */
@JsType(prototype = "$wnd.PIXI.DisplayObject")
public interface DisplayObject extends JsObject {

    Rectangle getBounds();

    Rectangle getLocalBounds();

    @JsProperty(value = "mask")
    void setMask(Graphics m);

    @JsProperty(value = "mask")
    Graphics getMask();

    @JsProperty(value = "position")
    Point getPosition();

    @JsProperty(value = "position")
    void setPosition(Point p);

    @JsProperty(value = "interactive")
    void setInteractive(boolean b);

    @JsProperty(value = "interactive")
    boolean getInteractive();

    @JsProperty(value = "buttonMode")
    void setButtonMode(boolean b);

    @JsProperty(value = "buttonMode")
    boolean getButtonMode();

    @JsProperty(value = "alpha")
    void setAlpha(double a);

    @JsProperty(value = "alpha")
    double getAlpha();

    @JsProperty(value = "hitArea")
    void setHitArea(Rectangle rect);

    @JsProperty(value = "hitArea")
    Rectangle getHitArea();

    @JsProperty(value = "updateFunction")
    JsObject getUpdateFunction();

    @JsProperty(value = "targetAlpha")
    void setTargetAlpha(double d);

    @JsProperty(value = "targetAlpha")
    double getTargetAlpha();

    /*
    @JsProperty(value = "mouseover")
    void setMouseover(MouseEventListener t);
    */

}

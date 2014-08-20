package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

/**
 * Created by ikusch on 19.08.14.
 */
@JsType(prototype = "$wnd.PIXI.Rectangle")
public interface Rectangle extends JsObject {

    @JsProperty(value = "x")
    double getX();

    @JsProperty(value = "x")
    void setX(double x);

    @JsProperty(value = "y")
    double getY();

    @JsProperty(value = "y")
    void setY(double y);

    @JsProperty(value = "width")
    double getWidth();

    @JsProperty(value = "width")
    void setWidth(double width);

    @JsProperty(value = "height")
    double getHeight();

    @JsProperty(value = "height")
    void setHeight(double height);

}

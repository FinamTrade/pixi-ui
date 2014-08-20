package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

/**
 * Created by ikusch on 19.08.14.
 */
@JsType(prototype = "$wnd.PIXI.Point")
public interface Point {

    @JsProperty(value = "x")
    double getX();

    @JsProperty(value = "x")
    void setX(double x);

    @JsProperty(value = "y")
    double getY();

    @JsProperty(value = "y")
    void setY(double y);
}

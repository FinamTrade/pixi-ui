package ru.finam.canvasui.client.tests.js;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

/**
 * Created by ikusch on 18.08.14.
 */
@JsType(prototype = "PIXI.Rectangle")
public interface PixiRectangle {

    @JsProperty
    void setX(double x);

    @JsProperty
    double getX();

}

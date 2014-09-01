package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.JsObject;

/**
 * Created by ikusch on 19.08.14.
 */
@JsType(prototype = "$wnd.PIXI.Rectangle")
public interface Rectangle extends JsObject {

    public static class Factory {

        public static final native Rectangle newInstance(double x, double y, int width, int height) /*-{
            return new $wnd.PIXI.Rectangle(x, y, width, height);
        }-*/;

    }

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

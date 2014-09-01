package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.Array;

/**
 * Created by ikusch on 19.08.14.
 */
@JsType(prototype = "$wnd.PIXI.DisplayObjectContainer")
public interface DisplayObjectContainer extends DisplayObject {

    public static class Factory {

        public static native DisplayObjectContainer newInstance() /*-{
            return new $wnd.PIXI.DisplayObjectContainer();
        }-*/;

    }

    void addChild(DisplayObject child);

    @JsProperty(value = "children")
    Array<DisplayObject> getChildren();

    @JsProperty(value = "children")
    void setChildren(Array<DisplayObject> children);

    @JsProperty(value = "width")
    double getWidth();

    @JsProperty(value = "width")
    void setWidth(double width);

    @JsProperty(value = "height")
    double getHeight();

    @JsProperty(value = "height")
    void setHeight(double height);

}

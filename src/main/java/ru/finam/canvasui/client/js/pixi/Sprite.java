package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

/**
 * Created by ikusch on 19.08.14.
 */
@JsType(isNative = true, prototype = "$wnd.PIXI.Sprite")
public interface Sprite extends DisplayObjectContainer {

    @JsProperty(value = "anchor")
    void setAnchor(Point point);

}

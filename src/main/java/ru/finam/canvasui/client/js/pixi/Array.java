package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

/**
 * Created by ikusch on 19.08.14.
 */
@JsType(isNative = true, prototype = "Array")
public interface Array<T extends JsObject> extends JsObject{

    void push(T data);

    T pop();

    @JsProperty(value = "length")
    int getLength();

}

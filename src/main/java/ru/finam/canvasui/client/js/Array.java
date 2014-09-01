package ru.finam.canvasui.client.js;

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

    public static class Factory {

        public static final native Array<JsObject> newArray() /*-{
            return [];
        }-*/;

    }

    public static class Static {

        public static final native <T extends JsObject> T get(int i, Array<T> array) /*-{
            array[i];
        }-*/;

    }

}

package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.JsArray;

/**
 * Created by ikusch on 19.08.14.
 */
public class ArrayFactory {

    public static final native Array<JsObject> newArray() /*-{
        return [];
    }-*/;

}

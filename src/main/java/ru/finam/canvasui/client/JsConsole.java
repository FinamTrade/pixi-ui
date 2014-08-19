package ru.finam.canvasui.client;

import com.google.gwt.core.client.JavaScriptObject;
import ru.finam.canvasui.client.pixi.tests.PixiScrollerTest5;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 10.08.14
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class JsConsole {

    public static final native void log(String message) /*-{
        try {
            console.log(message);
        } catch (e) {
        }
    }-*/;

    public static native void stringifyLog(String s, Object t) /*-{
        console.log(s + ' ' + t.width);
    }-*/;
}

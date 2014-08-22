package ru.finam.canvasui.client;

import ru.finam.canvasui.client.js.pixi.JsObject;

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

    public static native void stringifyLog(String s, JsObject t) /*-{
        console.log(s + ' ' + JSON.stringify(t));
    }-*/;
}

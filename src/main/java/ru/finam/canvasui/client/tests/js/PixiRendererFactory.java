package ru.finam.canvasui.client.tests.js;

/**
 * Created by ikusch on 18.08.14.
 */
public class PixiRendererFactory {

    public static native PixiRenderer autoDetectRenderer(double width, double height) /*-{
        return new $wnd.PIXI.autoDetectRenderer(width, height);
    }-*/;

}
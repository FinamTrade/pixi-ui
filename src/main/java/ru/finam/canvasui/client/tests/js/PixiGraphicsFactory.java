package ru.finam.canvasui.client.tests.js;

/**
 * Created by ikusch on 15.08.14.
 */
public abstract class PixiGraphicsFactory implements PixiGraphics {

    public static native PixiGraphics newInstance() /*-{
        var g = new $wnd.Graphics();
        return g;
    }-*/;

}
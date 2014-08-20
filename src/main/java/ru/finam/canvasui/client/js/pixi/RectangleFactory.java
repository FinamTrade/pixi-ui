package ru.finam.canvasui.client.js.pixi;

/**
 * Created by ikusch on 19.08.14.
 */
public class RectangleFactory {

    public static final native Rectangle newInstance(double x, double y, int width, int height) /*-{
        return new $wnd.PIXI.Rectangle(x, y, width, height);
    }-*/;

}

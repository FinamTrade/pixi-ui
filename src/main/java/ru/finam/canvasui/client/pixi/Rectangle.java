package ru.finam.canvasui.client.pixi;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 10.08.14
 * Time: 21:14
 * To change this template use File | Settings | File Templates.
 */
public class Rectangle extends JavaScriptObject {

    protected Rectangle() {}

    public static final native Rectangle newInstance(double x, double y, int width, int height) /*-{
        return new $wnd.PIXI.Rectangle(x, y, width, height);
    }-*/;

    public final native double getX() /*-{
        return this.x;
    }-*/;

    public final native double getY() /*-{
        return this.y;
    }-*/;

    public final native double getWidth() /*-{
        return this.width;
    }-*/;

    public final native double getHeight() /*-{
        return this.height;
    }-*/;

}

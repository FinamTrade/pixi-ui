package ru.finam.canvasui.client.pixi;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 11.08.14
 * Time: 13:13
 * To change this template use File | Settings | File Templates.
 */
public class Point extends JavaScriptObject {

    protected Point() {}

    public static native Point newInstance(double x, double y) /*-{
        return new $wnd.PIXI.Point(x, y);
    }-*/;

    public final native double getX() /*-{
        return this.x;
    }-*/;

    public final native double getY() /*-{
        return this.y;
    }-*/;

    public final native void setX(double x) /*-{
        this.x = x;
    }-*/;

    public final native void setY(double y) /*-{
        this.y = y;
    }-*/;

}

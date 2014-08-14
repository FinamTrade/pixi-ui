package ru.finam.canvasui.client.pixi;

import com.google.gwt.core.client.JsArray;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 09.08.14
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public class DisplayObjectContainer extends DisplayObject {

    protected DisplayObjectContainer() {}

    public final native void addChild(DisplayObject child) /*-{
        this.addChild(child);
    }-*/;

    public static native DisplayObjectContainer newInstance() /*-{
        return new $wnd.PIXI.DisplayObjectContainer();
    }-*/;

    public final native JsArray<DisplayObject> getChildrens() /*-{
        return this.children;
    }-*/;

    public final native int getWidth() /*-{
        return this.width;
    }-*/;

    public final native int getHeight() /*-{
        return this.height;
    }-*/;

    public final native void setWidth(int w) /*-{
        this.width = w;
    }-*/;

    public final native int setHeight(int h) /*-{
        this.height = h;
    }-*/;

}

package ru.finam.canvasui.client.pixi;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 10.08.14
 * Time: 13:21
 * To change this template use File | Settings | File Templates.
 */
public class Sprite extends DisplayObjectContainer {

    protected Sprite() {}

    public static native Sprite newInstance(Texture texture) /*-{
        return new $wnd.PIXI.Sprite(texture);
    }-*/;

    public final native void setAnchor(Point point) /*-{
        this.anchor = point;
    }-*/;

    public final void setAnchor(double x, double y) {
        setAnchor(Point.newInstance(x, y));
    }

}

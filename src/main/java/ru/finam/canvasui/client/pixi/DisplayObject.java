package ru.finam.canvasui.client.pixi;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 10.08.14
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */
public class DisplayObject extends JavaScriptObject {

    protected DisplayObject() {};

    public final native Rectangle getBounds() /*-{
        return this.getBounds();
    }-*/;

    public final native Rectangle getLocalBounds() /*-{
        return this.getLocalBounds();
    }-*/;

    public final native void setMask(Graphics m) /*-{
       this.mask = m;
    }-*/;

    public final native Graphics getMask() /*-{
       return this.mask;
    }-*/;

    public final native Point getPosition() /*-{
        return this.position;
    }-*/;

    public final native void setPosition(Point p) /*-{
        this.position = p;
    }-*/;

    public final native void setInteractive(boolean b) /*-{
        this.interactive = b;
    }-*/;

    public final native void setButtonMode(boolean b) /*-{
        this.buttonMode  = b;
    }-*/;

    public final native void setAlpha(double a) /*-{
        this.alpha  = a;
    }-*/;

    public final native void setHitArea(Rectangle rect) /*-{
        this.hitArea = rect;
    }-*/;

    public final void setPosition(double x, double y) {
        setPosition(Point.newInstance(x, y));
    }

    public final double getBoundedWidth() {
        return getBounds() == null ? 0 : ( getBounds().getWidth() + 2 * getBounds().getX() );
    }

    public final double getBoundedHeight() {
        return getBounds() == null ? 0 : ( getBounds().getHeight() + 2 * getBounds().getY() );
    }
}

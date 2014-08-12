package ru.finam.canvasui.client.pixi;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 10.08.14
 * Time: 12:47
 * To change this template use File | Settings | File Templates.
 */
public class Graphics extends DisplayObjectContainer {

    protected Graphics() {}

    public final native void lineStyle(int lineWidth, int color, double alpha) /*-{
        this.lineStyle(lineWidth, color, alpha);
    }-*/;

    public final native void drawRect(int x, int y, int width, int height) /*-{
        this.drawRect(x, y, width, height);
    }-*/;

    public static native Graphics newInstance() /*-{
        return new $wnd.PIXI.Graphics();
    }-*/;

    public final native void beginFill(int color, double alpha) /*-{
        this.beginFill(color, alpha);
    }-*/;

    public final native void drawRect(double x, double y, double width, double height) /*-{
        this.drawRect(x, y, width, height);
    }-*/;

    public final native void drawCircle(double x, double y, double radius) /*-{
        this.drawCircle(x, y, radius);
    }-*/;

    public final void drawRect(Rectangle rectangle) {
        drawRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public final void beginFill(int color) {
        beginFill(color, 1);
    }

}

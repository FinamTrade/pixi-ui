package ru.finam.canvasui.client.js.pixi;

/**
 * Created by ikusch on 19.08.14.
 */
public class PointFactory {

    public static native Point newInstance(double x, double y) /*-{
        return new $wnd.PIXI.Point(x, y);
    }-*/;

    public static Point newInstance(Point point) {
        return newInstance(point.getX(), point.getY());
    }
}

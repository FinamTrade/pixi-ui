package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.js.JsType;

/**
 * Created by ikusch on 19.08.14.
 */
@JsType(prototype = "$wnd.PIXI.Graphics")
public interface Graphics extends DisplayObjectContainer {

    void lineStyle(int lineWidth, int color, double alpha);

    void beginFill(int color, double alpha);

    /**
     * Applies a fill to the lines and shapes that were added since the last call to the beginFill() method.
     */
    void endFill();

    void drawRect(double x, double y, double width, double height);

    void drawCircle(double x, double y, double radius);

    void drawEllipse( double x, double  y, double  width, double  height );

    void lineTo(double x, double y);

    void moveTo(double x, double y);

    void clear();

    /**
     *
     * Useful function that returns a texture of the graphics object that can then be used to create
     * sprites This can be quite useful if your geometry is complicated and needs to be reused multiple times.
     *
     * @return
     */
    Texture generateTexture();

    public static class Factory {

        public static native Graphics newInstance() /*-{
            return new $wnd.PIXI.Graphics();
        }-*/;

    }

}

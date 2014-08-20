package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.js.JsType;

/**
 * Created by ikusch on 19.08.14.
 */
@JsType(prototype = "$wnd.PIXI.Graphics")
public interface Graphics extends DisplayObjectContainer {

    void lineStyle(int lineWidth, int color, double alpha);

    void beginFill(int color, double alpha);

    void drawRect(double x, double y, double width, double height);

    void drawCircle(double x, double y, double radius);

    void drawEllipse( double x, double  y, double  width, double  height );

}

package ru.finam.canvasui.client.pixi.custom;

import com.google.gwt.core.client.JavaScriptObject;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.pixi.*;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 10.08.14
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
public class ScrollPanel extends CustomComponentContainer {

    static {
        exportJsObject();
    }

    protected ScrollPanel() {}

    public static native void exportJsObject() /*-{
        $wnd.ScrollPanel = function(innerPanel) {
            $wnd.PIXI.DisplayObjectContainer.call(this);
            this.horizontalScroller = null;
            this.certicalScroller = null;
            this.maskBounds = null;
            this.maskObject = null;
            this.innerPanel = innerPanel;
            this.scrollMaxX = 0;
            this.scrollMaxY = 0;

            var thisScrollPanel = this;

            this.scrollXto = function(newPos) {
                thisScrollPanel.innerPanel.position.x = thisScrollPanel.scrollMaxX * newPos;
            }

            this.scrollYto = function(newPos) {
                thisScrollPanel.innerPanel.position.y = thisScrollPanel.scrollMaxY * newPos;
            }

        }
        $wnd.ScrollPanel.constructor = $wnd.ScrollPanel;
        $wnd.ScrollPanel.prototype = Object.create($wnd.PIXI.DisplayObjectContainer.prototype);
    }-*/;

    private static native void setMouseOverEvents(DisplayObject mouserOverObject,
                                                  Scroller horizonalScroller,
                                                  Scroller verticalScroller) /*-{

        mouserOverObject.setInteractive(true);
        mouserOverObject.interactive = true;

		mouserOverObject.mouseover = function(mouseData){
            if (!!horizonalScroller)
                horizonalScroller.targetAlpha = 1;
            if (!!verticalScroller)
                verticalScroller.targetAlpha = 1;
        }

        mouserOverObject.mouseout = function(mouseData){
            if (!!horizonalScroller)
                horizonalScroller.targetAlpha = 0;
            if (!!verticalScroller)
                verticalScroller.targetAlpha = 0;
        }

    }-*/;

    private static native ScrollPanel newNativeInstance(DisplayObject innerPanel) /*-{
        return new $wnd.ScrollPanel(innerPanel);
    }-*/;

    private native JavaScriptObject getScrollXto() /*-{
        return this.scrollXto;
    }-*/;

    private native JavaScriptObject getScrollYto() /*-{
        return this.scrollYto;
    }-*/;

    private native void setScrollMaxX(double maxX) /*-{
        this.scrollMaxX = maxX;
    }-*/;

    private native void setScrollMaxY(double maxY) /*-{
        this.scrollMaxY = maxY;
    }-*/;

    private native DisplayObject getInnerPanel() /*-{
        return this.innerPanel;
    }-*/;

    public static ScrollPanel newInstance(DisplayObjectContainer innerPanel, boolean drawBorders) {
        return newInstance(innerPanel, innerPanel.getBounds(), drawBorders);
    }

    public static ScrollPanel newInstance(DisplayObject innerPanel, Rectangle maskBounds, boolean drawBorders) {
        ScrollPanel scrollPanel = newNativeInstance(innerPanel);
        scrollPanel.setMaskBounds(maskBounds);
        scrollPanel.addChild(innerPanel);
        scrollPanel.setMaskObject(newMaskObject(maskBounds));
        scrollPanel.addChild(scrollPanel.getMaskObject());
        innerPanel.setPosition(
                Point.newInstance(0, 0));
        scrollPanel.getMaskObject().setPosition(Point.newInstance(0, 0));
        scrollPanel.setMask(scrollPanel.getMaskObject());
        if (drawBorders)
            scrollPanel.drawBorders(maskBounds);
        JsConsole.log("scrollPanel width = " + scrollPanel.getWidth());
        JsConsole.log("maskObject width = "+scrollPanel.getMaskObject().getBounds().getWidth());
        JsConsole.log("maskObject height = "+scrollPanel.getMaskObject().getBounds().getHeight());
        JsConsole.log("maskObject x = "+scrollPanel.getMaskObject().getBounds().getX());
        JsConsole.log("maskObject y = "+scrollPanel.getMaskObject().getBounds().getY());
        scrollPanel.addScrollers();
        scrollPanel.setHitArea(maskBounds);
        setMouseOverEvents(scrollPanel, scrollPanel.getHorizonalScroller(), scrollPanel.getVerticalScroller());
        scrollPanel.setPosition(Point.newInstance(0, 0));

        return scrollPanel;
    }

    private void addHorizontalScroller(double k) {
        Scroller horizonalScroller =
                Scroller.newHorizontalInstance(getMaskBounds().getWidth(), k, getScrollXto());
        setHorizonalScroller(horizonalScroller);
        horizonalScroller.setAlpha(0);
        addChild(horizonalScroller);
        double y = getMaskObject().getBoundedHeight() - Scroller.DEFAULT_WIDE * 2;
        horizonalScroller.setPosition(0, y);
        horizonalScroller.setUpdateFunction(horizonalScroller.newUpdateFunction());
    }

    private void addVerticalScroller(double k) {
        Scroller verticalScroller =
                Scroller.newVerticalInstance(getMaskBounds().getHeight(), k, getScrollYto());
        setVerticalScroller(verticalScroller);
        verticalScroller.setAlpha(0);
        addChild(verticalScroller);
        double x = getMaskObject().getBoundedWidth() - Scroller.DEFAULT_WIDE * 2;
        verticalScroller.setPosition(x, 0);
        verticalScroller.setUpdateFunction(verticalScroller.newUpdateFunction());
    }

    private void addScrollers() {
        double width1 = getInnerPanel().getBoundedWidth();
        double width2 = getMaskObject().getBoundedWidth();
        double maxScrollX = - ( width1 - width2 );
        setScrollMaxX(maxScrollX);
        double kw = width2 / width1;
        if (kw < 1) {
            addHorizontalScroller(kw);
        }

        double height1 = getInnerPanel().getBoundedHeight();
        double height2 = getMaskObject().getBoundedHeight();
        double maxScrollY = - ( height1 - height2 );
        setScrollMaxY(maxScrollY);
        double kh = height2 / height1;
        if (kh < 1) {
            addVerticalScroller(kh);
        }
    }

    private static Graphics newMaskObject(Rectangle bounds) {
        Graphics mask = Graphics.newInstance();
        mask.beginFill(0xFF0000);
        mask.drawRect(bounds);
        JsConsole.log("newMaskObject x = "+bounds.getX());
        JsConsole.log("newMaskObject w = "+bounds.getWidth());
        //mask.setHeight((int) bounds.getHeight());
        //mask.setWidth((int) bounds.getWidth());
        return mask;
    }

    private void drawBorders(Rectangle bounds) {
        Graphics graphics = Graphics.newInstance();
        graphics.lineStyle(1, 0x555555, 1);
        graphics.drawRect(bounds);
        addChild(graphics);
    }

    public static ScrollPanel newInstance(DisplayObjectContainer innerPanel, int width, int height, boolean drawBorders) {
        ScrollPanel scrollPanel = newInstance(innerPanel, Rectangle.newInstance(0, 0, width, height), drawBorders);
        return scrollPanel;
    }

    public static ScrollPanel newInstance(DisplayObjectContainer innerPanel, int width, int height) {
        return newInstance(innerPanel, width, height, false);
    }

    public final native Scroller getHorizonalScroller() /*-{
        return this.horizontalScroller;
    }-*/;

    public final native Rectangle getMaskBounds() /*-{
        return this.maskBounds;
    }-*/;

    public final native Graphics getMaskObject() /*-{
        return this.maskObject;
    }-*/;

    public final native void setHorizonalScroller(Scroller hs) /*-{
        this.horizontalScroller = hs;
    }-*/;

    public final native void setVerticalScroller(Scroller vs) /*-{
        this.verticalScroller = vs;
    }-*/;

    public final native Scroller getVerticalScroller() /*-{
        return this.verticalScroller;
    }-*/;

    public final native void setMaskBounds(Rectangle r) /*-{
        this.maskBounds = r;
    }-*/;

    public final native void setMaskObject(Graphics g) /*-{
        this.maskObject = g;
    }-*/;

}

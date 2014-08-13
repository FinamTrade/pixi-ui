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
public class ScrollPanel extends DisplayObjectContainer {

    static {
        exportJsObject();
    }

    protected ScrollPanel() {}

    public static native void exportJsObject() /*-{
        $wnd.ScrollPanel = function(innerPanel) {
            $wnd.PIXI.DisplayObjectContainer.call(this);
            this.horizontalScroller = null;
            this.maskBounds = null;
            this.maskObject = null;
            this.innerPanel = innerPanel;
            this.scrollMaxX = 0;

            var thisScrollPanel = this;

            this.scrollXto = function(newPos) {
                thisScrollPanel.innerPanel.position.x = thisScrollPanel.scrollMaxX * newPos;
            }

        }
        $wnd.ScrollPanel.constructor = $wnd.ScrollPanel;
        $wnd.ScrollPanel.prototype = Object.create($wnd.PIXI.DisplayObjectContainer.prototype);
    }-*/;

    private static native void setMouseOverEvents(DisplayObject mouserOverObject,
                                                       HorizonalScroller horizonalScroller) /*-{

        mouserOverObject.setInteractive(true);
        mouserOverObject.interactive = true;

		mouserOverObject.mouseover = function(mouseData){
            horizonalScroller.targetAlpha = 1;
        }

        mouserOverObject.mouseout = function(mouseData){
            horizonalScroller.targetAlpha = 0;
        }

    }-*/;

    private static native ScrollPanel newNativeInstance(DisplayObject innerPanel) /*-{
        return new $wnd.ScrollPanel(innerPanel);
    }-*/;

    private native JavaScriptObject getScrollXto() /*-{
        return this.scrollXto;
    }-*/;

    private native void setScrollMaxX(double maxX) /*-{
        this.scrollMaxX = maxX;
    }-*/;

    private native DisplayObject getInnerPanel() /*-{
        return this.innerPanel;
    }-*/;

    public static ScrollPanel newInstance(DisplayObjectContainer innerPanel) {
        return newInstance(innerPanel, innerPanel.getBounds());
    }

    public static ScrollPanel newInstance(DisplayObject innerPanel, Rectangle maskBounds) {
        ScrollPanel scrollPanel = newNativeInstance(innerPanel);
        scrollPanel.setMaskBounds(maskBounds);
        scrollPanel.addChild(innerPanel);
        scrollPanel.setMaskObject(newMaskObject(maskBounds));
        scrollPanel.addChild(scrollPanel.getMaskObject());
        innerPanel.setPosition(
                Point.newInstance(0, 0));
        scrollPanel.getMaskObject().setPosition(Point.newInstance(0, 0));
        scrollPanel.setMask(scrollPanel.getMaskObject());
        JsConsole.log("scrollPanel width = "+scrollPanel.getWidth());
        JsConsole.log("maskObject width = "+scrollPanel.getMaskObject().getBounds().getWidth());
        JsConsole.log("maskObject height = "+scrollPanel.getMaskObject().getBounds().getHeight());
        JsConsole.log("maskObject x = "+scrollPanel.getMaskObject().getBounds().getX());
        JsConsole.log("maskObject y = "+scrollPanel.getMaskObject().getBounds().getY());
        scrollPanel.addScrollers();
        scrollPanel.setHitArea(maskBounds);
        setMouseOverEvents(scrollPanel, scrollPanel.getHorizonalScroller());
        scrollPanel.setUpdateFunction(scrollPanel.getHorizonalScroller().newUpdateFunction());
        scrollPanel.setPosition(Point.newInstance(0, 0));

        return scrollPanel;
    }

    private void addScrollers() {
        double width1 = getInnerPanel().getBoundedWidth();
        double width2 = getMaskObject().getBoundedWidth();
        double maxScrollX = - ( width1 - width2 );
        setScrollMaxX(maxScrollX);
        JsConsole.log("width1 "+width1);
        JsConsole.log("width2 "+width2);
        double k = width2 / width1;
        HorizonalScroller horizonalScroller =
                HorizonalScroller.newInstance(getMaskBounds().getWidth(), k, getScrollXto());
        setHorizonalScroller(horizonalScroller);
        horizonalScroller.setAlpha(0);
        //horizonalScroller.addTo(this);
        addChild(horizonalScroller);
        //double y = getMaskObject().getBounds().getHeight() + getMaskObject().getBounds().getY();
        double y = getMaskObject().getBoundedHeight() - HorizonalScroller.DEFAULT_WIDE * 2;
        JsConsole.log("addScrollers y = "+y);
        JsConsole.log("horizonalScroller y = "+horizonalScroller.getBounds().getY());
        JsConsole.log("horizonalScroller h = "+horizonalScroller.getBounds().getHeight());
        horizonalScroller.setPosition(0, y);
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

    public static ScrollPanel newInstance(DisplayObjectContainer innerPanel, int width, int height) {
        ScrollPanel scrollPanel = newInstance(innerPanel, Rectangle.newInstance(0, 0, width, height));
        return scrollPanel;
    }

    public final native HorizonalScroller getHorizonalScroller() /*-{
        return this.horizontalScroller;
    }-*/;

    public final native Rectangle getMaskBounds() /*-{
        return this.maskBounds;
    }-*/;

    public final native Graphics getMaskObject() /*-{
        return this.maskObject;
    }-*/;

    public final native void setHorizonalScroller(HorizonalScroller hs) /*-{
        this.horizontalScroller = hs;
    }-*/;

    public final native void setMaskBounds(Rectangle r) /*-{
        this.maskBounds = r;
    }-*/;

    public final native void setMaskObject(Graphics g) /*-{
        this.maskObject = g;
    }-*/;

}

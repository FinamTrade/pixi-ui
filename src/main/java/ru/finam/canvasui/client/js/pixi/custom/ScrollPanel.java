package ru.finam.canvasui.client.js.pixi.custom;

import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.pixi.*;
import ru.finam.canvasui.client.js.pixi.DisplayObject;
import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.js.pixi.Graphics;
import ru.finam.canvasui.client.js.pixi.Rectangle;

/**
 * Created by ikusch on 19.08.14.
 */
public class ScrollPanel extends CustomComponentContainer {

    protected ScrollPanel(DisplayObjectContainer mainContainer, DisplayObject innerPanel) {
        super(mainContainer);
        initComponent(mainContainer, innerPanel);
    }

    private final native void initComponent(DisplayObjectContainer mainContainer, DisplayObject innerPanel) /*-{

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
    }-*/;

    private static native void setNativeMouseOverEvents(ScrollPanel scrollPanel, DisplayObject displayObject) /*-{

        displayObject.mouseover = function(mouseData){
            @ru.finam.canvasui.client.js.pixi.custom.ScrollPanel::mouseOvered(Lru/finam/canvasui/client/js/pixi/custom/ScrollPanel;)(scrollPanel);
        }

        displayObject.mouseout = function(mouseData){
            @ru.finam.canvasui.client.js.pixi.custom.ScrollPanel::mouseOuted(Lru/finam/canvasui/client/js/pixi/custom/ScrollPanel;)(scrollPanel);
        }

    }-*/;

    private native JsObject getScrollXto() /*-{
        return this.scrollXto;
    }-*/;

    private native JsObject getScrollYto() /*-{
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
        ScrollPanel scrollPanel = new ScrollPanel(DisplayObjectContainerFactory.newInstance(), innerPanel);

        scrollPanel.setMaskBounds(maskBounds);
        scrollPanel.addChild(innerPanel);
        scrollPanel.setMaskObject(newMaskObject(maskBounds));
        scrollPanel.addChild(scrollPanel.getMaskObject());
        innerPanel.setPosition(PointFactory.newInstance(0, 0));
        scrollPanel.getMaskObject().setPosition(PointFactory.newInstance(0, 0));
        scrollPanel.setMask(scrollPanel.getMaskObject());
        if (drawBorders)
            scrollPanel.drawBorders(maskBounds);
        scrollPanel.addScrollers();
        scrollPanel.setHitArea(maskBounds);
        setMouseOverEvents(scrollPanel);
        scrollPanel.setPosition(PointFactory.newInstance(0, 0));

        return scrollPanel;
    }

    private static void setMouseOverEvents(ScrollPanel scrollPanel) {
        scrollPanel.displayObjectContainer().setInteractive(true);
        setNativeMouseOverEvents(scrollPanel, scrollPanel.displayObjectContainer());
        /*
        scrollPanel.displayObjectContainer().setMouseover(new MouseEventListener() {
            @Override
            public void onEvent() {
                JsConsole.log("setMouseover!");
            }
        });
        */
    }

    public static void mouseOvered(ScrollPanel scrollPanel) {
        if (scrollPanel.getHorizonalScroller() != null && scrollPanel.getHorizonalScroller().displayObjectContainer() != null) {
            scrollPanel.getHorizonalScroller().displayObjectContainer().setTargetAlpha(1);
        }
        if (scrollPanel.getVerticalScroller() != null && scrollPanel.getVerticalScroller().displayObjectContainer() != null) {
            scrollPanel.getVerticalScroller().displayObjectContainer().setTargetAlpha(1);
        }
    }

    public static void mouseOuted(ScrollPanel scrollPanel) {
        if (scrollPanel.getHorizonalScroller() != null && scrollPanel.getHorizonalScroller().displayObjectContainer() != null) {
            scrollPanel.getHorizonalScroller().displayObjectContainer().setTargetAlpha(0);
        }
        if (scrollPanel.getVerticalScroller() != null && scrollPanel.getVerticalScroller().displayObjectContainer() != null) {
            scrollPanel.getVerticalScroller().displayObjectContainer().setTargetAlpha(0);
        }
    }

    public final double getBoundedWidth(DisplayObject displayObject) {
        return displayObject.getBounds() == null ? 0 : ( displayObject.getBounds().getWidth() + 2 * displayObject.getBounds().getX() );
    }

    public final double getBoundedHeight(DisplayObject displayObject) {
        return displayObject.getBounds() == null ? 0 : ( displayObject.getBounds().getHeight() + 2 * displayObject.getBounds().getY() );
    }

    private void addHorizontalScroller(double k) {
        Scroller horizonalScroller =
                Scroller.newHorizontalInstance(getMaskBounds().getWidth(), k, getScrollXto());
        setHorizonalScroller(horizonalScroller);
        horizonalScroller.setAlpha(0);
        addChild(horizonalScroller.displayObjectContainer());
        double y = getBoundedHeight(getMaskObject()) - Scroller.DEFAULT_WIDE * 2;
        horizonalScroller.setPosition(PointFactory.newInstance(0, y));
        horizonalScroller.setUpdateFunction(horizonalScroller.newUpdateFunction());
    }

    private void addVerticalScroller(double k) {
        Scroller verticalScroller =
                Scroller.newVerticalInstance(getMaskBounds().getHeight(), k, getScrollYto());
        setVerticalScroller(verticalScroller);
        verticalScroller.setAlpha(0);
        addChild(verticalScroller.displayObjectContainer());
        double x = getBoundedWidth(getMaskObject()) - Scroller.DEFAULT_WIDE * 2;
        verticalScroller.setPosition(PointFactory.newInstance(x, 0));
        verticalScroller.setUpdateFunction(verticalScroller.newUpdateFunction());
    }

    private void addScrollers() {
        double width1 = getBoundedWidth(getInnerPanel());
        double width2 = getBoundedWidth(getMaskObject());
        double maxScrollX = - ( width1 - width2 );
        setScrollMaxX(maxScrollX);
        double kw = width2 / width1;
        if (kw < 1) {
            addHorizontalScroller(kw);
        }

        double height1 = getBoundedHeight(getInnerPanel());
        double height2 = getBoundedHeight(getMaskObject());
        double maxScrollY = - ( height1 - height2 );
        setScrollMaxY(maxScrollY);
        double kh = height2 / height1;
        if (kh < 1) {
            addVerticalScroller(kh);
        }
    }

    private static Graphics newMaskObject(Rectangle bounds) {
        Graphics mask = GraphicsFactory.newInstance();
        mask.beginFill(0xFF0000, 1);
        mask.drawRect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        return mask;
    }

    private void drawBorders(Rectangle bounds) {
        Graphics graphics = GraphicsFactory.newInstance();
        graphics.lineStyle(1, 0x555555, 1);
        graphics.drawRect(bounds.getX(), bounds.getY(), (int)(bounds.getWidth() - 1), (int)(bounds.getHeight() - 1));
        addChild(graphics);
    }

    public static ScrollPanel newInstance(DisplayObjectContainer innerPanel, int width, int height, boolean drawBorders) {
        ScrollPanel scrollPanel = newInstance(innerPanel, RectangleFactory.newInstance(0, 0, width, height), drawBorders);
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
package ru.finam.canvasui.client.js.pixi.custom;

import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.user.client.Window;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.pixi.*;
import ru.finam.canvasui.client.js.pixi.DisplayObject;
import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.js.pixi.Graphics;
import ru.finam.canvasui.client.js.pixi.Rectangle;
import ru.finam.canvasui.client.js.pixi.custom.channel.EventListener;
import ru.finam.canvasui.client.js.pixi.custom.channel.MouseWheelEventChannel;

/**
 * Created by ikusch on 19.08.14.
 */
public class ScrollPanel extends CustomComponentContainer {

    private static final int MOUSE_WHEEL_SCROLL_K = 3;
    private Scroller horizontalScroller;
    private Scroller verticalScroller;
    private Rectangle maskBounds;
    private Graphics maskObject;
    private double scrollMaxX;
    private double scrollMaxY;
    private boolean mouseOvered = false;
    private DisplayObject innerPanel;
    private ScrollCallback scrollXto = new ScrollCallback() {
        @Override
        public void onScroll(double pos) {
            doScrollXTo(pos);
        }
    };
    private ScrollCallback scrollYto = new ScrollCallback() {
        @Override
        public void onScroll(double pos) {
            doScrollYTo(pos);
        }
    };

    protected ScrollPanel(DisplayObjectContainer mainContainer, Rectangle maskBounds, DisplayObject innerPanel, boolean drawBorders) {
        super(mainContainer);
        this.innerPanel = innerPanel;
        this.maskBounds = maskBounds;
        addChild(innerPanel);
        this.maskObject = newMaskObject(maskBounds);
        addChild(this.maskObject);
        innerPanel.setPosition(PointFactory.newInstance(0, 0));
        this.maskObject .setPosition(PointFactory.newInstance(0, 0));
        setMask(this.maskObject );
        if (drawBorders)
            drawBorders(maskBounds);
        addScrollers();
        setHitArea(maskBounds);
        setMouseOverEvents(this);
        setPosition(PointFactory.newInstance(0, 0));
        MouseWheelEventChannel.addNewListener(new EventListener<MouseWheelEvent>() {
            @Override
            public void onEvent(MouseWheelEvent event) {
                onMouseWheelEvent(event);
            }
        });
    }

    private void mouseWheelReaction(double deltaY) {
        double scrollMaxY = getScrollMaxY();
        double currentY = this.getInnerPanel().getPosition().getY();
        double newY = currentY - deltaY;
        if (newY > 0)
            newY = 0;
        if (newY < scrollMaxY)
            newY = scrollMaxY;
        double k = newY / scrollMaxY;
        doScrollYTo(k);
        this.verticalScroller.updateCoordK(k);
    }

    private void onMouseWheelEvent(MouseWheelEvent event) {
        if (this.mouseOvered) {
            double deltaY = event.getDeltaY() * MOUSE_WHEEL_SCROLL_K;
            mouseWheelReaction(deltaY);
        }
    }

    private static native void setMouseOverEvents(ScrollPanel scrollPanel, DisplayObject displayObject) /*-{

        displayObject.mouseover = function(mouseData){
            scrollPanel.@ru.finam.canvasui.client.js.pixi.custom.ScrollPanel::mouseOvered()();
        }

        displayObject.mouseout = function(mouseData){
            scrollPanel.@ru.finam.canvasui.client.js.pixi.custom.ScrollPanel::mouseOuted()();
        }

    }-*/;

    public void doScrollXTo(double newPos) {
        this.getInnerPanel().getPosition().setX(getScrollMaxX() * newPos);
    }

    public void doScrollYTo(double newPos) {
        this.getInnerPanel().getPosition().setY(getScrollMaxY() * newPos);
    }

    private void setScrollMaxX(double maxX) {
        this.scrollMaxX = maxX;
    }

    private double getScrollMaxX() {
        return this.scrollMaxX;
    }

    private void setScrollMaxY(double maxY) {
        this.scrollMaxY = maxY;
    };

    private double getScrollMaxY() {
        return this.scrollMaxY;
    };

    private DisplayObject getInnerPanel() {
        return this.innerPanel;
    }

    public static ScrollPanel newInstance(DisplayObjectContainer innerPanel, boolean drawBorders) {
        return newInstance(innerPanel, innerPanel.getBounds(), drawBorders);
    }

    public static ScrollPanel newInstance(DisplayObject innerPanel, Rectangle maskBounds, boolean drawBorders) {
        return new ScrollPanel(DisplayObjectContainerFactory.newInstance(), maskBounds, innerPanel, drawBorders);
    }

    private static void setMouseOverEvents(ScrollPanel scrollPanel) {
        scrollPanel.getMainComponent().setInteractive(true);
        setMouseOverEvents(scrollPanel, scrollPanel.getMainComponent());
    }

    public void mouseOvered() {
        this.mouseOvered = true;
        if (this.horizontalScroller != null && this.horizontalScroller.getMainComponent() != null) {
            this.horizontalScroller.setTargetAlpha(1);
        }
        if (this.verticalScroller != null && this.verticalScroller.getMainComponent() != null) {
            this.verticalScroller.setTargetAlpha(1);
        }
    }

    public void mouseOuted() {
        this.mouseOvered = false;
        if (this.horizontalScroller != null && this.horizontalScroller.getMainComponent() != null) {
            this.horizontalScroller.setTargetAlpha(0);
        }
        if (this.verticalScroller != null && this.verticalScroller.getMainComponent() != null) {
            this.verticalScroller.setTargetAlpha(0);
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
                Scroller.newHorizontalInstance(this.maskBounds.getWidth(), k, this.scrollXto);
        this.horizontalScroller = horizonalScroller;
        horizonalScroller.setAlpha(0);
        addChild(horizonalScroller.getMainComponent());
        double y = getBoundedHeight(this.maskObject) - Scroller.DEFAULT_WIDE * 2;
        horizonalScroller.setPosition(PointFactory.newInstance(0, y));
        horizonalScroller.setUpdateFunction(horizonalScroller.newUpdateFunction());
    }

    private void addVerticalScroller(double k) {
        Scroller verticalScroller =
                Scroller.newVerticalInstance(this.maskBounds.getHeight(), k, this.scrollYto);
        this.verticalScroller = verticalScroller;
        verticalScroller.setAlpha(0);
        addChild(verticalScroller.getMainComponent());
        double x = getBoundedWidth(this.maskObject) - Scroller.DEFAULT_WIDE * 2;
        verticalScroller.setPosition(PointFactory.newInstance(x, 0));
        verticalScroller.setUpdateFunction(verticalScroller.newUpdateFunction());
    }

    private void addScrollers() {
        double width1 = getBoundedWidth(getInnerPanel());
        double width2 = getBoundedWidth(this.maskObject);
        double maxScrollX = - ( width1 - width2 );
        setScrollMaxX(maxScrollX);
        double kw = width2 / width1;
        if (kw < 1) {
            addHorizontalScroller(kw);
        }

        double height1 = getBoundedHeight(getInnerPanel());
        double height2 = getBoundedHeight(this.maskObject);
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

}
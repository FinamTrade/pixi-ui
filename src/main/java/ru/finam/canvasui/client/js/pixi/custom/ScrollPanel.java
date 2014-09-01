package ru.finam.canvasui.client.js.pixi.custom;

import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.user.client.Window;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.JsObject;
import ru.finam.canvasui.client.js.gsap.PropertiesSet;
import ru.finam.canvasui.client.js.pixi.*;
import ru.finam.canvasui.client.js.pixi.DisplayObject;
import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.js.pixi.Graphics;
import ru.finam.canvasui.client.js.pixi.Rectangle;
import ru.finam.canvasui.client.js.pixi.custom.channel.EventListener;
import ru.finam.canvasui.client.js.pixi.custom.channel.MouseWheelEventChannel;
import ru.finam.canvasui.client.js.pixi.custom.event.ComponentUpdateEvent;

/**
 * Created by ikusch on 19.08.14.
 */
public class ScrollPanel extends CustomComponentContainer {

    private static final int MOUSE_WHEEL_SCROLL_K = 3;
    private Scroller horizontalScroller;
    private Scroller verticalScroller;
    private Rectangle maskBounds;
    public Graphics maskObject;
    private double scrollMaxX;
    private double scrollMaxY;
    private boolean mouseOvered = false;
    private CustomComponentContainer innerPanel;
    private ScrollCallback scrollXto = new ScrollCallback() {
        @Override
        public void onScroll(double pos) {
            scrollXToIfScrollable(pos);
        }
    };
    private ScrollCallback scrollYto = new ScrollCallback() {
        @Override
        public void onScroll(double pos) {
            scrollYToIfScrollable(pos);
        }
    };

    protected ScrollPanel(DisplayObjectContainer mainContainer, Rectangle maskBounds, CustomComponentContainer innerPanel, boolean drawBorders) {
        super(mainContainer);
        this.innerPanel = innerPanel;
        this.maskBounds = maskBounds;
        addChild(innerPanel);
        this.maskObject = newMaskObject();
        addChild(this.maskObject);
        innerPanel.setPosition(PointFactory.newInstance(0, 0));
        this.maskObject.setPosition(PointFactory.newInstance(0, 0));
        setMask(this.maskObject );
        if (drawBorders)
            drawBorders(maskBounds);
        updateScrollers();
        setHitArea(maskBounds);
        setMouseOverEvents(this);
        setPosition(PointFactory.newInstance(0, 0));
        listeners();
    }

    private void listeners() {
        MouseWheelEventChannel.addNewListener(new EventListener<MouseWheelEvent>() {
            @Override
            public void onEvent(MouseWheelEvent event) {
                onMouseWheelEvent(event);
            }
        });
        if (innerPanel instanceof UpdatableComponent) {
            ((UpdatableComponent) innerPanel).componentUpdateEventChannel().addListener(new EventListener<ComponentUpdateEvent>() {
                @Override
                public void onEvent(ComponentUpdateEvent event) {
                    onInnerPanelUpdate();
                }
            });
        }
    }

    private void onInnerPanelUpdate() {
        updateScrollers();
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
        scrollYToIfScrollable(k);
        this.verticalScroller.updateCoordK(k);
    }

    private void onMouseWheelEvent(MouseWheelEvent event) {
        if (this.mouseOvered && this.verticalScroller != null && this.verticalScroller.getK() < 1) {
            if (verticalScroller.getAlpha() == 0)
                mouseOvered();
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

    public void doScrollXTo() {
        doScrollXTo(this.horizontalScroller.getScrollPosition());
    }

    public void scrollYToIfScrollable(double newPos) {
        if (verticalScroller != null)
            doScrollYTo(newPos);
    }

    public void scrollXToIfScrollable(double newPos) {
        if (horizontalScroller != null)
            doScrollXTo(newPos);
    }

    public void doScrollYTo() {
        doScrollYTo(this.verticalScroller.getScrollPosition());
    }

    public void doScrollYTo(double newPos) {
        this.getInnerPanel().getPosition().setY(getScrollMaxY() * newPos);
    }

    private double innerPanelScrolledYposK() {
        return this.getInnerPanel().getPosition().getY() / getScrollMaxY();
    }

    private double innerPanelScrolledXposK() {
        return this.getInnerPanel().getPosition().getX() / getScrollMaxX();
    }

    private void setScrollMaxX(double maxX) {
        this.scrollMaxX = maxX;
    }

    private double getScrollMaxX() {
        return this.scrollMaxX;
    }

    private void setScrollMaxY(double maxY) {
        this.scrollMaxY = maxY;
    }

    private double getScrollMaxY() {
        return this.scrollMaxY;
    }

    private CustomComponentContainer getInnerPanel() {
        return this.innerPanel;
    }

    public static ScrollPanel newInstance(CustomComponentContainer innerPanel, boolean drawBorders) {
        return newInstance(innerPanel, innerPanel.getBounds(), drawBorders);
    }

    public static ScrollPanel newInstance(CustomComponentContainer innerPanel, Rectangle maskBounds, boolean drawBorders) {
        return new ScrollPanel(DisplayObjectContainer.Factory.newInstance(), maskBounds, innerPanel, drawBorders);
    }

    private static void setMouseOverEvents(ScrollPanel scrollPanel) {
        scrollPanel.getMainComponent().setInteractive(true);
        setMouseOverEvents(scrollPanel, scrollPanel.getMainComponent());
    }

    public void mouseOvered() {
        this.mouseOvered = true;
        if (this.horizontalScroller != null && this.horizontalScroller.getMainComponent() != null) {
            horizontalScroller.animatedShow();
        }
        if (this.verticalScroller != null && this.verticalScroller.getMainComponent() != null) {
            verticalScroller.animatedShow();
        }
    }

    public void mouseOuted() {
        this.mouseOvered = false;
        if (this.horizontalScroller != null && this.horizontalScroller.getMainComponent() != null && !this.horizontalScroller.isDragging()) {
            horizontalScroller.animatedHide();
        }
        if (this.verticalScroller != null && this.verticalScroller.getMainComponent() != null && !this.verticalScroller.isDragging()) {
            verticalScroller.animatedHide();
        }
    }

    private void newHorizontalScroller(double k) {
        horizontalScroller = Scroller.newHorizontalInstance(this.maskBounds.getWidth(), k, this.scrollXto, 0);
        addChild(horizontalScroller.getMainComponent());
        double y = this.maskBounds.getHeight() - Scroller.DEFAULT_WIDE * 2;
        horizontalScroller.setPosition(PointFactory.newInstance(0, y));
    }

    private void newVerticalScroller(double k) {
        verticalScroller = Scroller.newVerticalInstance(this.maskBounds.getHeight(), k, this.scrollYto, 0);
        addChild(verticalScroller.getMainComponent());
        double x = this.maskBounds.getWidth() - Scroller.DEFAULT_WIDE * 2;
        verticalScroller.setPosition(PointFactory.newInstance(x, 0));
    }

    private void updateScrollers() {
        double width1 = getInnerPanel().getBoundedWidth();
        double width2 = this.maskBounds.getWidth();
        double maxScrollX = - ( width1 - width2 );
        setScrollMaxX(maxScrollX);
        double kw = width2 / width1;
        if (kw < 1) {
            updateHorizontalScroller(kw);
        }
        else {
            removeHorizontalScroller();
            doScrollXTo(0);
        }

        double height1 = getInnerPanel().getBoundedHeight() + 1;
        double height2 = this.maskBounds.getHeight();
        double maxScrollY = - ( height1 - height2 );
        setScrollMaxY(maxScrollY);
        double kh = height2 / height1;
        if (kh < 1) {
            updateVerticalScroller(kh);
        }
        else {
            removeVerticalScroller();
            doScrollYTo(0);
        }
    }

    private void removeHorizontalScroller() {
        if (horizontalScroller != null) {
            horizontalScroller.animatedRemove();
            horizontalScroller = null;
        }
    }

    private void removeVerticalScroller() {
        if (verticalScroller != null) {
            verticalScroller.animatedRemove();
            verticalScroller = null;
        }
    }

    private void updateHorizontalScroller(double k) {
        if (horizontalScroller == null)
            newHorizontalScroller(k);
        else {
            horizontalScroller.updateK(k);
        }
        double innerPanelScrolledXposK = innerPanelScrolledXposK();
        if (innerPanelScrolledXposK() > 1) {
            doScrollXTo();
        }
        else {
            horizontalScroller.updateCoordK(innerPanelScrolledXposK);
        }
        if (this.mouseOvered && horizontalScroller.getAlpha() == 0)
            mouseOvered();
    }

    private void updateVerticalScroller(double k) {
        if (verticalScroller == null)
            newVerticalScroller(k);
        else {
            verticalScroller.updateK(k);
        }
        double innerPanelScrolledYposK = innerPanelScrolledYposK();
        if (innerPanelScrolledYposK > 1) {
            doScrollYTo();
        }
        else {
            verticalScroller.updateCoordK(innerPanelScrolledYposK);
        }
        if (this.mouseOvered && verticalScroller.getAlpha() == 0)
            mouseOvered();
    }

    private Graphics newMaskObject() {
        Graphics mask = Graphics.Factory.newInstance();
        mask.beginFill(0xFF0000, 1);
        mask.drawRect(this.maskBounds.getX(), this.maskBounds.getY(), this.maskBounds.getWidth(), this.maskBounds.getHeight());
        return mask;
    }

    private void drawBorders(Rectangle bounds) {
        Graphics graphics = Graphics.Factory.newInstance();
        graphics.lineStyle(1, 0x555555, 1);
        graphics.drawRect(bounds.getX(), bounds.getY(), (int)(bounds.getWidth() - 1), (int)(bounds.getHeight() - 1));
        addChild(graphics);
    }

    public static ScrollPanel newInstance(CustomComponentContainer innerPanel, int width, int height, boolean drawBorders) {
        ScrollPanel scrollPanel = newInstance(innerPanel, Rectangle.Factory.newInstance(0, 0, width, height + 1),
                drawBorders);
        return scrollPanel;
    }

    public static ScrollPanel newInstance(CustomComponentContainer innerPanel, int width, int height) {
        return newInstance(innerPanel, width, height, false);
    }

    public static ScrollPanel newInstance(CustomComponentContainer innerPanel) {
        return newInstance(innerPanel, (int) innerPanel.getBoundedWidth(), (int) innerPanel.getBoundedHeight(), false);
    }

}
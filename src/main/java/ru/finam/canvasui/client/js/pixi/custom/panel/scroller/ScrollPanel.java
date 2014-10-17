package ru.finam.canvasui.client.js.pixi.custom.panel.scroller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import ru.finam.canvasui.client.js.gsap.easing.Ease;
import ru.finam.canvasui.client.js.gsap.easing.Power1;
import ru.finam.canvasui.client.js.pixi.*;
import ru.finam.canvasui.client.js.pixi.custom.channel.EventListener;
import ru.finam.canvasui.client.js.pixi.custom.channel.MouseWheelEventChannel;
import ru.finam.canvasui.client.js.pixi.custom.channel.ScrollEventChannel;
import ru.finam.canvasui.client.js.pixi.custom.event.ComponentUpdateEvent;
import ru.finam.canvasui.client.js.pixi.custom.event.ScrollEvent;
import ru.finam.canvasui.client.js.pixi.custom.event.TouchEvent;
import ru.finam.canvasui.client.js.pixi.custom.panel.UpdatableComponent;
import ru.finam.canvasui.client.js.pixi.custom.panel.masked.HasMask;
import ru.finam.canvasui.client.js.pixi.custom.panel.masked.MaskObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ikusch on 19.08.14.
 */
public class ScrollPanel extends ComponentWithKinematicDraggable implements HasMask {

    private static final double DEF_WRAPPER_SIZE = 100;
    private static final int MOUSE_WHEEL_SCROLL_K = 3;
    private static final int VERTICAL_I = ScrollOrientation.VERTICAL.ordinal();
    private static final int HORIZONTAL_I = ScrollOrientation.HORIZONTAL.ordinal();
    private static final double SCROLLER_EDGE_LENGTH = 0;
    private static final double DRAG_TRESHOLD = 8;
    private Scroller[] scrollers = new Scroller[ORIENTATIONS_LENGTH];
    public MaskObject maskObject;
    private boolean mouseOvered = false;
    private ComponentWithShowSizes<DisplayObject> innerPanel;
    private ScrollCallback scrollTo = new ScrollCallback() {
        @Override
        public void onScroll(double pos, ScrollOrientation orientation) {
            scrollToIfScrollable(pos, orientation);
        }
    };
    private Point dragStartPos;
    private Set<Runnable> actionsWhenFlickComplete = new HashSet<>();

    protected ScrollPanel(DisplayObjectContainer mainContainer, Rectangle maskBounds,
                          ComponentWithShowSizes innerPanel, boolean drawBorders, Ease ease) {
        this(mainContainer, maskBounds, innerPanel, drawBorders);
        setEase(ease);
    }

    protected ScrollPanel(DisplayObjectContainer mainContainer, Rectangle maskBounds, ComponentWithShowSizes innerPanel, boolean drawBorders) {
        super(mainContainer);
        this.innerPanel = innerPanel;
        addChild(innerPanel);
        this.maskObject = newMaskObject(maskBounds);
        addChild(this.maskObject.mainComponent());
        innerPanel.setPosition(PointFactory.newInstance(0, 0));
        this.maskObject.mainComponent().setPosition(PointFactory.newInstance(0, 0));
        setMask(this.maskObject.mainComponent());
        if (drawBorders)
            drawBorders(maskObject.getMaskBounds());
        updateScrollers();
        setHitArea(maskObject.getMaskBounds());
        setMouseOverEvents(this);
        setTouchEvents();
        setPosition(PointFactory.newInstance(0, 0));
        listeners();
    }

    private void setTouchEvents() {
        this.innerPanel.setInteractive(true);
        updateHitArea();
        createTouchDraggable(this.innerPanel.getMainComponent());
    }

    protected final native void createTouchDraggable(DisplayObject innerPanel) /*-{

        var theScrollPanel = this;
        innerPanel.mousedown =
        innerPanel.touchstart = function(data)
        {
            theScrollPanel.@ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ScrollPanel::touchStart(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/event/TouchEvent;)(data, this);
        };

        innerPanel.mouseup = innerPanel.mouseupoutside =
        innerPanel.touchend = innerPanel.touchendoutside = function(data)
        {
            theScrollPanel.@ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ScrollPanel::touchEnd(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/event/TouchEvent;)(data, this);
        };

        innerPanel.mousemove =
        innerPanel.touchmove = function(data)
        {
            theScrollPanel.@ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ScrollPanel::touchMove(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/event/TouchEvent;)(data, this);
        }

    }-*/;

    private boolean pointWithinRectangle(Point point, Rectangle bounds) {
        return ( (point.getX() < ( bounds.getX() + bounds.getWidth() )) && (point.getX() > bounds.getX())
            && (point.getY() < ( bounds.getY() + bounds.getHeight())) && (point.getY() > bounds.getY()) );
    }

    protected double defaultAlpha() {
        return 1;
    }

    protected double getVisibleLength(ScrollOrientation orientation) {
        return orientation.getLength(maskObject.getMaskBounds());
    }

    protected double dragEndEdge(ScrollOrientation orientation) {
        return 0;
    }

    protected double startEdge(ScrollOrientation orientation) {
        return this.scrollMaxOffset[orientation.ordinal()];
    }

    protected void updateDraggableCopmonents(double newOffset, TouchEvent that,
                                           ScrollOrientation orientation) {
        orientation.setOffset(this.innerPanel.getPosition(), newOffset);
        Scroller scroller = scrollers[orientation.ordinal()];
        if (scroller != null)
            scroller.updateScrollPosK(innerPanelScrolledOffsetK(orientation), true);
    }

    protected void touchStart(MouseEvent data, TouchEvent that) {
        double newCoordX = data.getLocalPosition(that.getParent()).getX();
        double newCoordY = data.getLocalPosition(that.getParent()).getY();
        if (pointWithinRectangle(PointFactory.newInstance(newCoordX, newCoordY), this.maskObject.getMaskBounds())) {
            super.touchStart(data, that);
        }
    }

    @Override
    protected double dragWrapperSize(ScrollOrientation orientation) {
        if (scrollers[orientation.ordinal()] != null)
            return Math.min(getVisibleLength(orientation) / 5, DEF_WRAPPER_SIZE);
        else
            return 0;
    }

    @Override
    protected double dragTreshold() {
        return DRAG_TRESHOLD;
    }

    @Override
    protected DisplayObject getMainDragComponent() {
        return this.innerPanel.getMainComponent();
    }

    protected double draggingAlpha() {
        return 1;
    }

    @Override
    protected DisplayObject getDraggableComponent() {
        return this.innerPanel.getMainComponent();
    }

    @Override
    protected double getScrollerEdgeLength() {
        return SCROLLER_EDGE_LENGTH;
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

    private Runnable onInnerPanelUpdateAction() {
        return new Runnable() {
            @Override
            public void run() {
                onInnerPanelUpdate();
            }
        };
    }

    private void onInnerPanelUpdate() {
        if (!flickTimeline().isActive()) {
            updateScrollers();
            setTouchEvents();
        }
        else {
            actionsWhenFlickComplete.add(onInnerPanelUpdateAction());
        }
    }

    @Override
    public void onFlickComplete() {
        for (Runnable task : actionsWhenFlickComplete) {
            task.run();
            actionsWhenFlickComplete.remove(task);
        }
    }

    private void mouseWheelReaction(double deltaY) {
        double scrollMaxY = scrollMaxOffset[VERTICAL_I];
        double currentY = this.getInnerPanel().getPosition().getY();
        double newY = currentY - deltaY;
        if (newY > 0)
            newY = 0;
        if (newY < scrollMaxY)
            newY = scrollMaxY;
        double k = newY / scrollMaxY;
        if (this.scrollers[VERTICAL_I] != null) {
            scrollToIfScrollable(k, ScrollOrientation.VERTICAL);
            this.scrollers[VERTICAL_I].updateScrollPosK(k, true);
        }
        if (this.scrollers[HORIZONTAL_I] != null) {
            this.scrollers[HORIZONTAL_I].updateScrollPosK(true);
            double k2 = this.scrollers[HORIZONTAL_I].getScrollPosition();
            scrollToIfScrollable(k2, ScrollOrientation.HORIZONTAL);
        }
    }

    private void onMouseWheelEvent(MouseWheelEvent event) {
        if (this.mouseOvered && this.scrollers[VERTICAL_I] != null && this.scrollers[VERTICAL_I].getK() < 1) {
            if (scrollers[VERTICAL_I].getAlpha() == 0)
                mouseOvered();
            double deltaY = event.getDeltaY() * MOUSE_WHEEL_SCROLL_K;
            mouseWheelReaction(deltaY);
        }
    }

    private static native void setMouseOverEvents(ScrollPanel scrollPanel, DisplayObject displayObject) /*-{

        displayObject.mouseover = function(mouseData){
            scrollPanel.@ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ScrollPanel::mouseOvered()();
        }

        displayObject.mouseout = function(mouseData){
            scrollPanel.@ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ScrollPanel::mouseOuted()();
        }

    }-*/;

    public void addNewScrollListener(EventListener<ScrollEvent> scrollEventListener) {
        ScrollEventChannel.getInstance().addListener(scrollEventListener);
    }

    public void doScrollTo(double newPos, ScrollOrientation orientation) {
        moveAndUpdateDraggableCopmonents(scrollMaxOffset[orientation.ordinal()] * newPos, null, orientation);
        ScrollEventChannel.getInstance().fire(new ScrollEvent(newPos));
    }

    public double scrollPosition(ScrollOrientation orientation) {
        return this.scrollers[orientation.ordinal()] == null ? 0 : this.scrollers[orientation.ordinal()].getScrollPosition();
    }

    public void doScrollTo(ScrollOrientation orientation) {
        doScrollTo(this.scrollers[orientation.ordinal()].getScrollPosition(), orientation);
    }

    public void scrollToIfScrollable(double newPos, ScrollOrientation orientation) {
        if (this.scrollers[orientation.ordinal()] != null) {
            if (flickTimeline().isActive()) {
                killFlickAnimation();
            }
            onFlickComplete();
            doScrollTo(newPos, orientation);
        }
    }

    private double innerPanelScrolledOffsetK(ScrollOrientation orientation) {
        return orientation.getOffset(this.getInnerPanel().getPosition()) / scrollMaxOffset[orientation.ordinal()];
    }

    private ComponentWithShowSizes getInnerPanel() {
        return this.innerPanel;
    }

    public static ScrollPanel newInstance(ComponentWithShowSizes innerPanel, boolean drawBorders) {
        return newInstance(innerPanel, innerPanel.getBounds(), drawBorders, Power1.Static.easeOut());
    }

    private static ScrollPanel newInstance(ComponentWithShowSizes innerPanel, Rectangle maskBounds,
                                          boolean drawBorders, Ease ease) {
        return new ScrollPanel(DisplayObjectContainer.Factory.newInstance(), maskBounds, innerPanel, drawBorders, ease);
    }

    private static ScrollPanel newInstance(ComponentWithShowSizes innerPanel, Rectangle maskBounds, boolean drawBorders) {
        return newInstance(innerPanel, maskBounds, drawBorders, Power1.Static.easeOut());
    }

    private static void setMouseOverEvents(ScrollPanel scrollPanel) {
        scrollPanel.getMainComponent().setInteractive(true);
        setMouseOverEvents(scrollPanel, scrollPanel.getMainComponent());
    }

    public void mouseOvered() {
        this.mouseOvered = true;
        for (Scroller scroller : scrollers) {
            if (scroller != null && scroller.getMainComponent() != null) {
                scroller.animatedShow();
            }
        }
    }

    public void mouseOuted() {
        this.mouseOvered = false;
        for (Scroller scroller : scrollers) {
            if (scroller != null && scroller.getMainComponent() != null && !scroller.isDragging()) {
                scroller.animatedHide();
            }
        }
    }

    private void newScroller(double k, ScrollOrientation orientation) {
        scrollers[orientation.ordinal()] = Scroller.newInstance(orientation.getLength(this.maskObject.getMaskBounds()), k,
                this.scrollTo, 0, orientation, getEase());
        addChild(scrollers[orientation.ordinal()].getMainComponent());
        double offset = orientation.getOrtogonalLength(this.maskObject.getMaskBounds()) - Scroller.DEFAULT_WIDE * 2;
        scrollers[orientation.ordinal()].setPosition(orientation.newPoint(0, offset));
    }

    private void updateScrollers() {
        for (ScrollOrientation orientation : ScrollOrientation.values()) {
            updateScroller(orientation);
        }
    }

    private void updateScroller(ScrollOrientation orientation) {
        double length1 = orientation.getLength(getInnerPanel());
        if (orientation.equals(ScrollOrientation.VERTICAL) && length1 > 0) {
            length1 += 1;
        }
        double length2 = orientation.getLength(this.maskObject.getMaskBounds());
        double maxScrollOffset = 0;
        if (length1 > length2) {
            maxScrollOffset = -(length1 - length2);
        }
        scrollMaxOffset[orientation.ordinal()] = maxScrollOffset;
        if (length1 > 0 && length2 > 0) {
            updateScroller(length1, length2, orientation);
        }
    }

    private void updateScroller(double length1, double length2, ScrollOrientation orientation) {
        double k = length2 / length1;
        GWT.log("orientation = " + orientation.name());
        GWT.log("length1 = " + length1);
        GWT.log("length2 = " + length2);
        GWT.log("k = " + k);
        if (k < 1) {
            updateScroller(k, orientation);
        } else {
            removeScroller(orientation);
            doScrollTo(0, orientation);
        }
    }

    private void removeScroller(ScrollOrientation orientation) {
        if (scrollers[orientation.ordinal()] != null) {
            scrollers[orientation.ordinal()].animatedRemove();
            scrollers[orientation.ordinal()] = null;
        }
    }

    private void updateScroller(double k, ScrollOrientation orientation) {
        double innerPanelScrolledOffsetK = innerPanelScrolledOffsetK(orientation);
        if (scrollers[orientation.ordinal()] == null)
            newScroller(k, orientation);
        else {
            scrollers[orientation.ordinal()].updateK(k, innerPanelScrolledOffsetK);
        }
        if (innerPanelScrolledOffsetK(orientation) > 1 && (!( isDragging() || flickerAnimationIsActive() ))) {
            doScrollTo(orientation);
        }
        if (this.mouseOvered && scrollers[orientation.ordinal()].getAlpha() == 0)
            mouseOvered();
    }

    private boolean flickerAnimationIsActive() {
        return flickTimeline().isActive();
    }

    private MaskObject newMaskObject(Rectangle maskBounds) {
        return new MaskObject(maskBounds.getX(), maskBounds.getY(), maskBounds.getWidth(), maskBounds.getHeight());
    }

    private void drawBorders(Rectangle bounds) {
        Graphics graphics = Graphics.Factory.newInstance();
        graphics.lineStyle(1, 0x555555, 1);
        graphics.drawRect(bounds.getX(), bounds.getY(), (int)(bounds.getWidth() - 1), (int)(bounds.getHeight() - 1));
        addChild(graphics);
    }

    public static ScrollPanel newInstance(ComponentWithShowSizes innerPanel, int width, int height,
                                          boolean drawBorders, Ease ease) {
        ScrollPanel scrollPanel = newInstance(innerPanel, Rectangle.Factory.newInstance(0, 0, width, height + 1),
                drawBorders, ease);
        return scrollPanel;
    }

    public static ScrollPanel newInstance(ComponentWithShowSizes innerPanel, int width, int height,
                                          boolean drawBorders) {
        return newInstance(innerPanel, width, height, drawBorders, Power1.Static.easeOut());
    }

    public static ScrollPanel newInstance(ComponentWithShowSizes innerPanel, int width, int height) {
        return newInstance(innerPanel, width, height, false);
    }

    public static ScrollPanel newInstance(ComponentWithShowSizes innerPanel) {
        return newInstance(innerPanel, (int) innerPanel.showWidth(), (int) innerPanel.showHeight(), false);
    }

    @Override
    protected void killOtherAnimations() {
        if (scrollers[ScrollOrientation.HORIZONTAL.ordinal()] != null) {
            if (scrollers[ScrollOrientation.HORIZONTAL.ordinal()].resizeTimeline().getProgress() < 1) {
                scrollers[ScrollOrientation.HORIZONTAL.ordinal()].resizeTimeline().progress(1, true);
                scrollers[ScrollOrientation.HORIZONTAL.ordinal()].killAnimations();
            }
        }
        if (scrollers[ScrollOrientation.VERTICAL.ordinal()] != null) {
            if (scrollers[ScrollOrientation.VERTICAL.ordinal()].resizeTimeline().getProgress() < 1) {
                scrollers[ScrollOrientation.VERTICAL.ordinal()].resizeTimeline().progress(1, true);
                scrollers[ScrollOrientation.VERTICAL.ordinal()].killAnimations();
            }
        }
    }

    private void updateHitArea() {
        this.innerPanel.setHitArea(Rectangle.Factory.newInstance(0, 0, innerPanel.showWidth(), innerPanel.showHeight()));
    }

    @Override
    public void updateMaskHeight(double height) {
        if (height > 0) {
            height += 1;
        }
        this.maskObject.drawMask(maskObject.getMaskBounds().getX(), maskObject.getMaskBounds().getY(), maskObject.getMaskBounds().getWidth(), height);
        updateHitArea();
        removeScroller(ScrollOrientation.VERTICAL);
    }

    @Override
    public void updateMaskWidth(double width) {
        this.maskObject.drawMask(maskObject.getMaskBounds().getX(), maskObject.getMaskBounds().getY(), width, maskObject.getMaskBounds().getHeight());
        updateHitArea();
        removeScroller(ScrollOrientation.HORIZONTAL);
    }

    @Override
    public void updateMaskX(double x) {
        this.maskObject.drawMask(x, maskObject.getMaskBounds().getY(), maskObject.getMaskBounds().getWidth(), maskObject.getMaskBounds().getHeight());
        updateHitArea();
        removeScroller(ScrollOrientation.HORIZONTAL);
    }

    @Override
    public void updateMaskY(double y) {
        this.maskObject.drawMask(maskObject.getMaskBounds().getX(), y, maskObject.getMaskBounds().getWidth(), maskObject.getMaskBounds().getHeight());
        updateHitArea();
        removeScroller(ScrollOrientation.VERTICAL);
    }
}
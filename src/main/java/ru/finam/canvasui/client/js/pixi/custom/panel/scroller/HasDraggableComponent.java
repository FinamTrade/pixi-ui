package ru.finam.canvasui.client.js.pixi.custom.panel.scroller;

import ru.finam.canvasui.client.js.pixi.*;
import ru.finam.canvasui.client.js.pixi.custom.panel.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.event.TouchEvent;

import java.util.Date;

/**
 * Created by ikusch on 03.09.2014.
 */
public abstract class HasDraggableComponent extends CustomComponentContainer {

    protected static final int ORIENTATIONS_LENGTH = ScrollOrientation.values().length;
    private static final long DRAG_TIME_TRESHOLD = 300;
    private static final double DRAG_OFFSET_DELTA_MAX = 50;
    private Point touchStartDiff = PointFactory.newInstance(0, 0);
    protected Point dragStartPos;
    private boolean dragStarted;
    private Point offsetDelta;
    protected long dragStartTime;
    protected long dragEndTime;
    protected double[] scrollMaxOffset = new double[ORIENTATIONS_LENGTH];

    protected abstract DisplayObject getMainDragComponent();
    protected abstract double draggingAlpha();
    protected abstract DisplayObjectContainer getDraggableComponent();
    protected abstract double getScrollerEdgeLength();
    protected abstract double dragEndEdge(ScrollOrientation scrollOrientation);
    protected abstract double startEdge(ScrollOrientation scrollOrientation);
    protected abstract double dragTreshold();
    protected abstract void updateDraggableCopmonents(double newOffset, TouchEvent that,
                                                      ScrollOrientation orientation);
    protected abstract double defaultAlpha();
    protected abstract double dragWrapperSize(ScrollOrientation orientation);

    protected HasDraggableComponent(DisplayObjectContainer mainComponent) {
        super(mainComponent);
    }

    protected HasDraggableComponent() {
        this(DisplayObjectContainer.Factory.newInstance());
    }

    protected final native void createDraggable(DisplayObject displayObject) /*-{

        var that = this;

        // use the mousedown and touchstart
        displayObject.mousedown = displayObject.touchstart = function(data)
        {
            that.@ru.finam.canvasui.client.js.pixi.custom.panel.scroller.HasDraggableComponent::touchStart(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/event/TouchEvent;)(data, this);
        };

        // set the events for when the mouse is released or a touch is released
        displayObject.mouseup = displayObject.mouseupoutside =
        displayObject.touchend = displayObject.touchendoutside = function(data)
        {
            that.@ru.finam.canvasui.client.js.pixi.custom.panel.scroller.HasDraggableComponent::touchEnd(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/event/TouchEvent;)(data, this);
        };

        // set the callbacks for when the mouse or a touch moves
        displayObject.mousemove = displayObject.touchmove = function(data)
        {
            that.@ru.finam.canvasui.client.js.pixi.custom.panel.scroller.HasDraggableComponent::touchMove(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/event/TouchEvent;)(data, this);
        }
    }-*/;

    private void calcTouchStartDiff(MouseEvent data, TouchEvent that) {
        for (ScrollOrientation scrollOrientation : ScrollOrientation.values()) {
            double coord1 = scrollOrientation.getOffset(data.getLocalPosition(that.getParent()));
            double coord2 = scrollOrientation.getOffset(this.getDraggableComponent().getPosition());
            scrollOrientation.setOffset(touchStartDiff, coord1 - getScrollerEdgeLength() - ( coord2 -
                    getScrollerEdgeLength() ));
        }
    }

    private Point touchMoveNewCoord(MouseEvent data, TouchEvent that) {
        double newCoordX = that.getData().getLocalPosition(that.getParent()).getX() - this.touchStartDiff.getX();
        double newCoordY = that.getData().getLocalPosition(that.getParent()).getY() - this.touchStartDiff.getY();
        return PointFactory.newInstance(newCoordX, newCoordY);
    }

    protected void touchStart(MouseEvent data, TouchEvent that) {
        data.getOriginalEvent().preventDefault();
        dragStarted = false;
        dragStartTime = new Date().getTime();
        offsetDelta = PointFactory.newInstance(0, 0);
        dragStartPos = PointFactory.newInstance(this.getDraggableComponent().getPosition());
        that.setData(data);
        that.setDragging(true);
        this.setDragging(true);
        this.getMainDragComponent().setAlpha(draggingAlpha());
        calcTouchStartDiff(data, that);
        touchStarted();
    }

    protected void touchStarted() {
    }

    protected void dragStarted(ScrollOrientation orientation, double newOffset, double prevOffset) {
        dragStarted = true;
        orientation.setOffset(offsetDelta, newOffset - prevOffset);
    }

    private void touchMove(ScrollOrientation orientation, Point newCoord, TouchEvent that) {
        double newOffset = orientation.getOffset(newCoord);
        double currentOffset = orientation.getOffset(getDraggableComponent().getPosition());
        if (Math.abs(newOffset - currentOffset) > DRAG_OFFSET_DELTA_MAX) {
            newOffset = currentOffset + (Math.abs(newOffset - currentOffset) / (newOffset - currentOffset)) * DRAG_OFFSET_DELTA_MAX;
        }
        double prevOffset = orientation.getOffset(dragStartPos);
        long currentTime = new Date().getTime();
        if ( (! ( Math.abs(prevOffset - newOffset) < dragTreshold() && (( currentTime - dragEndTime ) >
                DRAG_TIME_TRESHOLD)) )
                || dragStarted) {
            if (!dragStarted) {
                dragStarted(orientation, newOffset, prevOffset);
            }
            newOffset = newOffset - orientation.getOffset(offsetDelta);
            updateDraggableCopmonentsWithWraper(newOffset, that, orientation);
            if ( currentTime - this.dragStartTime > 300 ) {
                this.dragStartTime = currentTime;
                dragStartPos = PointFactory.newInstance(this.getDraggableComponent().getPosition());
            }
        }
    }

    protected double calcStartEdge(ScrollOrientation orientation) {
        return startEdge(orientation);
    }

    protected double calcDragEndEdge(ScrollOrientation orientation) {
        return dragEndEdge(orientation);
    }

    protected void moveAndUpdateDraggableCopmonents(double newOffset, TouchEvent touchEvent,
                                                    ScrollOrientation orientation) {
        updateDraggableCopmonents(newOffset, touchEvent, orientation);
        this.getDraggableComponent().getHeight();//magic
        this.getDraggableComponent().getWidth();//magic
    }

    protected void updateDraggableCopmonentsWithWraper(double newOffset, TouchEvent touchEvent,
                                                     ScrollOrientation orientation) {
        double startEdge = calcStartEdge(orientation);
        double endEdge = calcDragEndEdge(orientation);
        if (newOffset > endEdge) {
            newOffset = endEdge;
        }
        if (newOffset < startEdge) {
            newOffset = startEdge;
        }
        moveAndUpdateDraggableCopmonents(newOffset, touchEvent, orientation);
    }

    public void touchMove(MouseEvent data, TouchEvent that) {
        if(that.getDragging())
        {
            Point newCoord = touchMoveNewCoord(data, that);
            for (ScrollOrientation orientation : ScrollOrientation.values()) {
                touchMove(orientation, newCoord, that);
            }
        }
    }

    public void touchEnd(MouseEvent data, TouchEvent that) {
        this.dragEndTime = new Date().getTime();
        this.getMainDragComponent().setAlpha(defaultAlpha());
        that.setDragging(false);
        this.setDragging(false);
        that.setData(null);
    }

}

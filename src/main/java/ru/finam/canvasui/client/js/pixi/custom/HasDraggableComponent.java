package ru.finam.canvasui.client.js.pixi.custom;

import ru.finam.canvasui.client.js.pixi.*;

/**
 * Created by ikusch on 03.09.2014.
 */
public abstract class HasDraggableComponent extends CustomComponentContainer {

    private Point touchStartDiff = PointFactory.newInstance(0, 0);

    protected abstract DisplayObject getMainDragComponent();
    protected abstract double draggingAlpha();
    protected abstract DisplayObject getDraggableComponent();
    protected abstract double getScrollerEdgeLength();
    protected abstract double dragEndEdge(ScrollOrientation scrollOrientation);
    protected abstract double startEdge(ScrollOrientation scrollOrientation);
    protected abstract void updateDraggableCopmonents(double newOffset, TouchEvent that, double startEdge, double endEdge, ScrollOrientation orientation);
    protected abstract double defaultAlpha();

    protected HasDraggableComponent(DisplayObjectContainer mainComponent) {
        super(mainComponent);
    }

    protected HasDraggableComponent() {
    }

    protected final native void createDraggable(DisplayObject displayObject) /*-{

        var that = this;

        // use the mousedown and touchstart
        displayObject.mousedown = displayObject.touchstart = function(data)
        {
            that.@ru.finam.canvasui.client.js.pixi.custom.Scroller::touchStart(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/TouchEvent;)(data, this);
        };

        // set the events for when the mouse is released or a touch is released
        displayObject.mouseup = displayObject.mouseupoutside =
        displayObject.touchend = displayObject.touchendoutside = function(data)
        {
            that.@ru.finam.canvasui.client.js.pixi.custom.Scroller::touchEnd(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/TouchEvent;)(data, this);
        };

        // set the callbacks for when the mouse or a touch moves
        displayObject.mousemove = displayObject.touchmove = function(data)
        {
            that.@ru.finam.canvasui.client.js.pixi.custom.Scroller::touchMove(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/TouchEvent;)(data, this);
        }
    }-*/;

    protected void touchStart(MouseEvent data, TouchEvent that) {
        data.getOriginalEvent().preventDefault();
        that.setData(data);
        that.setDragging(true);
        this.setDragging(true);
        this.getMainDragComponent().setAlpha(draggingAlpha());
        calcTouchStartDiff(data, that);
    }

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

    public void touchMove(MouseEvent data, TouchEvent that) {
        if(that.getDragging())
        {
            Point newCoord = touchMoveNewCoord(data, that);
            for (ScrollOrientation orientation : ScrollOrientation.values()) {
                double newOffset = orientation.getOffset(newCoord);
                double startEdge = startEdge(orientation);
                double endEdge = dragEndEdge(orientation);
                if (newOffset > endEdge) {
                    newOffset = endEdge;
                }
                if (newOffset < startEdge) {
                    newOffset = startEdge;
                }
                updateDraggableCopmonents(newOffset, that, startEdge, endEdge, orientation);
            }
        }
    }

    public void touchEnd(MouseEvent data, TouchEvent that) {
        this.getMainDragComponent().setAlpha(defaultAlpha());
        that.setDragging(false);
        this.setDragging(false);
        that.setData(null);
    }

}

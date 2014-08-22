package ru.finam.canvasui.client.js.pixi.custom;

import com.google.gwt.core.client.JavaScriptObject;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.pixi.*;

/**
 * Created by ikusch on 19.08.14.
 */
public class Scroller extends CustomComponentContainer {

    public static final int DEFAULT_WIDE = 3;
    public static final int DEFAULT_COLOR = 0x000000;
    public static final double DEFAULT_ALPHA = 0.6;
    public static final double DRAGGING_ALPHA = 0.9;
    public static final double MIN_LENGTH = 15;
    private static final double SCROLLER_EDGE_LENGTH = 4;

    private DisplayObjectContainer scrollerContainer;
    private Orientation orientation;
    private Sprite scrollerMiddle;
    private Sprite scrollerForward;
    private Sprite scrollerTail;
    private double touchStartDiff;
    private double endEdge;
    private double k;
    private double scrollPosition;
    private ScrollCallback scrollCallback;

    public static enum Orientation {
        HORIZONTAL, VERTICAL;
    }

    protected Scroller(double k,
                       double scrollPosition,
                       ScrollCallback scrollCallback, Orientation orientation, double length) {
        super();
        this.scrollPosition = scrollPosition;
        this.orientation = orientation;
        this.scrollCallback = scrollCallback;
        this.k = k;
        setDragging(false);
        addGraphics(length, orientation);
    }

    public void doScrollCallback(double d) {
        scrollCallback.onScroll(d);
    }

    private static Scroller newInstance(double length, double k, ScrollCallback scrollCallback, Orientation orientation) {
        return newInstance(length, k, 0, scrollCallback, orientation);
    }

    private static Scroller newInstance(double length, double k,
                                        double scrollPosition, ScrollCallback scrollCallback, Orientation orientation) {
        return new Scroller(k, scrollPosition, scrollCallback, orientation, length);
    }

    public static Scroller newHorizontalInstance(double width, double k, ScrollCallback scrollCallback) {
        return newInstance(width, k, 0, scrollCallback, Orientation.HORIZONTAL);
    }

    public static Scroller newVerticalInstance(double width, double k, ScrollCallback scrollCallback) {
        return newInstance(width, k, 0, scrollCallback, Orientation.VERTICAL);
    }

    private double touchStartDiff(MouseEvent data, TouchEvent that) {
        double coord1 = 0;
        double coord2 = 0;
        if (this.orientation.equals(Orientation.HORIZONTAL)) {
            coord1 = data.getLocalPosition(that.getParent()).getX();
            coord2 = this.scrollerMiddle.getPosition().getX();
        }
        if (this.orientation.equals(Orientation.VERTICAL)) {
            coord1 = data.getLocalPosition(that.getParent()).getY();
            coord2 = this.scrollerMiddle.getPosition().getY();
        }
        double touchStartDiff = coord1 - SCROLLER_EDGE_LENGTH -
                (coord2 - SCROLLER_EDGE_LENGTH);
        return touchStartDiff;
    }

    public void touchStart(MouseEvent data, TouchEvent that) {
        // stop the default event...
        data.getOriginalEvent().preventDefault();
        // store a reference to the data
        // The reason for this is because of multitouch
        // we want to track the movement of this particular touch
        that.setData(data);
        that.setDragging(true);
        this.scrollerContainer.setAlpha(DRAGGING_ALPHA);
        this.setDragging(true);
        double touchStartDiff = touchStartDiff(data, that);
        this.touchStartDiff = touchStartDiff;
    }

    public void touchEnd(MouseEvent data, TouchEvent that) {
        this.scrollerContainer.setAlpha(DEFAULT_ALPHA);
        that.setDragging(false);
        this.setDragging(false);
        that.setData(null);
    }

    public double touchMoveNewCoord(MouseEvent data, TouchEvent that) {
        double newCoord = 0;
        if (this.orientation.equals(Orientation.HORIZONTAL)) {
            newCoord = that.getData().getLocalPosition(that.getParent()).getX() - this.touchStartDiff;
        }
        if (this.orientation.equals(Orientation.VERTICAL)) {
            newCoord = that.getData().getLocalPosition(that.getParent()).getY() - this.touchStartDiff;
        }
        return newCoord;
    }

    private void updateCoord(double newCoord) {
        updateCoord(newCoord, null);
    }

    public void updateCoordK(double k) {
        double startEdge = SCROLLER_EDGE_LENGTH;
        double newCoord = k * (endEdge - startEdge) + startEdge;
        updateCoord(newCoord);
    }

    private void updateCoord(double newCoord, TouchEvent that) {
        double startEdge = SCROLLER_EDGE_LENGTH;
        if (this.orientation.equals(Orientation.HORIZONTAL)) {
            if (that != null)
                that.getPosition().setX(newCoord);
            this.scrollerForward.getPosition().setX( newCoord - startEdge );
            this.scrollerMiddle.getPosition().setX( newCoord );
            this.scrollerTail.getPosition().setX( newCoord + this.scrollerMiddle.getWidth() );
        }
        if (this.orientation.equals(Orientation.VERTICAL)) {
            if (that != null)
                that.getPosition().setY(newCoord);
            this.scrollerForward.getPosition().setY( newCoord - startEdge );
            this.scrollerMiddle.getPosition().setY( newCoord );
            this.scrollerTail.getPosition().setY( newCoord + this.scrollerMiddle.getHeight() );
        }
    }

    public void touchMove(MouseEvent data, TouchEvent that) {
        if(that.getDragging())
        {
            double newCoord = touchMoveNewCoord(data, that);
            double endEdge = this.endEdge;
            double startEdge = SCROLLER_EDGE_LENGTH;
            if (newCoord > endEdge) {
                newCoord = endEdge;
            }
            if (newCoord < startEdge) {
                newCoord = startEdge;
            }
            updateCoord(newCoord, that);
            double newScrollerPosition = (newCoord - startEdge) / (endEdge - startEdge);
            this.doScrollCallback(newScrollerPosition);
        }
    }

    private final native void createDraggable(Sprite scrollerMiddle) /*-{

        var thisScroller = this;

        // use the mousedown and touchstart
        scrollerMiddle.mousedown = scrollerMiddle.touchstart = function(data)
        {
            thisScroller.@ru.finam.canvasui.client.js.pixi.custom.Scroller::touchStart(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/TouchEvent;)(data, this);
        };

        // set the events for when the mouse is released or a touch is released
        scrollerMiddle.mouseup = scrollerMiddle.mouseupoutside =
            scrollerMiddle.touchend = scrollerMiddle.touchendoutside = function(data)
        {
            thisScroller.@ru.finam.canvasui.client.js.pixi.custom.Scroller::touchEnd(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/TouchEvent;)(data, this);
        };

        // set the callbacks for when the mouse or a touch moves
        scrollerMiddle.mousemove = scrollerMiddle.touchmove = function(data)
        {
            thisScroller.@ru.finam.canvasui.client.js.pixi.custom.Scroller::touchMove(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/TouchEvent;)(data, this);
        }
    }-*/;

    private String getScrollerForwardTexturePath(Orientation orientation) {
        if (orientation.equals(Orientation.HORIZONTAL))
            return "img/scroller/h-scroller-left.png";
        if (orientation.equals(Orientation.VERTICAL))
            return "img/scroller/v-scroller-top.png";
        return "";
    }

    private String getScrollerMiddleTexturePath(Orientation orientation) {
        if (orientation.equals(Orientation.HORIZONTAL))
            return "img/scroller/h-scroller-center.png";
        if (orientation.equals(Orientation.VERTICAL))
            return "img/scroller/v-scroller-center.png";
        return "";
    }

    private String getScrollerTailTexturePath(Orientation orientation) {
        if (orientation.equals(Orientation.HORIZONTAL))
            return "img/scroller/h-scroller-right.png";
        if (orientation.equals(Orientation.VERTICAL))
            return "img/scroller/v-scroller-bottom.png";
        return "";
    }

    private int scrollerMiddleWidth(double sl, Orientation orientation, Sprite scrollerMiddle) {
        if (orientation.equals(Orientation.HORIZONTAL)) {
            return (int) ( sl - 2 * SCROLLER_EDGE_LENGTH );
        }
        if (orientation.equals(Orientation.VERTICAL)) {
            return DEFAULT_WIDE * 2;
        }
        throw new AssertionError();
    }

    private int scrollerMiddleHeight(double sl, Orientation orientation, Sprite scrollerMiddle) {
        if (orientation.equals(Orientation.VERTICAL)) {
            return (int) ( sl - 2 * SCROLLER_EDGE_LENGTH );
        }
        if (orientation.equals(Orientation.HORIZONTAL)) {
            return DEFAULT_WIDE * 2;
        }
        throw new AssertionError();
    }

    private Point scrollerMiddlePosition(Orientation orientation) {
        if (orientation.equals(Orientation.HORIZONTAL)) {
            return PointFactory.newInstance(SCROLLER_EDGE_LENGTH, 0);
        }
        if (orientation.equals(Orientation.VERTICAL)) {
            return PointFactory.newInstance(0, SCROLLER_EDGE_LENGTH);
        }
        throw new AssertionError();
    }

    private Point scrollerTailPosition(double sl, Orientation orientation) {
        if (orientation.equals(Orientation.HORIZONTAL)) {
            return PointFactory.newInstance(( sl - SCROLLER_EDGE_LENGTH ), 0);
        }
        if (orientation.equals(Orientation.VERTICAL)) {
            return PointFactory.newInstance(0, ( sl - SCROLLER_EDGE_LENGTH ));
        }
        throw new AssertionError();
    }

    private final void addGraphics(double length, Orientation orientation) {
        DisplayObjectContainer scrollerContainer = DisplayObjectContainerFactory.newInstance();
        this.scrollerContainer = scrollerContainer;
        Texture textureScrollerForward = TextureFactory.fromImage(getScrollerForwardTexturePath(orientation));
        Sprite scrollerForward = SpriteFactory.newInstance(textureScrollerForward);
        this.scrollerForward = scrollerForward;
        scrollerContainer.addChild(scrollerForward);
        scrollerForward.setPosition(PointFactory.newInstance(0, 0));

        Texture textureScrollerMIddle = TextureFactory.fromImage(getScrollerMiddleTexturePath(orientation));
        Sprite scrollerMiddle = SpriteFactory.newInstance(textureScrollerMIddle);
        this.scrollerMiddle = scrollerMiddle;
        scrollerContainer.addChild(scrollerMiddle);
        double sl = scrollerLength(length);
        scrollerMiddle.setWidth(scrollerMiddleWidth(sl, orientation, scrollerMiddle));
        scrollerMiddle.setHeight(scrollerMiddleHeight(sl, orientation, scrollerMiddle));
        scrollerMiddle.setPosition(scrollerMiddlePosition(orientation));
        scrollerMiddle.setInteractive(true);
        scrollerMiddle.setButtonMode(true);

        Texture textureScrollerTail = TextureFactory.fromImage(getScrollerTailTexturePath(orientation));
        Sprite scrollerTail = SpriteFactory.newInstance(textureScrollerTail);
        this.scrollerTail = scrollerTail;
        scrollerContainer.addChild(scrollerTail);
        scrollerTail.setPosition(scrollerTailPosition(sl, orientation));
        this.endEdge = length - sl + SCROLLER_EDGE_LENGTH;
        createDraggable(this.scrollerMiddle);
        addChild(scrollerContainer);
        scrollerContainer.setAlpha(DEFAULT_ALPHA);

    }

    private double scrollerLength(double lenth) {
        double newLength = lenth * this.k;
        return newLength > MIN_LENGTH ? newLength : MIN_LENGTH;
    }

}

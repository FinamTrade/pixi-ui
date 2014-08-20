package ru.finam.canvasui.client.js.pixi.custom;

import com.google.gwt.core.client.JavaScriptObject;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.pixi.*;

/**
 * Created by ikusch on 19.08.14.
 */
public class Scroller extends CustomComponentContainer {

    public static final int DEFAULT_WIDE = 3;
    public static final int EDGE_WIDE = 4;
    public static final int DEFAULT_COLOR = 0x000000;
    public static final double DEFAULT_ALPHA = 0.6;
    public static final double DRAGGING_ALPHA = 0.9;
    public static final double MIN_LENGTH = 15;
    private static final double SCROLLER_EDGE_LENGTH = 4;

    public static enum Orientation {
        HORIZONTAL, VERTICAL;

        public static Orientation getByOrdinal(int orientation) {
            return values()[orientation];
        }
    }

    protected Scroller(double k,
                       double scrollPosition,
                       JsObject scrollCallback, Orientation orientation) {
        super();
        initComponent(k, scrollPosition, scrollCallback, orientation.ordinal());
        setDragging(false);
    }

    private final native void initComponent(double k,
                                            double scrollPosition,
                                            JsObject scrollCallback,
                                            int orientationId) /*-{
        this.k = k;
        this.scrollPosition = scrollPosition;
        this.scrollCallback = scrollCallback;
        this.orientationId = orientationId;
    }-*/;

    private final native void setScrollerContainer(DisplayObjectContainer scrollerContainer) /*-{
        this.scrollerContainer = scrollerContainer;
    }-*/;

    private final native DisplayObjectContainer getScrollerContainer() /*-{
        return this.scrollerContainer;
    }-*/;

    public final native void setOrientationId(int o) /*-{
        this.orientationId = o;
    }-*/;

    public final native int getOrientationId() /*-{
        return this.orientationId;
    }-*/;

    public final native void setScrollerMiddle(Sprite scrollerMiddle) /*-{
        this.scrollerMiddle = scrollerMiddle;
    }-*/;

    public final native Sprite getScrollerMiddle() /*-{
        return this.scrollerMiddle;
    }-*/;

    public final native void setScrollerForward(Sprite scrollerForward) /*-{
        this.scrollerForward = scrollerForward;
    }-*/;

    public final native Sprite getScrollerForward() /*-{
        return this.scrollerForward;
    }-*/;

    public final native void setScrollerTail(Sprite scrollerTail) /*-{
        this.scrollerTail = scrollerTail;
    }-*/;

    public final native Sprite getScrollerTail() /*-{
        return this.scrollerTail;
    }-*/;

    public final native void setTouchStartDiff(double d) /*-{
        this.touchStartDiff = d;
    }-*/;

    public final native double getTouchStartDiff() /*-{
        return this.touchStartDiff;
    }-*/;

    public final native double getEndEdge() /*-{
        return this.endEdge;
    }-*/;

    public final native void setEndEdge(double d) /*-{
        this.endEdge = d;
    }-*/;

    public final native void doScrollCallback(double d) /*-{
        this.scrollCallback(d);
    }-*/;

    public final void orientation(Orientation orientation) {
        setOrientationId(orientation.ordinal());
    }

    public final Orientation orientation() {
        return Orientation.getByOrdinal(getOrientationId());
    };

    private static Scroller newInstance(double length, double k, JsObject scrollCallback, Orientation orientation) {
        return newInstance(length, k, 0, scrollCallback, orientation);
    }

    private static Scroller newInstance(double length, double k,
                                        double scrollPosition, JsObject scrollCallback, Orientation orientation) {
        Scroller horizonalScroller = new Scroller(k, scrollPosition, scrollCallback, orientation);
        horizonalScroller.addGraphics(length, orientation);
        return horizonalScroller;
    }

    public static Scroller newHorizontalInstance(double width, double k, JsObject scrollCallback) {
        return newInstance(width, k, 0, scrollCallback, Orientation.HORIZONTAL);
    }

    public static Scroller newVerticalInstance(double width, double k, JsObject scrollCallback) {
        return newInstance(width, k, 0, scrollCallback, Orientation.VERTICAL);
    }

    private static double touchStartDiff(MouseEvent data, TouchEvent that, Scroller thisScroller) {
        double coord1 = 0;
        double coord2 = 0;
        if (thisScroller.orientation().equals(Orientation.HORIZONTAL)) {
            coord1 = data.getLocalPosition(that.getParent()).getX();
            coord2 = thisScroller.getScrollerMiddle().getPosition().getX();
        }
        if (thisScroller.orientation().equals(Orientation.VERTICAL)) {
            coord1 = data.getLocalPosition(that.getParent()).getY();
            coord2 = thisScroller.getScrollerMiddle().getPosition().getY();
        }
        double touchStartDiff = coord1 - SCROLLER_EDGE_LENGTH -
                (coord2 - SCROLLER_EDGE_LENGTH);
        return touchStartDiff;
    }

    public final static void touchStart(MouseEvent data, TouchEvent that, Scroller thisScroller) {
        // stop the default event...
        data.getOriginalEvent().preventDefault();
        // store a reference to the data
        // The reason for this is because of multitouch
        // we want to track the movement of this particular touch
        that.setData(data);
        that.setDragging(true);
        thisScroller.getScrollerContainer().setAlpha(DRAGGING_ALPHA);
        thisScroller.setDragging(true);
        double touchStartDiff = touchStartDiff(data, that, thisScroller);
        thisScroller.setTouchStartDiff(touchStartDiff);
    }

    public final static void touchEnd(MouseEvent data, TouchEvent that, Scroller thisScroller) {
        thisScroller.getScrollerContainer().setAlpha(DEFAULT_ALPHA);
        that.setDragging(false);
        thisScroller.setDragging(false);
        that.setData(null);
    }

    public final static double touchMoveNewCoord(MouseEvent data, TouchEvent that, Scroller thisScroller) {
        double newCoord = 0;
        if (thisScroller.orientation().equals(Orientation.HORIZONTAL)) {
            newCoord = that.getData().getLocalPosition(that.getParent()).getX() - thisScroller.getTouchStartDiff();
        }
        if (thisScroller.orientation().equals(Orientation.VERTICAL)) {
            newCoord = that.getData().getLocalPosition(that.getParent()).getY() - thisScroller.getTouchStartDiff();
        }
        return newCoord;
    }

    public final static void touchMoveUpdateCoord(double newCoord, double startEdge, MouseEvent data, TouchEvent that, Scroller thisScroller) {
        if (thisScroller.orientation().equals(Orientation.HORIZONTAL)) {
            that.getPosition().setX(newCoord);
            thisScroller.getScrollerForward().getPosition().setX( newCoord - startEdge );
            thisScroller.getScrollerTail().getPosition().setX( newCoord + thisScroller.getScrollerMiddle().getWidth() );
        }
        if (thisScroller.orientation().equals(Orientation.VERTICAL)) {
            that.getPosition().setY(newCoord);
            thisScroller.getScrollerForward().getPosition().setY( newCoord - startEdge );
            thisScroller.getScrollerTail().getPosition().setY( newCoord + thisScroller.getScrollerMiddle().getHeight() );
        }
    }

    public final static void touchMove(MouseEvent data, TouchEvent that, Scroller thisScroller) {
        //JsConsole.log("touchMove! "+that.isDragging());
        if(that.getDragging())
        {
            double newCoord = touchMoveNewCoord(data, that, thisScroller);
            double endEdge = thisScroller.getEndEdge();
            double startEdge = SCROLLER_EDGE_LENGTH;
            if (newCoord > endEdge) {
                newCoord = endEdge;
            }
            if (newCoord < startEdge) {
                newCoord = startEdge;
            }
            touchMoveUpdateCoord(newCoord, startEdge, data, that, thisScroller);
            double newScrollerPosition = (newCoord - startEdge) / (endEdge - startEdge);
            thisScroller.doScrollCallback(newScrollerPosition);
        }
    }

    private final native void setDraggable() /*-{

        var thisScroller = this;

        // use the mousedown and touchstart
        thisScroller.scrollerMiddle.mousedown = thisScroller.scrollerMiddle.touchstart = function(data)
        {
            @ru.finam.canvasui.client.js.pixi.custom.Scroller::touchStart(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/TouchEvent;Lru/finam/canvasui/client/js/pixi/custom/Scroller;)(data, this, thisScroller);
        };

        // set the events for when the mouse is released or a touch is released
        thisScroller.scrollerMiddle.mouseup = thisScroller.scrollerMiddle.mouseupoutside =
            thisScroller.scrollerMiddle.touchend = thisScroller.scrollerMiddle.touchendoutside = function(data)
        {
            @ru.finam.canvasui.client.js.pixi.custom.Scroller::touchEnd(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/TouchEvent;Lru/finam/canvasui/client/js/pixi/custom/Scroller;)(data, this, thisScroller);
        };

        // set the callbacks for when the mouse or a touch moves
        thisScroller.scrollerMiddle.mousemove = thisScroller.scrollerMiddle.touchmove = function(data)
        {
            @ru.finam.canvasui.client.js.pixi.custom.Scroller::touchMove(Lru/finam/canvasui/client/js/pixi/MouseEvent;Lru/finam/canvasui/client/js/pixi/custom/TouchEvent;Lru/finam/canvasui/client/js/pixi/custom/Scroller;)(data, this, thisScroller);
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
        setScrollerContainer(scrollerContainer);
        Texture textureScrollerForward = TextureFactory.fromImage(getScrollerForwardTexturePath(orientation));
        Sprite scrollerForward = SpriteFactory.newInstance(textureScrollerForward);
        setScrollerForward(scrollerForward);
        scrollerContainer.addChild(scrollerForward);
        scrollerForward.setPosition(PointFactory.newInstance(0, 0));

        Texture textureScrollerMIddle = TextureFactory.fromImage(getScrollerMiddleTexturePath(orientation));
        Sprite scrollerMiddle = SpriteFactory.newInstance(textureScrollerMIddle);
        setScrollerMiddle(scrollerMiddle);
        scrollerContainer.addChild(scrollerMiddle);
        double sl = scrollerLength(length);
        scrollerMiddle.setWidth(scrollerMiddleWidth(sl, orientation, scrollerMiddle));
        scrollerMiddle.setHeight(scrollerMiddleHeight(sl, orientation, scrollerMiddle));
        scrollerMiddle.setPosition(scrollerMiddlePosition(orientation));
        scrollerMiddle.setInteractive(true);
        scrollerMiddle.setButtonMode(true);

        Texture textureScrollerTail = TextureFactory.fromImage(getScrollerTailTexturePath(orientation));
        Sprite scrollerTail = SpriteFactory.newInstance(textureScrollerTail);
        setScrollerTail(scrollerTail);
        scrollerContainer.addChild(scrollerTail);
        scrollerTail.setPosition(scrollerTailPosition(sl, orientation));
        setEndEdge(length - sl + SCROLLER_EDGE_LENGTH);
        setDraggable();
        addChild(scrollerContainer);
        scrollerContainer.setAlpha(DEFAULT_ALPHA);

    }

    private double scrollerLength(double lenth) {
        double newLength = lenth * getK();
        return newLength > MIN_LENGTH ? newLength : MIN_LENGTH;
    }

    public final native double getK() /*-{
        return this.k;
    }-*/;

    public final native double getScrollPosition() /*-{
        return this.scrollPosition;
    }-*/;

    public final native void setK(double tk) /*-{
        this.k = tk;
    }-*/;

    public final native void setScrollPosition(double p) /*-{
        this.scrollPosition = p;
    }-*/;

}

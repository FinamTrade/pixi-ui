package ru.finam.canvasui.client.pixi.custom;

import com.google.gwt.core.client.JavaScriptObject;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.pixi.*;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 11.08.14
 * Time: 20:14
 * To change this template use File | Settings | File Templates.
 */
public class Scroller extends CustomComponentContainer {

    public static final int DEFAULT_WIDE = 3;
    public static final int EDGE_WIDE = 4;
    public static final int DEFAULT_COLOR = 0x000000;
    public static final double DEFAULT_ALPHA = 0.6;
    public static final double DRAGGING_ALPHA = 0.9;
    public static final double MIN_LENGTH = 15;

    public static enum Orientation {
        HORIZONTAL, VERTICAL;

        public static Orientation getByOrdinal(int orientation) {
            return values()[orientation];
        }
    }

    static {
        exportJsObject();
    }

    private static final double SCROLLER_EDGE_LENGTH = 4;

    protected Scroller() {}

    public static native void exportJsObject() /*-{
        $wnd.Scroller = function(k, scrollPosition, scrollCallback, orientation) {
            $wnd.PIXI.DisplayObjectContainer.call(this);
            this.k = k;
            this.scrollPosition = scrollPosition;
            this.scrollCallback = scrollCallback;
            this.dragging = false;
            this.orientation = orientation;
        }
        $wnd.Scroller.constructor = $wnd.Scroller;
        $wnd.Scroller.prototype = Object.create($wnd.PIXI.DisplayObjectContainer.prototype);
    }-*/;

    private native static Scroller newNativeInstance(double k,
                                                              double scrollPosition,
                                                              JavaScriptObject scrollCallback, int orientationId) /*-{
        var scroller = new $wnd.Scroller(k, scrollPosition, scrollCallback, orientationId);
        return scroller;
    }-*/;

    private final native void setScrollerContainer(DisplayObjectContainer scrollerContainer) /*-{
        this.scrollerContainer = scrollerContainer;
    }-*/;

    private final native DisplayObjectContainer getScrollerContainer() /*-{
        return this.scrollerContainer;
    }-*/;

    public final native void setDragging(boolean b) /*-{
        this.dragging = b;
    }-*/;

    public final native boolean isDragging() /*-{
        return this.dragging;
    }-*/;

    public final native void setOrientation(int o) /*-{
        this.orientation = o;
    }-*/;

    public final native int getOrientation() /*-{
        return this.orientation;
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
        setOrientation(orientation.ordinal());
    }

    public final Orientation orientation() {
        return Orientation.getByOrdinal(getOrientation());
    };

    private static Scroller newInstance(double length, double k, JavaScriptObject scrollCallback, Orientation orientation) {
        return newInstance(length, k, 0, scrollCallback, orientation);
    }

    private static Scroller newInstance(double length, double k,
                                                double scrollPosition, JavaScriptObject scrollCallback, Orientation orientation) {
        Scroller horizonalScroller = newNativeInstance(k, scrollPosition, scrollCallback, orientation.ordinal());
        horizonalScroller.addGraphics(length, orientation);
        return horizonalScroller;
    }

    public static Scroller newHorizontalInstance(double width, double k, JavaScriptObject scrollCallback) {
        return newInstance(width, k, 0, scrollCallback, Orientation.HORIZONTAL);
    }

    public static Scroller newVerticalInstance(double width, double k, JavaScriptObject scrollCallback) {
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
        if(that.isDragging())
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
		this.scrollerMiddle.mousedown = this.scrollerMiddle.touchstart = function(data)
		{
            @ru.finam.canvasui.client.pixi.custom.Scroller::touchStart(Lru/finam/canvasui/client/pixi/MouseEvent;Lru/finam/canvasui/client/pixi/custom/TouchEvent;Lru/finam/canvasui/client/pixi/custom/Scroller;)(data, this, thisScroller);
		};

		// set the events for when the mouse is released or a touch is released
        this.scrollerMiddle.mouseup = this.scrollerMiddle.mouseupoutside = this.scrollerMiddle.touchend = this.scrollerMiddle.touchendoutside = function(data)
		{
            @ru.finam.canvasui.client.pixi.custom.Scroller::touchEnd(Lru/finam/canvasui/client/pixi/MouseEvent;Lru/finam/canvasui/client/pixi/custom/TouchEvent;Lru/finam/canvasui/client/pixi/custom/Scroller;)(data, this, thisScroller);
		};

		// set the callbacks for when the mouse or a touch moves
        this.scrollerMiddle.mousemove = this.scrollerMiddle.touchmove = function(data)
		{
            @ru.finam.canvasui.client.pixi.custom.Scroller::touchMove(Lru/finam/canvasui/client/pixi/MouseEvent;Lru/finam/canvasui/client/pixi/custom/TouchEvent;Lru/finam/canvasui/client/pixi/custom/Scroller;)(data, this, thisScroller);
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
            return Point.newInstance(SCROLLER_EDGE_LENGTH, 0);
        }
        if (orientation.equals(Orientation.VERTICAL)) {
            return Point.newInstance(0, SCROLLER_EDGE_LENGTH);
        }
        throw new AssertionError();
    }

    private Point scrollerTailPosition(double sl, Orientation orientation) {
        if (orientation.equals(Orientation.HORIZONTAL)) {
            return Point.newInstance(( sl - SCROLLER_EDGE_LENGTH ), 0);
        }
        if (orientation.equals(Orientation.VERTICAL)) {
            return Point.newInstance(0, ( sl - SCROLLER_EDGE_LENGTH ));
        }
        throw new AssertionError();
    }

    private final void addGraphics(double length, Orientation orientation) {
        DisplayObjectContainer scrollerContainer = DisplayObjectContainer.newInstance();
        setScrollerContainer(scrollerContainer);
        Texture textureScrollerForward = Texture.fromImage(getScrollerForwardTexturePath(orientation));
        Sprite scrollerForward = Sprite.newInstance(textureScrollerForward);
        setScrollerForward(scrollerForward);
        scrollerContainer.addChild(scrollerForward);
        scrollerForward.setPosition(0, 0);

        Texture textureScrollerMIddle = Texture.fromImage(getScrollerMiddleTexturePath(orientation));
        Sprite scrollerMiddle = Sprite.newInstance(textureScrollerMIddle);
        setScrollerMiddle(scrollerMiddle);
        scrollerContainer.addChild(scrollerMiddle);
        double sl = scrollerLength(length);
        scrollerMiddle.setWidth(scrollerMiddleWidth(sl, orientation, scrollerMiddle));
        scrollerMiddle.setHeight(scrollerMiddleHeight(sl, orientation, scrollerMiddle));
        scrollerMiddle.setPosition(scrollerMiddlePosition(orientation));
        scrollerMiddle.setInteractive(true);
        scrollerMiddle.setButtonMode(true);

        Texture textureScrollerTail = Texture.fromImage(getScrollerTailTexturePath(orientation));
        Sprite scrollerTail = Sprite.newInstance(textureScrollerTail);
        setScrollerTail(scrollerTail);
        scrollerContainer.addChild(scrollerTail);
        scrollerTail.setPosition(scrollerTailPosition(sl, orientation));
        setEndEdge(length - sl + SCROLLER_EDGE_LENGTH);
        setDraggable();
        addChild(scrollerContainer);
        scrollerContainer.setAlpha(DEFAULT_ALPHA);
        /*
        double sw = scrollerWidth(width);
        Graphics graphics = Graphics.newInstance();
        graphics.beginFill(0x000000, 1);
        graphics.lineStyle(0, 0x000000, 1);
        graphics.drawRect(EDGE_WIDE, 0, sw - 2 * EDGE_WIDE, DEFAULT_WIDE * 2);
        graphics.drawEllipse(EDGE_WIDE, 0 + DEFAULT_WIDE, 3, DEFAULT_WIDE);
        graphics.drawEllipse(sw - EDGE_WIDE, 0 + DEFAULT_WIDE, 3, DEFAULT_WIDE);
        Sprite scrollerSprite = Sprite.newInstance(graphics.generateTexture());
        addChild(graphics);
        */
        /*
        graphics.drawRect(EDGE_WIDE - 2, 1, 2, DEFAULT_WIDE * 2 - 2 );
        graphics.drawRect(sw - EDGE_WIDE, 1, 2, DEFAULT_WIDE * 2 - 2 );
        graphics.beginFill(0x000000, 0.85 * DEFAULT_ALPHA);
        graphics.lineStyle(0, 0x000000, 0.85 * DEFAULT_ALPHA);
        graphics.drawRect(EDGE_WIDE - 1, 0, 1, 1 );
        graphics.drawRect(EDGE_WIDE - 1, DEFAULT_WIDE * 2 - 1, 1, 1 );

        graphics.drawRect(sw - EDGE_WIDE, 0, 1, 1 );
        graphics.drawRect(sw - EDGE_WIDE, DEFAULT_WIDE * 2 - 1, 1, 1 );

        graphics.drawRect(sw - EDGE_WIDE + 2, 2, 1, 2 );
        graphics.drawRect(EDGE_WIDE - 3, 2, 1, 2 );

        graphics.beginFill(0x000000, 0.33 * DEFAULT_ALPHA);
        graphics.lineStyle(0, 0x000000, 0.33 * DEFAULT_ALPHA);
        */

        /*

        graphics.drawRect(EDGE_WIDE - 2, 0, 1, 1 );
        graphics.drawRect(EDGE_WIDE - 2, DEFAULT_WIDE * 2 - 1, 1, 1 );

        graphics.drawRect(sw - EDGE_WIDE + 1, 0, 1, 1 );
        graphics.drawRect(sw - EDGE_WIDE + 1, DEFAULT_WIDE * 2 - 1, 1, 1 );

        graphics.drawRect(EDGE_WIDE - 3, 1, 1, 1 );
        graphics.drawRect(EDGE_WIDE - 3, DEFAULT_WIDE * 2 - 2, 1, 1 );

        graphics.drawRect(sw - EDGE_WIDE + 2, 1, 1, 1 );
        graphics.drawRect(sw - EDGE_WIDE + 2, DEFAULT_WIDE * 2 - 2, 1, 1 );
        */

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


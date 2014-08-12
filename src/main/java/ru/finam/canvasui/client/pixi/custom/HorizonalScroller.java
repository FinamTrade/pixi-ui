package ru.finam.canvasui.client.pixi.custom;

import com.google.gwt.core.client.Callback;
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
public class HorizonalScroller extends DisplayObjectContainer {

    public static final int DEFAULT_WIDE = 3;
    public static final int DEFAULT_COLOR = 0x000000;
    public static final double DEFAULT_ALPHA = 0.6;
    public static final double DRAGGING_ALPHA = 0.9;
    public static final double MIN_WIDTH = 15;

    static {
        exportJsObject();
    }

    private static final double SCROLLER_EDGE_WIDTH = 4;

    protected HorizonalScroller() {}

    public static native void exportJsObject() /*-{
        $wnd.HorizonalScroller = function(k, scrollPosition, scrollCallback) {
            $wnd.PIXI.DisplayObjectContainer.call(this);
            this.k = k;
            this.scrollPosition = scrollPosition;
            this.scrollCallback = scrollCallback;
            this.draging = false;
        }
        $wnd.HorizonalScroller.constructor = $wnd.HorizonalScroller;
        $wnd.HorizonalScroller.prototype = Object.create($wnd.PIXI.DisplayObjectContainer.prototype);
    }-*/;

    private native static HorizonalScroller newNativeInstance(double k,
                                                              double scrollPosition,
                                                              JavaScriptObject scrollCallback) /*-{
        var scroller = new $wnd.HorizonalScroller(k, scrollPosition, scrollCallback);
        return scroller;
    }-*/;

    public static HorizonalScroller newInstance(double width, double k, JavaScriptObject scrollCallback) {
        return newInstance(width, k, 0, scrollCallback);
    }

    public static HorizonalScroller newInstance(double width, double k,
                                                double scrollPosition, JavaScriptObject scrollCallback) {
        HorizonalScroller horizonalScroller = newNativeInstance(k, scrollPosition, scrollCallback);
        horizonalScroller.addGraphics(width);
        return horizonalScroller;
    }

    private final native void setDragging(DisplayObject sprite,
                                          DisplayObject startSprite,
                                          DisplayObject endSprite,
                                          DisplayObjectContainer scrollerContainer,
                                          double startEdge, double endEdge) /*-{

        var thisScroller = this;
        // use the mousedown and touchstart
		sprite.mousedown = sprite.touchstart = function(data)
		{
			// stop the default event...
			data.originalEvent.preventDefault();
			// store a reference to the data
			// The reason for this is because of multitouch
			// we want to track the movement of this particular touch
			this.data = data;
			scrollerContainer.alpha = @ru.finam.canvasui.client.pixi.custom.HorizonalScroller::DRAGGING_ALPHA;
			this.dragging = true;
			thisScroller.draging = true;
			sprite.startDiff = data.getLocalPosition(this.parent).x - startEdge -
			    (sprite.position.x - startEdge);
		};

		// set the events for when the mouse is released or a touch is released
		sprite.mouseup = sprite.mouseupoutside = sprite.touchend = sprite.touchendoutside = function(data)
		{
			scrollerContainer.alpha = @ru.finam.canvasui.client.pixi.custom.HorizonalScroller::DEFAULT_ALPHA;
			this.dragging = false;
			thisScroller.draging = false;
			// set the interaction data to null
			this.data = null;
		};

		// set the callbacks for when the mouse or a touch moves
		sprite.mousemove = sprite.touchmove = function(data)
		{
			if(this.dragging)
			{
				var newX = this.data.getLocalPosition(this.parent).x - sprite.startDiff;
				if (newX > endEdge) {
				    newX = endEdge;
				}
				if (newX < startEdge) {
				    newX = startEdge;
				}
				this.position.x = newX;
				startSprite.position.x = newX - startEdge;
				endSprite.position.x = newX + sprite.width;
				var newScrollerPosition = (newX - startEdge) / (endEdge - startEdge);
				//thisScroller.srollTarget.position.x = - 200 * newScrollerPosition;
				thisScroller.scrollCallback(newScrollerPosition);
			}
		}
    }-*/;

    private final void addGraphics(double width) {
        DisplayObjectContainer scrollerContainer = DisplayObjectContainer.newInstance();
        Texture textureScrollerLeft = Texture.fromImage("img/scroller/h-scroller-left.png");
        Sprite scrollerLeft = Sprite.newInstance(textureScrollerLeft);
        scrollerContainer.addChild(scrollerLeft);
        scrollerLeft.setPosition(0, 0);
        Texture textureScrollerCenter = Texture.fromImage("img/scroller/h-scroller-center.png");
        Sprite scrollerCenter = Sprite.newInstance(textureScrollerCenter);
        scrollerContainer.addChild(scrollerCenter);
        double sw = scrollerWidth(width);
        scrollerCenter.setWidth((int) ( sw - 2 * SCROLLER_EDGE_WIDTH ) );
        scrollerCenter.setPosition(SCROLLER_EDGE_WIDTH, 0);
        scrollerCenter.setInteractive(true);
        scrollerCenter.setButtonMode(true);
        Texture textureScrollerRight = Texture.fromImage("img/scroller/h-scroller-right.png");
        Sprite scrollerRight = Sprite.newInstance(textureScrollerRight);
        scrollerContainer.addChild(scrollerRight);
        scrollerRight.setPosition(( sw - SCROLLER_EDGE_WIDTH ), 0);
        setDragging(scrollerCenter,
                scrollerLeft,
                scrollerRight,
                scrollerContainer,
                SCROLLER_EDGE_WIDTH,
                (width - sw + SCROLLER_EDGE_WIDTH));
        addChild(scrollerContainer);
        scrollerContainer.setAlpha(DEFAULT_ALPHA);
    }

    private double scrollerWidth(double width) {
        double newWidth = width * getK();
        return newWidth > MIN_WIDTH ? newWidth : MIN_WIDTH;
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


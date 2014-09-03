package ru.finam.canvasui.client.js.pixi.custom;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.JsObject;
import ru.finam.canvasui.client.js.gsap.PropertiesSet;
import ru.finam.canvasui.client.js.gsap.TimelineLite;
import ru.finam.canvasui.client.js.pixi.*;

/**
 * Created by ikusch on 19.08.14.
 */
public class Scroller extends HasDraggableComponent {

    public static final int DEFAULT_WIDE = 3;
    public static final int DEFAULT_COLOR = 0x000000;
    public static final double DEFAULT_ALPHA = 0.6;
    public static final double DRAGGING_ALPHA = 0.9;
    public static final int MIN_LENGTH = 15;
    private static final double SCROLLER_EDGE_LENGTH = 4;
    private static final double RESIZE_ANI_DURATION = 3.8;
    private static final double SCROLL_TOGGLE_DURATION = 1;

    private DisplayObjectContainer scrollerContainer;
    private ScrollOrientation orientation;
    private Sprite scrollerMiddle;
    private Sprite scrollerForward;
    private Sprite scrollerTail;
    private double endEdge;
    private double k;
    private double fullLength;
    private WrappedDouble scrollPosition = new WrappedDouble(0);
    private ScrollCallback scrollCallback;
    private Integer scrollerLength = null;
    private TimelineLite resizeTimeline;
    private TimelineLite moveTimeline;
    private PropertiesSet tScrollerLengthHolder;

    private class WrappedDouble {

        private PropertiesSet propertiesSet = new PropertiesSet();

        private WrappedDouble(double d) {
            setValue(d);
        }

        public double getValue() {
            return propertiesSet.doubleKeyValue("val");
        }

        public void setValue(double d) {
            propertiesSet.addKeyValue("val", d);
        }

        public JsObject jsObject() {
            return propertiesSet.getJsObject();
        }

    }

    protected Scroller(double k,
                       double scrollPosition,
                       ScrollCallback scrollCallback, ScrollOrientation orientation, double length) {
        super();
        this.scrollPosition = new WrappedDouble(scrollPosition);
        this.orientation = orientation;
        this.scrollCallback = scrollCallback;
        this.k = k;
        this.fullLength = length;
        setDragging(false);
        addGraphics();
    }

    public void doScrollCallback(double d) {
        scrollCallback.onScroll(d, this.orientation);
    }

    private static Scroller newInstance(double length, double k, ScrollCallback scrollCallback,
                                        ScrollOrientation orientation) {
        return newInstance(length, k, 0, scrollCallback, orientation);
    }

    private static Scroller newInstance(double length, double k,
                                        double scrollPosition, ScrollCallback scrollCallback,
                                        ScrollOrientation orientation) {
        return new Scroller(k, scrollPosition, scrollCallback, orientation, length);
    }

    public static Scroller newInstance(double width, double k, ScrollCallback scrollCallback,
                                                 double initAlpha, ScrollOrientation orientation) {
        Scroller scroller = newInstance(width, k, 0, scrollCallback, orientation);
        scroller.setAlpha(initAlpha);
        return scroller;
    }

    protected double draggingAlpha() {
        return DRAGGING_ALPHA;
    }

    @Override
    protected DisplayObject getDraggableComponent() {
        return this.scrollerMiddle;
    }

    @Override
    protected double getScrollerEdgeLength() {
        return SCROLLER_EDGE_LENGTH;
    }

    private void updateCoord(double newCoord) {
        updateCoord(newCoord, null);
    }

    public void updateCoordK(double k, boolean immediatly) {
        if (k != this.scrollPosition.getValue()) {
            moveTimeline().kill(null, this.scrollPosition.jsObject());
            if (immediatly)
                this.scrollPosition.setValue(k);
            else {
                moveTimeline().to(this.scrollPosition.jsObject(), RESIZE_ANI_DURATION,
                        new PropertiesSet().addKeyValue("val", k).getJsObject(), null);
            }
        }
        double startEdge = SCROLLER_EDGE_LENGTH;
        double newCoord = k * (endEdge - startEdge) + startEdge;
        updateCoord(newCoord);
    }

    private void updateCoord(double newCoord, TouchEvent that) {
        double startEdge = SCROLLER_EDGE_LENGTH;
        if (this.orientation.equals(ScrollOrientation.HORIZONTAL)) {
            if (that != null)
                that.getPosition().setX(newCoord);
            this.scrollerForward.getPosition().setX( newCoord - startEdge );
            this.scrollerMiddle.getPosition().setX( newCoord );
            this.scrollerTail.getPosition().setX( newCoord + this.scrollerMiddle.getWidth() );
        }
        if (this.orientation.equals(ScrollOrientation.VERTICAL)) {
            if (that != null)
                that.getPosition().setY(newCoord);
            this.scrollerForward.getPosition().setY(newCoord - startEdge);
            this.scrollerMiddle.getPosition().setY( newCoord );
            this.scrollerTail.getPosition().setY(newCoord + this.scrollerMiddle.getHeight());
        }
    }

    protected void updateDraggableCopmonents(double newOffset, TouchEvent that, double startEdge, double endEdge,
                                           ScrollOrientation orientation) {
        if (orientation.equals(this.orientation)) {
            moveTimeline().kill(null, this.scrollPosition.jsObject());
            updateCoord(newOffset, that);
            this.scrollPosition.setValue( (newOffset - startEdge) / (endEdge - startEdge) );
            this.doScrollCallback(scrollPosition.getValue());
        }
    }

    @Override
    protected double defaultAlpha() {
        return DEFAULT_ALPHA;
    }

    protected double dragEndEdge(ScrollOrientation scrollOrientation) {
        return this.endEdge;
    }

    protected double startEdge(ScrollOrientation scrollOrientation) {
        return SCROLLER_EDGE_LENGTH;
    }

    private String getScrollerForwardTexturePath(ScrollOrientation orientation) {
        if (orientation.equals(ScrollOrientation.HORIZONTAL))
            return "img/scroller/h-scroller-left.png";
        if (orientation.equals(ScrollOrientation.VERTICAL))
            return "img/scroller/v-scroller-top.png";
        return "";
    }

    private String getScrollerMiddleTexturePath(ScrollOrientation orientation) {
        if (orientation.equals(ScrollOrientation.HORIZONTAL))
            return "img/scroller/h-scroller-center.png";
        if (orientation.equals(ScrollOrientation.VERTICAL))
            return "img/scroller/v-scroller-center.png";
        return "";
    }

    private String getScrollerTailTexturePath(ScrollOrientation orientation) {
        if (orientation.equals(ScrollOrientation.HORIZONTAL))
            return "img/scroller/h-scroller-right.png";
        if (orientation.equals(ScrollOrientation.VERTICAL))
            return "img/scroller/v-scroller-bottom.png";
        return "";
    }

    private int scrollerMiddleWidth(ScrollOrientation orientation, Sprite scrollerMiddle) {
        if (orientation.equals(ScrollOrientation.HORIZONTAL)) {
            return (int) ( scrollerLength - 2 * SCROLLER_EDGE_LENGTH );
        }
        if (orientation.equals(ScrollOrientation.VERTICAL)) {
            return DEFAULT_WIDE * 2;
        }
        throw new AssertionError();
    }

    private int scrollerMiddleHeight(ScrollOrientation orientation, Sprite scrollerMiddle) {
        if (orientation.equals(ScrollOrientation.VERTICAL)) {
            return (int) ( scrollerLength - 2 * SCROLLER_EDGE_LENGTH );
        }
        if (orientation.equals(ScrollOrientation.HORIZONTAL)) {
            return DEFAULT_WIDE * 2;
        }
        throw new AssertionError();
    }

    private Point scrollerMiddlePosition(ScrollOrientation orientation) {
        if (orientation.equals(ScrollOrientation.HORIZONTAL)) {
            return PointFactory.newInstance(SCROLLER_EDGE_LENGTH + scrollerForward.getPosition().getX(), 0);
        }
        if (orientation.equals(ScrollOrientation.VERTICAL)) {
            return PointFactory.newInstance(0, SCROLLER_EDGE_LENGTH + scrollerForward.getPosition().getY());
        }
        throw new AssertionError();
    }

    private Point scrollerTailPosition(ScrollOrientation orientation) {
        if (orientation.equals(ScrollOrientation.HORIZONTAL)) {
            return PointFactory.newInstance(( scrollerLength - SCROLLER_EDGE_LENGTH + scrollerForward.getPosition().getX() ), 0);
        }
        if (orientation.equals(ScrollOrientation.VERTICAL)) {
            return PointFactory.newInstance(0, ( scrollerLength - SCROLLER_EDGE_LENGTH + scrollerForward.getPosition().getY() ));
        }
        throw new AssertionError();
    }

    public void updateK(double k) {
        this.k = k;
        updateScrollerSize();
    }

    private final native JsObject onRepeat(Scroller inst) /*-{
        return function() {
            inst.@ru.finam.canvasui.client.js.pixi.custom.Scroller::updateScrollerSizeWithTval()();
        };
    }-*/;

    private void animatedUpdateScrollerSize() {
        tScrollerLengthHolder.addKeyValue("val", scrollerLength);
        resizeTimeline().kill(null, tScrollerLengthHolder.getJsObject());
        resizeTimeline().duration(RESIZE_ANI_DURATION);
        resizeTimeline().totalDuration(RESIZE_ANI_DURATION);
        resizeTimeline().delay(0);
        resizeTimeline().to(tScrollerLengthHolder.getJsObject(), RESIZE_ANI_DURATION, new PropertiesSet().addKeyValue("val",
                scrollerLength(this.fullLength)).getJsObject(), null);
    }

    private void updateScrollerSizeWithTval() {
        updateScrollerSize((int) tScrollerLengthHolder.doubleKeyValue("val"));
    }

    private void updateScrollerSize() {
        if (scrollerLength == null)
            updateScrollerSize(scrollerLength(this.fullLength));
        else
            animatedUpdateScrollerSize();
    }

    private void updateScrollerSize(int scrollerLength) {
        this.scrollerLength = scrollerLength;
        scrollerMiddle.setWidth(scrollerMiddleWidth(orientation, scrollerMiddle));
        scrollerMiddle.setHeight(scrollerMiddleHeight(orientation, scrollerMiddle));
        scrollerMiddle.setPosition(scrollerMiddlePosition(orientation));
        scrollerTail.setPosition(scrollerTailPosition(orientation));
        scrollerMiddle.setInteractive(true);
        scrollerMiddle.setButtonMode(true);
        this.endEdge = this.fullLength - scrollerLength + SCROLLER_EDGE_LENGTH;
        updateCoordK(this.scrollPosition.getValue(), false);
    }

    private final void addGraphics() {
        tScrollerLengthHolder = new PropertiesSet();
        tScrollerLengthHolder.addKeyValue("val", 0);

        this.scrollerContainer = DisplayObjectContainer.Factory.newInstance();
        Texture textureScrollerForward = TextureFactory.fromImage(getScrollerForwardTexturePath(orientation));
        scrollerForward = SpriteFactory.newInstance(textureScrollerForward);
        scrollerContainer.addChild(scrollerForward);
        scrollerForward.setPosition(PointFactory.newInstance(0, 0));

        Texture textureScrollerMIddle = TextureFactory.fromImage(getScrollerMiddleTexturePath(orientation));
        scrollerMiddle = SpriteFactory.newInstance(textureScrollerMIddle);
        scrollerContainer.addChild(scrollerMiddle);

        Texture textureScrollerTail = TextureFactory.fromImage(getScrollerTailTexturePath(orientation));
        scrollerTail = SpriteFactory.newInstance(textureScrollerTail);
        scrollerContainer.addChild(scrollerTail);

        updateScrollerSize();

        createDraggable(this.scrollerMiddle);
        addChild(scrollerContainer);
        scrollerContainer.setAlpha(DEFAULT_ALPHA);

        resizeTimeline().eventCallback("onUpdate", onRepeat(this), null, null);

    }

    private void onRemoveAnimationComplete() {
        this.getParent().removeChild(this.getMainComponent());
    }

    private final native JsObject onRemoveAnimationComplete(Scroller inst) /*-{
        return function() {
            inst.@ru.finam.canvasui.client.js.pixi.custom.Scroller::onRemoveAnimationComplete()();
        };
    }-*/;

    public void animatedHide() {
        animatedAlphaChange(0);
    }

    public void animatedRemove() {
        timeline().eventCallback("onComplete", onRemoveAnimationComplete(this), null, null);
        animatedHide();
    }

    public void animatedShow() {
        animatedAlphaChange(1);
    }

    public void animatedAlphaChange(double newAlpha) {
        timeline().kill(null, getMainComponent());
        timeline().to(getMainComponent(), SCROLL_TOGGLE_DURATION,
                new PropertiesSet().addKeyValue("alpha", newAlpha).getJsObject(), null);
    }

    public TimelineLite resizeTimeline() {
        if (resizeTimeline == null)
            resizeTimeline = TimelineLite.Factory.newInstance();
        return resizeTimeline;
    }

    public TimelineLite moveTimeline() {
        if (moveTimeline == null)
            moveTimeline = TimelineLite.Factory.newInstance();
        return moveTimeline;
    }

    private int scrollerLength(double lenth) {
        int newLength = (int) (lenth * this.k);
        return newLength > MIN_LENGTH ? newLength : MIN_LENGTH;
    }

    public double getK() {
        return k;
    }

    public double getScrollPosition() {
        return scrollPosition.getValue();
    }

    @Override
    protected DisplayObject getMainDragComponent() {
        return this.scrollerContainer;
    }

}

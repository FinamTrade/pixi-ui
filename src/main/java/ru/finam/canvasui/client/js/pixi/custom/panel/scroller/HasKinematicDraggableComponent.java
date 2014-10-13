package ru.finam.canvasui.client.js.pixi.custom.panel.scroller;

import ru.finam.canvasui.client.js.JsObject;
import ru.finam.canvasui.client.js.gsap.PropertiesSet;
import ru.finam.canvasui.client.js.gsap.TimelineLite;
import ru.finam.canvasui.client.js.gsap.easing.Ease;
import ru.finam.canvasui.client.js.gsap.easing.Quint;
import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.js.pixi.MouseEvent;
import ru.finam.canvasui.client.js.pixi.Point;
import ru.finam.canvasui.client.js.pixi.PointFactory;
import ru.finam.canvasui.client.js.pixi.custom.event.TouchEvent;

/**
 * Created by ikusch on 05.09.2014.
 */
abstract class HasKinematicDraggableComponent extends HasDraggableComponent {

    private static final double FLICK_K = 1.1;
    private static final double FLICK_WRAPPER_K = 3;
    private static final double WRAPPER_RETURN_ANIMATION_DURATION = 1;
    private static final double ANIMATION_DURATION_MAX = 3;
    private static final long DRAG_MAX_DURATION_TRESHOLD = 300;
    //private static final double DELTA_OFFSET_TRESHOLD = 40;
    private static final double DELTA_OFFSET_TRESHOLD = 80;
    /**
     * To aviod high amplitude jittering
     */
    private static final int DRAG_MAX_ALLOWED = 200;
    /**
     * To aviod high amplitude jittering
     */
    private static final double MAX_ALLOWED_SPEED = 2.9;
    /**
     * To aviod high amplitude jittering
     */
    private static final long DRAG_MIN_DURATION_TRESHOLD = 50;

    private TimelineLite flickTimeline;
    private Point tPositionHolder = PointFactory.newInstance(0, 0);
    private Ease ease = Quint.Static.easeOut();

    private class FlickCalcResult {

        private FlickCalcResult(double duration, double destination) {
            this.duration = duration;
            this.destination = destination;
        }

        private double duration;
        private double destination;
    }

    private class FlickDesinationPoints {
        private FlickDesinationPoints(Point pointVal1, Point pointVal2, double duration) {
            this.pointVal1 = pointVal1;
            this.pointVal2 = pointVal2;
            this.duration = duration;
        }
        private double duration;
        private Point pointVal1;
        private Point pointVal2;
    }

    protected HasKinematicDraggableComponent(DisplayObjectContainer mainComponent, Ease ease) {
        this(mainComponent);
        this.ease = ease;
    }

    protected HasKinematicDraggableComponent(DisplayObjectContainer mainComponent) {
        super(mainComponent);
        flickTimeline().eventCallback("onUpdate", onRepeat(this), null, null);
        flickTimeline().eventCallback("onComplete", flickComplete(this), null, null);
    }

    protected void dragStarted(ScrollOrientation orientation, double newOffset, double prevOffset) {
        killFlickAnimation();
        super.dragStarted(orientation, newOffset, prevOffset);
    }

    @Override
    protected void touchStarted() {
        killFlickAnimation();
    }

    public void killFlickAnimation() {
        flickTimeline().kill(null, tPositionHolder);
        for (ScrollOrientation orientation : ScrollOrientation.values()) {
            moveAndUpdateDraggableCopmonents(orientation.getOffset(getDraggableComponent().getPosition()), null, orientation);
        }
    }

    public void onFlickComplete() {
    }

    private final native JsObject onRepeat(HasDraggableComponent inst) /*-{
        return function() {
            inst.@ru.finam.canvasui.client.js.pixi.custom.panel.scroller.HasKinematicDraggableComponent::flickframe()();
        };
    }-*/;

    private final native JsObject flickComplete(HasDraggableComponent inst) /*-{
        return function() {
            inst.@ru.finam.canvasui.client.js.pixi.custom.panel.scroller.HasKinematicDraggableComponent::onFlickComplete()();
        };
    }-*/;

    private void flickframe() {
        updateDraggableCopmonentsWithWraper(tPositionHolder.getY(), null, ScrollOrientation.VERTICAL);
        updateDraggableCopmonentsWithWraper(tPositionHolder.getX(), null, ScrollOrientation.HORIZONTAL);
    }

    protected void updateDraggableCopmonentsWithWraper(double newOffset, TouchEvent touchEvent,
                                                       ScrollOrientation orientation) {
        if (flickTimeline().isActive())
            killOtherAnimations();
        super.updateDraggableCopmonentsWithWraper(newOffset, touchEvent, orientation);
    }

    protected abstract void killOtherAnimations();

    @Override
    protected double calcStartEdge(ScrollOrientation orientation) {
        return startEdge(orientation) - dragWrapperSize(orientation);
    }

    @Override
    protected double calcDragEndEdge(ScrollOrientation orientation) {
        return dragEndEdge(orientation) + dragWrapperSize(orientation);
    }

    public TimelineLite flickTimeline() {
        if (flickTimeline == null)
            flickTimeline = TimelineLite.Factory.newInstance();
        return flickTimeline;
    }

    private FlickCalcResult calculateFlick(double currentOffset, double startOffset, double time, double lowerMargin,
                                           Double wrapperSize,
                                           Double deceleration) {
        double distance = currentOffset - startOffset;
        double speed = Math.abs(distance) / time;
        double destination = 0;
        double duration = 0;
        if (speed > MAX_ALLOWED_SPEED) {
            speed = MAX_ALLOWED_SPEED;
        }

        if (deceleration == null)
            deceleration = 0.0006;

        destination = currentOffset + ( speed * speed ) / ( 2 * deceleration ) * ( distance < 0 ? -1 : 1 );
        duration = speed / deceleration;

        if ( destination < lowerMargin ) {
            if (wrapperSize != null)
                destination = lowerMargin - ( wrapperSize / 2.5 * ( speed / 8 ) );
            else
                destination = lowerMargin;
            distance = Math.abs(destination - currentOffset);
            duration = distance / speed;
        } else if ( destination > 0 ) {
            if (wrapperSize != null)
                destination = wrapperSize / 2.5 * ( speed / 8 );
            else
                destination = 0;
            distance = Math.abs(currentOffset) + destination;
            duration = distance / speed;
        }

        return new FlickCalcResult(duration, destination);
    }

    private FlickDesinationPoints flickDesinationPoints(Point releasePoint, long dragDuration) {

        double flickDestinationOffset[] = new double[ScrollOrientation.values().length];
        double totalDurations[] = new double[ScrollOrientation.values().length];
        FlickCalcResult[] flicks = new FlickCalcResult[ScrollOrientation.values().length];

        for (ScrollOrientation orientation : ScrollOrientation.values()) {
            double releaseOffset = orientation.getOffset(releasePoint);
            double dragStartOffset = orientation.getOffset(dragStartPos);
            flicks[orientation.ordinal()] = calculateFlick(releaseOffset, dragStartOffset,
                    dragDuration, scrollMaxOffset[orientation.ordinal()],
                    dragWrapperSize(orientation) * FLICK_WRAPPER_K, null);

            double highDestinationOffset = dragEndEdge(orientation) + dragWrapperSize(orientation);
            double lowDestinationOffset = startEdge(orientation) - dragWrapperSize(orientation);
            flickDestinationOffset[orientation.ordinal()] = flicks[orientation.ordinal()].destination;
            if (flickDestinationOffset[orientation.ordinal()] > highDestinationOffset) {
                flickDestinationOffset[orientation.ordinal()] = highDestinationOffset;
            }
            if (flickDestinationOffset[orientation.ordinal()] < lowDestinationOffset) {
                flickDestinationOffset[orientation.ordinal()] = lowDestinationOffset;
            }
            if (releaseOffset < startEdge(orientation) &&
                    flickDestinationOffset[orientation.ordinal()] > releaseOffset) {
                flickDestinationOffset[orientation.ordinal()] = startEdge(orientation);
            }
            if (releaseOffset > dragEndEdge(orientation) &&
                    flickDestinationOffset[orientation.ordinal()] < (releaseOffset)) {
                flickDestinationOffset[orientation.ordinal()] = dragEndEdge(orientation);
            }
            totalDurations[orientation.ordinal()] = flicks[orientation.ordinal()].duration * FLICK_K / 1000;
        }

        Point destinationPoint1 = PointFactory.newInstance(
                flickDestinationOffset[ScrollOrientation.HORIZONTAL.ordinal()],
                flickDestinationOffset[ScrollOrientation.VERTICAL.ordinal()]
        );

        double totalDuration = Math.max(totalDurations[ScrollOrientation.HORIZONTAL.ordinal()],
            totalDurations[ScrollOrientation.VERTICAL.ordinal()]);
        if (totalDuration > ANIMATION_DURATION_MAX) {
            totalDuration = ANIMATION_DURATION_MAX;
        }

        Point destinationPoint2 = PointFactory.newInstance(destinationPoint1.getX(), destinationPoint1.getY());

        for (ScrollOrientation orientation : ScrollOrientation.values()) {

            if (flicks[orientation.ordinal()].destination > dragEndEdge(orientation)) {
                orientation.setOffset(destinationPoint2, dragEndEdge(orientation));
            }
            if (flicks[orientation.ordinal()].destination < startEdge(orientation)) {
                orientation.setOffset(destinationPoint2, startEdge(orientation));
            }
        }

        return new FlickDesinationPoints(destinationPoint1, destinationPoint2, totalDuration);
    }

    private void startFlick(Point releasePoint, long dragDuration) {
        FlickDesinationPoints flickDesinationPoints = flickDesinationPoints(releasePoint, dragDuration);
        double totalDuration = flickDesinationPoints.duration;
        Point flickDestination1 = flickDesinationPoints.pointVal1;
        Point flickDestination2 = flickDesinationPoints.pointVal2;

        flickTimeline().paused(true);
        killFlickAnimation();
        flickTimeline().delay(0);
        flickTimeline().paused(false);
        flickTimeline().to(tPositionHolder, totalDuration,
                new PropertiesSet().addKeyValue("x", flickDestination1.getX())
                        .addKeyValue("y", flickDestination1.getY())
                        .addKeyValue("ease", this.ease)
                        .getJsObject(),
                null);

        if (!(flickDestination1.getX() == flickDestination2.getX() && flickDestination1.getY() == flickDestination2.getY())) {
            flickTimeline().to(tPositionHolder, WRAPPER_RETURN_ANIMATION_DURATION,
                    new PropertiesSet().addKeyValue("y", flickDestination2.getY())
                            .addKeyValue("x", flickDestination2.getX())
                            .addKeyValue("ease", this.ease)
                            .getJsObject(),
                    null);
        }
    }

    private void calcAndStartFlick() {
        long dragDuration = this.dragEndTime - this.dragStartTime;

        Point releasePoint = getDraggableComponent().getPosition();
        double releaseY = releasePoint.getY();
        double releaseX = releasePoint.getX();
        tPositionHolder.setX(releaseX);
        tPositionHolder.setY(releaseY);

        if (dragStartPos == null)
            return;

        double dY = (releaseY - dragStartPos.getY());
        double dX = (releaseX - dragStartPos.getX());
        if (Math.abs(dX) > DRAG_MAX_ALLOWED) {
            dX = ( dX / Math.abs(dX) ) * DRAG_MAX_ALLOWED;
        }
        if (Math.abs(dY) > DRAG_MAX_ALLOWED) {
            dY = ( dY / Math.abs(dY) ) * DRAG_MAX_ALLOWED;
        }

        if (
                (
                        (dragDuration < DRAG_MAX_DURATION_TRESHOLD) &&
                        (dragDuration > DRAG_MIN_DURATION_TRESHOLD) &&
                        ((Math.abs(dX) > DELTA_OFFSET_TRESHOLD ) || (Math.abs(dY) > DELTA_OFFSET_TRESHOLD))
                )
                || outOfBounds(releasePoint)
            ) {
            startFlick(releasePoint, dragDuration);
        }

    }

    private boolean outOfBounds(Point releasePoint) {
        boolean b = false;
        for (ScrollOrientation orientation : ScrollOrientation.values()) {

            b = b || (orientation.getOffset(releasePoint) > dragEndEdge(orientation)) ||
            (orientation.getOffset(releasePoint) < startEdge(orientation));

        }
        return b;
    }

    public void touchEnd(MouseEvent data, TouchEvent that) {
        super.touchEnd(data, that);
        calcAndStartFlick();
    }

    public Ease getEase() {
        return ease;
    }

    public void setEase(Ease ease) {
        this.ease = ease;
    }
}

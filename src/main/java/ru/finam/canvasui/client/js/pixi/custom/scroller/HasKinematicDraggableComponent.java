package ru.finam.canvasui.client.js.pixi.custom.scroller;

import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.JsObject;
import ru.finam.canvasui.client.js.gsap.PropertiesSet;
import ru.finam.canvasui.client.js.gsap.TimelineLite;
import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.js.pixi.MouseEvent;
import ru.finam.canvasui.client.js.pixi.Point;
import ru.finam.canvasui.client.js.pixi.PointFactory;
import ru.finam.canvasui.client.js.pixi.custom.TouchEvent;

import java.util.Date;

/**
 * Created by ikusch on 05.09.2014.
 */
abstract class HasKinematicDraggableComponent extends HasDraggableComponent {

    private static final double FLICK_K = 1.1;
    private static final double FLICK_WRAPPER_K = 3;
    private static final double WRAPPER_RETURN_ANIMATION_DURATION = 1;
    private static final double ANIMATION_DURATION_MAX = 3;

    private TimelineLite flickTimeline;
    private Point tPositionHolder = PointFactory.newInstance(0, 0);

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

    protected HasKinematicDraggableComponent(DisplayObjectContainer mainComponent) {
        super(mainComponent);
        flickTimeline().eventCallback("onUpdate", onRepeat(this), null, null);
    }

    protected void dragStarted(ScrollOrientation orientation, double newOffset, double prevOffset) {
        flickTimeline().kill(null, tPositionHolder);
        super.dragStarted(orientation, newOffset, prevOffset);
    }

    private final native JsObject onRepeat(HasDraggableComponent inst) /*-{
        return function() {
            inst.@ru.finam.canvasui.client.js.pixi.custom.scroller.HasKinematicDraggableComponent::flickframe()();
        };
    }-*/;

    private void flickframe() {
        updateDraggableCopmonentsWithWraper(tPositionHolder.getY() * scrollMaxOffset[ScrollOrientation.VERTICAL.ordinal()], null, ScrollOrientation.VERTICAL);
        updateDraggableCopmonentsWithWraper(tPositionHolder.getX() * scrollMaxOffset[ScrollOrientation.HORIZONTAL.ordinal()], null, ScrollOrientation.HORIZONTAL);
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

    private void startFlick() {
        long dragDuration = new Date().getTime() - this.dragStartTime;

        Point releasePoint = getDraggableComponent().getPosition();
        double releaseY = releasePoint.getY();
        double releaseX = releasePoint.getX();
        double hK = 0;
        if (scrollMaxOffset[ScrollOrientation.HORIZONTAL.ordinal()] != 0) {
            hK = 1 / scrollMaxOffset[ScrollOrientation.HORIZONTAL.ordinal()];
        }
        double vK = 0;
        if (scrollMaxOffset[ScrollOrientation.VERTICAL.ordinal()] != 0) {
            vK = 1 / scrollMaxOffset[ScrollOrientation.VERTICAL.ordinal()];
        }
        tPositionHolder.setX(releaseX * hK);
        tPositionHolder.setY(releaseY * vK);

        FlickDesinationPoints flickDesinationPoints = flickDesinationPoints(releasePoint, dragDuration);
        double totalDuration = flickDesinationPoints.duration;
        Point flickDestination1 = flickDesinationPoints.pointVal1;
        Point flickDestination2 = flickDesinationPoints.pointVal2;

        flickTimeline().paused(true);
        flickTimeline().kill(null, tPositionHolder);
        flickTimeline().delay(0);
        flickTimeline().paused(false);
        flickTimeline().to(tPositionHolder, totalDuration,
                new PropertiesSet().addKeyValue("x", flickDestination1.getX() * hK)
                        .addKeyValue("y", flickDestination1.getY() * vK).getJsObject(),
                null);

        if (!(flickDestination1.getX() == flickDestination2.getX() && flickDestination1.getY() == flickDestination2.getY())) {
            flickTimeline().to(tPositionHolder, WRAPPER_RETURN_ANIMATION_DURATION,
                    new PropertiesSet().addKeyValue("y", flickDestination2.getY() * vK)
                            .addKeyValue("x", flickDestination2.getX() * hK).getJsObject(),
                    null);
        }

    }

    public void touchEnd(MouseEvent data, TouchEvent that) {
        super.touchEnd(data, that);
        startFlick();
    }

}

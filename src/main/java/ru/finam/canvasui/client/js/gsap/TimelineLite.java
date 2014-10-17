package ru.finam.canvasui.client.js.gsap;

import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.Array;
import ru.finam.canvasui.client.js.JsObject;

/**
 * Created by ikusch on 22.08.14.
 */
@JsType(prototype = "$wnd.TimelineLite")
public interface TimelineLite extends SimpleTimeline {

    public static class Static {

        /**
         * [static] Seamlessly transfers all tweens, timelines, and [optionally] delayed calls from the root timeline
         * into a new TimelineLite so that you can perform advanced tasks on a seemingly global basis without affecting
         * tweens/timelines that you create after the export.
         *
         * @param vars
         * @param omitDelayedCalls
         * @return
         */
        public static final native TimelineLite exportRoot(JsObject vars, boolean omitDelayedCalls) /*-{
            return new $wnd.TimelineLite.exportRoot(vars, omitDelayedCalls);
        }-*/;

    }

    /**
     *
     * [override] Adds a tween, timeline, callback, or label (or an array of them) to the timeline.
     *
     * @param child - TweenLite, TweenMax, TimelineLite, or TimelineMax instance to insert
     *
     * @param position - (default = +=0) — The position at which the tween/timeline should be inserted
     *                 which can be expressed as a number (for an absolute time as seconds or frames for
     *                 frames-based timelines) or a string, using "+=" or "-=" prefix to indicate a relative
     *                 value (relative to the END of the timeline). For example, myTimeline.insert(myTween, 3)
     *                 would insert myTween 3 seconds into the timeline.
     *
     * @param align - (default = normal) — Determines how the tweens/timelines/callbacks/labels will be aligned
     *              in relation to each other before getting inserted. Options are: "sequence" (aligns them
     *              one-after-the-other in a sequence), "start" (aligns the start times of all of the objects (ignoring delays)),
     *              and "normal" (aligns the start times of all the tweens (honoring delays)). The default is "normal".
     *
     * @param stagger - (default = 0) — Staggers the inserted objects by a set amount of time (in seconds) (or in frames
     *                for frames-based timelines). For example, if the stagger value is 0.5 and the "align" parameter is
     *                set to "start", the second one will start 0.5 seconds after the first one starts, then 0.5 seconds
     *                later the third one will start, etc. If the align property is "sequence", there would be 0.5 seconds
     *                added between each tween. Default is 0.
     *
     * @return
     */
    TimelineLite add(JsObject child, JsObject position, String align, double stagger);

    /**
     *
     * Adds a label to the timeline, making it easy to mark important positions/times.
     *
     * @param label
     * @param position
     * @return
     */
    TimelineLite addLabel(String label, JsObject position);

    /**
     *
     * Inserts a special callback that pauses playback of the timeline at a particular time or label.
     *
     * @param position
     * @param callback
     * @param params
     * @param scope
     * @return
     */
    TimelineLite addPause(JsObject position, JsObject callback, Array<JsObject> params, JsObject scope);

    /**
     *
     * Adds a callback to the end of the timeline (or elsewhere using the "position" parameter) -
     * this is a convenience method that accomplishes exactly the same thing as
     * add( TweenLite.delayedCall(...) ) but with less code.
     *
     * @param callback
     * @param params
     * @param scope
     * @param position
     * @return
     */
    TimelineLite call(JsObject callback, Array<JsObject> params, JsObject scope, JsObject position);

    /**
     *
     * Empties the timeline of all tweens, timelines, and callbacks (and optionally labels too).
     *
     * @param labels
     * @return
     */
    TimelineLite clear(boolean labels);

    /**
     * [override] adjusts the timeline's timeScale to fit it within the specified duration.
     *
     * @param d
     * @return
     */
    TimelineLite duration(double d);

    /**
     * Adds a TweenLite.from() tween to the end of the timeline (or elsewhere using the "position" parameter) -
     * this is a convenience method that accomplishes exactly the same thing as add( TweenLite.from(...) )
     * but with less code.
     *
     * @param object
     * @param duration
     * @param vars
     * @return
     */
    TimelineLite from(JsObject object, double duration, JsObject vars);

    /**
     * Adds a TweenLite.fromTo() tween to the end of the timeline - this is a convenience method that accomplishes
     * exactly the same thing as add( TweenLite.fromTo(...) ) but with less code.
     *
     * @param object
     * @param duration
     * @param fromVars
     * @param toVars
     * @param position
     * @return
     */
    TimelineLite fromTo(JsObject object, double duration, JsObject fromVars, JsObject toVars, JsObject position);

    /**
     *
     * Returns an array containing all the tweens and/or timelines nested in this timeline.
     *
     * @param neted
     * @param tweens
     * @param timelines
     * @param ignoreBeforeTime
     * @return
     */
    Array<Animation> getChildren(boolean neted, boolean tweens, boolean timelines, double ignoreBeforeTime);

    /**
     *
     * Returns the time associated with a particular label.
     *
     * @param label
     * @return
     */
    double getLabelTime(String label);

    /**
     *
     * Returns the tweens of a particular object that are inside this timeline.
     *
     * @param target
     * @param nested
     * @return
     */
    Array<Animation> getTweensOf(JsObject target, boolean nested);

    /**
     * [override] Clears any initialization data (like starting/ending values in tweens) which can be useful if,
     * for example, you want to restart a tween without reverting to any previously recorded starting values.
     *
     * @return
     */
    TimelineLite invalidate();

    /**
     * Removes a tween, timeline, callback, or label (or array of them) from the timeline.
     *
     * @param value
     * @return
     */
    TimelineLite remove(JsObject value);

    /**
     *
     * Removes a label from the timeline and returns the time of that label.
     *
     * @param label
     * @return
     */
    JsObject removeLabel(String label);

    /**
     *
     * [override] Jumps to a specific time (or label) without affecting whether or not the instance is paused or reversed.
     *
     * @param position
     * @param suppressEvents
     * @return
     */
    TimelineLite seek(JsObject position, boolean suppressEvents);

    /**
     *
     * Adds a zero-duration tween to the end of the timeline (or elsewhere using the "position" parameter) that sets
     * values immediately (when the virtual playhead reaches that position on the timeline) - this is a convenience method
     * that accomplishes exactly the same thing as add( TweenLite.to(target, 0, {...}) ) but with less code.
     *
     * @param target
     * @param vars
     * @param position
     * @return
     */
    TimelineLite set(JsObject target, JsObject vars, JsObject position);

    /**
     *
     * Shifts the startTime of the timeline's children by a certain amount and optionally adjusts labels too.
     *
     * @param amount
     * @param adjustLabels
     * @param ignoreBeforeTime
     * @return
     */
    TimelineLite shiftChildren(double amount, boolean adjustLabels, double ignoreBeforeTime);


    /**
     *
     * Tweens an array of targets from a common set of destination values (using the current values
     * as the destination), but staggers their start times by a specified amount of time, creating an
     * evenly-spaced sequence with a surprisingly small amount of code.
     *
     * @param targets
     * @param duration
     * @param vars
     * @param stagger
     * @param position
     * @param onCompleteAll
     * @param onCompleteAllParams
     * @param onCompleteScope
     * @return
     */
    TimelineLite staggerFrom(Array<JsObject> targets, double duration, JsObject vars, double stagger, JsObject position,
                             JsObject onCompleteAll, Array<JsObject> onCompleteAllParams, JsObject onCompleteScope);

    /**
     *
     * Tweens an array of targets from and to a common set of values, but staggers their start times
     * by a specified amount of time, creating an evenly-spaced sequence with a surprisingly small amount of code.
     *
     * @param targets
     * @param duration
     * @param fromVars
     * @param toVars
     * @param stagger
     * @param position
     * @param onCompleteAll
     * @param onCompleteAllParams
     * @param onCompleteScope
     * @return
     */
    TimelineLite staggerFromTo(Array<JsObject> targets, double duration, JsObject fromVars, JsObject toVars, double stagger, JsObject position,
                             JsObject onCompleteAll, Array<JsObject> onCompleteAllParams, JsObject onCompleteScope);

    /**
     *
     * Tweens an array of targets to a common set of destination values, but staggers their start times
     * by a specified amount of time, creating an evenly-spaced sequence with a surprisingly small amount of code.
     *
     * @param targets
     * @param duration
     * @param vars
     * @param stagger
     * @param position
     * @param onCompleteAll
     * @param onCompleteAllParams
     * @param onCompleteScope
     * @return
     */
    TimelineLite staggerTo(Array<JsObject> targets, double duration, JsObject vars, double stagger, JsObject position,
                               JsObject onCompleteAll, Array<JsObject> onCompleteAllParams, JsObject onCompleteScope);

    /**
     *
     * Adds a TweenLite.to() tween to the end of the timeline (or elsewhere using the "position" parameter) -
     * this is a convenience method that accomplishes exactly the same thing as add( TweenLite.to(...) ) but with less code.
     *
     * @param target
     * @param duration
     * @param vars
     * @param position
     * @return
     */
    TimelineLite to(JsObject target, double duration, JsObject vars, JsObject position);

    /**
     *
     * [override] Adjusts the timeline's timeScale to fit it within the specified duration.
     *
     * @param value
     * @return
     */
    TimelineLite totalDuration(double value);

    /**
     *
     * [READ-ONLY] If true, the timeline's timing mode is frames-based instead of seconds.
     *
     * @return
     */
    boolean useFrames();

    public static GeneratorHolder getterGenerator = new GeneratorHolder(
        new KeyValue("getDuration", "duration()"),
        new KeyValue("getTotalDuration", "totalDuration()")
    );

    /**
     * [override] Gets the timeline's duration.
     * @return
     */
    double getDuration();

    /**
     * [override] Gets the timeline's total duration.
     *
     * @return
     */
    double getTotalDuration();

    public class Factory {

        public static final native TimelineLite newNativeInstance() /*-{
            return new $wnd.TimelineLite();
        }-*/;

        public static TimelineLite newInstance() {
            TimelineLite timelineLite = newNativeInstance();
            Animation.getterGenerator.generateGetters(timelineLite);
            TimelineLite.getterGenerator.generateGetters(timelineLite);
            return timelineLite;
        }

    }

}
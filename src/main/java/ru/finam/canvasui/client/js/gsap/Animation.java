package ru.finam.canvasui.client.js.gsap;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.Array;
import ru.finam.canvasui.client.js.JsObject;

/**
 * Created by ikusch on 25.08.14.
 *
 * Base class for all TweenLite, TweenMax, TimelineLite, and TimelineMax classes, providing core
 * methods/properties/functionality, but there is no reason to create an instance of this class
 * directly.
 *
 */
@JsType(isNative = true, prototype = "$wnd.core.Animation")
public interface Animation extends JsObject {

    public static class Static {

        /**
         *
         * The object that dispatches a "tick" event each time the engine updates, making it easy for you to
         * add your own listener(s) to run custom logic after each update (great for game developers).
         *
         * @return - ticker
         */
        public static final native JsObject getTicker() /*-{
            return $wnd.TweenLite.ticker;
        }-*/;

        public static final native void setTicker(JsObject ticker) /*-{
            $wnd.TweenLite.ticker = ticker;
        }-*/;

    }

    @JsProperty(value = "data")
    void setData(JsObject data);

    /**
     * A place to store any data you want (initially populated with vars.data if it exists).
     */
    @JsProperty(value = "data")
    JsObject getData();

    /**
     * [Read-only] Parent timeline.
     */
    @JsProperty(value = "timeline")
    SimpleTimeline getTimeline();

    /**
     * The vars object passed into the constructor which stores configuration variables like onComplete, onUpdate, etc.
     */
    @JsProperty(value = "vars")
    JsObject getVars();

    @JsProperty(value = "vars")
    void setVars(JsObject vars);

    /**
     * Sets the animation's initial delay which is the length of time in seconds (or frames for frames-based tweens) before the animation should begin.
     */
    Animation delay(double value);

    /**
     * Sets the animation's duration, not including any repeats or repeatDelays (which are only available in TweenMax and TimelineMax).
     */
    Animation duration(double d);

    /**
     * Sets an event callback like "onComplete", "onUpdate", "onStart", "onReverseComplete" or "onRepeat" (onRepeat only applies to
     * TweenMax or TimelineMax instances) along with any parameters that should be passed to that callback.
     */
    Animation eventCallback(String type, JsObject callback, Array<JsObject> params, JsObject scope);

    /**
     * Clears any initialization data (like starting/ending values in tweens) which can be useful if,
     * for example, you want to restart a tween without reverting to any previously recorded starting values.
     * @return
     */
    Animation invalidate();

    /**
     * Indicates whether or not the animation is currently active (meaning the virtual playhead is
     * actively moving across this instance's time span and it is not paused, nor are any of its ancestor timelines).
     */
    boolean isActive();

    /**
     * Kills the animation entirely or in part depending on the parameters.
     */
    Animation kill(JsObject vars, JsObject target);

    /**
     * Pauses the instance, optionally jumping to a specific time.
     */
    Animation pause(JsObject atTime, boolean suppressEvents);

    /**
     * Sets the animation's paused state which indicates whether or not the animation is currently paused.
     */
    Animation paused(boolean value);

    /**
     * Begins playing forward, optionally from a specific time (by default playback begins from wherever the playhead currently is).
     */
    Animation play(JsObject from, boolean suppressEvents);

    /**
     * Sets the animations's progress which is a value between 0 and 1 indicating the position of the virtual
     * playhead (excluding repeats) where 0 is at the beginning, 0.5 is at the halfway point, and 1 is at the end (complete).
     */
    Animation progress(double value, boolean suppressEvents);

    /**
     * Restarts and begins playing forward from the beginning.
     */
    Animation restart(boolean includeDelay , boolean suppressEvents);

    /**
     * Resumes playing without altering direction (forward or reversed), optionally jumping to a specific time first.
     */
    Animation resume(JsObject from, boolean suppressEvents);

    /**
     * Reverses playback so that all aspects of the animation are oriented backwards including, for example, a tween's ease.
     */
    Animation reverse(JsObject from, boolean suppressEvents);

    /**
     * Sets the animation's reversed state which indicates whether or not the animation should be played backwards.
     */
    Animation reversed(boolean value);

    /**
     * Jumps to a specific time without affecting whether or not the instance is paused or reversed.
     */
    Animation seek(JsObject time, boolean suppressEvents);

    /**
     * Sets the time at which the animation begins on its parent timeline (after any delay that was defined).
     */
    Animation startTime(double value);

    /**
     * Sets the local position of the playhead (essentially the current time), described in seconds
     * (or frames for frames-based animations) which will never be less than 0 or greater than the animation's duration.
     */
    Animation time(double value, boolean suppressEvents);

    /**
     * Factor that's used to scale time in the animation where 1 = normal speed (the default), 0.5 = half speed, 2 = double speed, etc.
     */
    Animation timeScale(double value);

    /**
     * Sets the animation's total duration including any repeats or repeatDelays (which are only available in TweenMax and TimelineMax).
     */
    Animation totalDuration(double value);

    /**
     * Sets the animation's total progress which is a value between 0 and 1 indicating the position of the virtual playhead
     * (including repeats) where 0 is at the beginning, 0.5 is at the halfway point, and 1 is at the end (complete).
     */
    Animation totalProgress(double value, boolean suppressEvents);

    /**
     * Sets the position of the playhead according to the totalDuration which includes any repeats and repeatDelays (only available in TweenMax and TimelineMax).
     */
    Animation totalTime(double value, boolean suppressEvents);

    public static class KeyValue {

        private String key;
        private String value;

        KeyValue(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public static class GeneratorHolder {

        private KeyValue[] toGen;

        public GeneratorHolder(KeyValue... toGen) {
            this.toGen = toGen;
        }

        public void generateGetters(Animation animationI) {
            for (KeyValue call:toGen) {
                generateGetter(animationI, call.key, call.value);
            }
        }

        private final native void generateGetter(Animation animation, String name, String call) /*-{
            animation[name] = function() {
                return eval('animation.' + call);
            }
        }-*/;

    }

    public static GeneratorHolder getterGenerator = new GeneratorHolder(
        new KeyValue("getDelay", "delay()"),
        new KeyValue("getDuration", "duration()"),
        new KeyValue("getEventCallback", "eventCallback()"),
        new KeyValue("isPaused", "paused()"),
        new KeyValue("getProgress", "progress()"),
        new KeyValue("isReversed", "reversed()"),
        new KeyValue("getStartTime", "startTime()"),
        new KeyValue("getTime", "time()"),
        new KeyValue("getTimeScale", "timeScale()"),
        new KeyValue("getTotalDuration", "totalDuration()"),
        new KeyValue("getTotalProgress", "totalProgress()"),
        new KeyValue("getTotalTime", "totalTime()")
    );

    /**
     * Gets the animation's initial delay which is the length of time in seconds (or frames for frames-based tweens) before the animation should begin.
     */
    double getDelay();

    /**
     * Gets the animation's duration, not including any repeats or repeatDelays (which are only available in TweenMax and TimelineMax).
     */
    double getDuration();

    /**
     * Gets an event callback like "onComplete", "onUpdate", "onStart", "onReverseComplete" or "onRepeat"
     * (onRepeat only applies to TweenMax or TimelineMax instances) along with any parameters that should be
     * passed to that callback.
     */
    JsObject getEventCallback();

    /**
     * Gets the animation's paused state which indicates whether or not the animation is currently paused.
     */
    boolean isPaused();

    /**
     * Gets the animations's progress which is a value between 0 and 1 indicating the position of the virtual playhead
     * (excluding repeats) where 0 is at the beginning, 0.5 is at the halfway point, and 1 is at the end (complete).
     */
    double getProgress();

    /**
     * Gets the animation's reversed state which indicates whether or not the animation should be played backwards.
     */
    boolean isReversed();

    /**
     * Gets the time at which the animation begins on its parent timeline (after any delay that was defined).
     */
    double getStartTime();

    /**
     * Gets the local position of the playhead (essentially the current time), described in seconds
     * (or frames for frames-based animations) which will never be less than 0 or greater than the animation's duration.
     */
    double getTime();

    /**
     * Factor that's used to scale time in the animation where 1 = normal speed (the default), 0.5 = half speed, 2 = double speed, etc.
     */
    double getTimeScale();

    /**
     * Gets the animation's total duration including any repeats or repeatDelays (which are only available in TweenMax and TimelineMax).
     */
    double getTotalDuration();

    /**
     * Gets the animation's total progress which is a value between 0 and 1 indicating the position of the virtual playhead
     * (including repeats) where 0 is at the beginning, 0.5 is at the halfway point, and 1 is at the end (complete).
     */
    double getTotalProgress();

    /**
     * Gets the position of the playhead according to the totalDuration which includes any repeats and repeatDelays (only available in TweenMax and TimelineMax).
     */
    double getTotalTime();

}
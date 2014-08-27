package ru.finam.canvasui.client.js.gsap;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.Array;
import ru.finam.canvasui.client.js.JsFunction;
import ru.finam.canvasui.client.js.JsObject;
import ru.finam.canvasui.client.js.gsap.easing.Ease;

/**
 * Created by ikusch on 26.08.14.
 */
@JsType(isNative = true, prototype = "$wnd.TweenLite")
public interface TweenLite extends Animation {

    public static class Static {

        /**
         * Provides An easy way to change the default easing equation. Choose from any of the GreenSock eases in the com.greensock.easing package.
         *
         * The default value is Power1.easeOut.
         */
        public static final native Ease getDefaultEase() /*-{
            return $wnd.TweenLite.defaultEase;
        }-*/;

        public static final native void setDefaultEase(Ease ease) /*-{
            $wnd.TweenLite.defaultEase = ease;
        }-*/;

        /**
         * Provides An easy way to change the default overwrite mode. Choose from any of the following: "auto", "all", "none", "allOnStart", "concurrent", "preexisting".
         * The default value is "auto".
         */
        public static final native String getDefaultOverwrite() /*-{
            return $wnd.TweenLite.defaultOverwrite;
        }-*/;

        public static final native void setDefaultOverwrite(String defaultOverwrite) /*-{
            $wnd.TweenLite.defaultOverwrite = defaultOverwrite;
        }-*/;

        /**
         * The selector engine (like jQuery) that should be used when a tween receives a string as its target,
         * like TweenLite.to("#myID", 1, {x:"100px"}). By default, TweenLite will look for window.$ and then
         * window.jQuery and if neither is found, it will default to document.getElementById() (in which case
         * it will also strip out any leading "#" in any IDs it receives). Feel free to use any selector you
         * want: jQuery, Zepto, Sizzle, or your own. Set it like this:
         *
         * TweenLite.selector = YOUR_SELECTOR_ENGINE;
         *
         * If jQuery is loaded (or anything that's defined as the industry standard window.$), you don't need to
         * do anything - TweenLite will automatically sense (and use) it. But TweenLite does NOT have any
         * dependencies on jQuery or any specific selector engine. If you don't load any, TweenLite will use
         * document.getElementById() which will at least allow you to define things with IDs.
         *
         * //tween the element with ID of "myID"
         * TweenLite.to("#myID", 2, {backgroundColor:"#ff0000", width:"50%", top:"100px", ease:Power2.easeInOut});
         *
         * //or if jQuery is loaded, you can do more advanced selecting like all the elements with the class "myClass" like this:
         * TweenLite.to(".myClass", 2, {boxShadow:"0px 0px 20px red", color:"#FC0"});
         *
         * For maximum performance it is typically best to store the results of a selector in a variable
         * if you need to reference the same set multiple times:
         *
         * //slightly slower because the selector engine needs to find the same elements twice:
         * TweenLite.to(".myClass", 2, {left:"100px"});
         * TweenLite.to(".myClass", 2, {top:"200px", delay:2});
         *
         * //it's a bit faster to store it in a variable that you reuse like this instead:
         * var target = $(".myClass");
         * TweenLite.to(target, 2, {left:"100px"});
         * TweenLite.to(target, 2, {top:"200px", delay:2});
         *
         * The only requirements for the selector engine is that it must have an "each()" method that iterates
         * through each element in the results and "this" inside the function refers to the DOM element, and it
         * must make the DOM elements accessible via index notation like selectorResults[0], selectorResults[1], etc.
         * This is pretty standard among selector engines.
         *
         * @return - JsObject
         */
        public static final native JsObject getSelector() /*-{
            return $wnd.TweenLite.selector;
        }-*/;

        public static final native void setSelector(JsObject selector) /*-{
            $wnd.TweenLite.selector = selector;
        }-*/;

        /**
         *
         * The object that dispatches a "tick" event each time the engine updates, making it easy for you to
         * add your own listener(s) to run custom logic after each update (great for game developers).
         * Add as many listeners as you want.
         *
         * Basic example:
         *
         * //add listener
         * TweenLite.ticker.addEventListener("tick", myFunction);
         *
         * function myFunction(event) {
         *  //executes on every tick after the core engine updates
         * }
         *
         * //to remove the listener later...
         * TweenLite.ticker.removeEventListener("tick", myFunction);
         *
         * The ticker is driven by requestAnimationFrame events in modern browsers so that the
         * updates are perfectly synchronized with the browser's rendering cycle. It also means that
         * when the user switches to a different tab in the browser, the ticker's updates get throttled
         * back significantly in order to conserve battery power and reduce load on the CPU (this happens
         * because the browser itself throttles back requestAnimationFrame event dispatching). Typically
         * requestAnimationFrame events occur around 60 times per second, but that's up to the browser and
         * depends on system performance as well. If requestAnimationFrame isn't supported, the ticker
         * automatically falls back to using a regular setTimeout() loop which is supported in all browsers.
         *
         * Customizing the ticker
         *
         * To force the ticker to use setTimout() instead of requestAnimationFrame, you can use the
         * ticker's useRAF() method:
         *
         * //turn off requestAnimationFrame, causing ticker to use setTimeout() instead*
         * TweenLite.ticker.useRAF(false);]
         *
         *
         * And if you'd like to set a particular frame rate, you can use the fps() method like this:
         *
         * //throttle back the frames-per-second to 30
         * TweenLite.ticker.fps(30);
         *
         * When using requestAnimationFrame (the default), the fps() setting acts like a throttle.
         * Since you cannot tell the browser to crank outrequestAnimationFrames at a higher rate than 60fps,
         * you can't do something like TweenLite.ticker.fps(100) (well, you can but it'll still run at around 60fps).
         * You could, however, do TweenLite.ticker.fps(30) and the engine will skip beats when necessary in order to
         * get you as close as possible to 30fps (or whatever fps you set below 60). If you need an fps greater than
         * 60fps (which generally isn't recommended), you should turn off requestAnimationFrame using
         * TweenLite.ticker.useRAF(false) and then set the fps() to whatever you want, likeTweenLite.ticker.fps(100)
         *
         * Advanced listeners
         *
         * If you need to define the scope (what "this" refers to inside the function) or define a particular priority
         * so that the handlers are called in a particular order, you can use the advanced syntax with extra parameters
         * as follows:
         *
         * addEventListener(type, callback, scope, useParam, priority)
         *
         * Parameters:
         *
         * type : String - type of listener, should always be "tick"
         *
         * callback : Function - the function to call when the event occurs
         *
         * scope : Object - binds the scope to a particular object (scope is basically what "this" refers to in your
         *  function). This can be very useful in JavaScript because scope isn't generally maintained.
         *
         * useParam : Boolean - if true, an event object will be generated and fed to the callback each
         * time the event occurs. The event is a generic object and has two properties: type (always "tick")
         * and target which refers to the ticker instance. The default for useParam is falsebecause it improves
         * performance.
         *
         * priority : Integer - influences the order in which the listeners are called. Listeners with lower
         * priorities are called after ones with higher priorities.
         *
         * Advanced Example
         *
         * //add listener that requests an event object parameter, binds scope to the current scope (this),
         * //and sets priority to 1 so that it is called before any other listeners that had a priority lower than 1...
         * TweenLite.ticker.addEventListener("tick", myFunction, this, true, 1);
         *
         * function myFunction(event) {
         * //executes on every tick after the core engine updates
         * }
         *
         * //to remove the listener later...
         * TweenLite.ticker.removeEventListener("tick", myFunction);
         *
         * @return ticker
         *
         */
        public static final native JsObject getTicker() /*-{
            return $wnd.TweenLite.ticker;
        }-*/;

        public static final native void setTicker(JsObject ticker) /*-{
            $wnd.TweenLite.ticker = ticker;
        }-*/;

        /**
         *
         * Provides a simple way to call a function after a set amount of time (or frames).
         * You can optionally pass any number of parameters to the function too.
         *
         * //calls myFunction after 1 second and passes 2 parameters:
         * TweenLite.delayedCall(1, myFunction, ["param1", 2]);
         *
         * function myFunction(param1, param2) {
         *  //do stuff
         * }
         *
         *
         * @param delay : Number - Delay in seconds (or frames if useFrames is true) before the function should be called
         * @param callback : Function - Function to call
         * @param params : Array (default = null) — An Array of parameters to pass the function (optional).
         * @param scope (default = null) — The scope in which the callback should be called (basically,
         *              what "this" refers to in the function).
         * @param useFrames : Boolean (default = false) — If the delay should be measured in frames
         *                  instead of seconds, setuseFrames to true (default is false)
         *
         * @return TweenLite
         *
         */
        public static final native TweenLite delayedCall(double delay, JsFunction callback, Array<JsObject> params, JsObject scope, boolean useFrames) /*-{
            return $wnd.TweenLite.delayedCall(delay, callback, params, scope, useFrames);
        }-*/;

        /**
         *
         * Static method for creating a TweenLite instance that tweens backwards - you define the BEGINNING
         * values and the current values are used as the destination values which is great for doing things
         * like animating objects onto the screen because you can set them up initially the way you want them to look at the
         * end of the tween and then animate in from elsewhere.
         *
         * @param target - Target object (or array of objects) whose properties this tween affects.
         * @param duration - Duration in seconds (or frames if useFrames:true is set in the vars parameter)
         * @param vars - An object defining the starting value for each property that should be tweened as
         *             well as any special properties like onComplete, ease, etc. For example, to tween obj.x
         *             from 100 and obj.y from 200 and then call myFunction, do this:
         *             TweenLite.from(obj, 1, {x:100, y:200, onComplete:myFunction});
         * @return
         */
        public static final native TweenLite from(JsObject target, double duration, JsObject vars) /*-{
            return $wnd.TweenLite.from(target, duration, vars);
        }-*/;

        /**
         *
         * Static method for creating a TweenLite instance that allows you to define both the starting and ending values
         * (as opposed to to() and from() tweens which are based on the target's current values at one end or the other).
         *
         * @param target - Target object (or array of objects) whose properties this tween affects.
         * @param duration - Duration in seconds (or frames if useFrames:true is set in the vars parameter)
         * @param fromVars - An object defining the starting value for each property that should be tweened.
         *                 For example, to tween mc.x from 100 and mc.y from 200, fromVars would look like this: {x:100, y:200}.
         * @param toVars - An object defining the end value for each property that should be tweened as well as any special
         *               properties likeonComplete, ease, etc. For example, to tween mc.x from 0 to 100 and mc.y from 0 to 200
         *               and then call myFunction, do this:TweenLite.fromTo(mc, 1, {x:0, y:0}, {x:100, y:200, onComplete:myFunction});
         * @return
         */
        public static final native TweenLite fromTo(JsObject target, double duration, JsObject fromVars, JsObject toVars) /*-{
            return $wnd.TweenLite.fromTo(target, duration, fromVars, toVars);
        }-*/;

        /**
         *
         * Returns an array containing all the tweens of a particular target (or group of targets) that have
         * not been released for garbage collection yet which typically happens within a few seconds after
         * the tween completes.
         *
         * @param target - The target whose tweens should be returned, or an array of such targets
         * @param onlyActive - (default = false) — If true, only tweens that are currently active will be
         *                   returned (a tween is considered "active" if the virtual playhead is actively moving
         *                   across the tween and it is not paused, nor are any of its ancestor timelines paused).
         *
         * @return - An array of tweens
         */
        public static final native TweenLite getTweensOf(JsObject target, boolean onlyActive) /*-{
            return $wnd.TweenLite.getTweensOf(target, onlyActive);
        }-*/;

        /**
         *
         * Immediately kills all of the delayedCalls to a particular function.
         *
         * @param func - The function for which all delayedCalls should be killed/cancelled.
         * @return
         */
        public static final native void killDelayedCallsTo(JsFunction func) /*-{
            $wnd.TweenLite.killDelayedCallsTo(func);
        }-*/;

        /**
         *
         * Kills all the tweens (or specific tweening properties) of a particular object or delayedCalls to a particular function.
         *
         * @param target - Object whose tweens should be killed immediately or selector text to feed the selector engine to find the target(s).
         * @param onlyActive - (default = false) — If true, only tweens that are currently active will be killed
         *                   (a tween is considered "active" if the virtual playhead is actively moving across the tween and
         *                   it is not paused, nor are any of its ancestor timelines paused).
         * @param vars - (default = null) — To kill only specific properties, use a generic object containing enumerable
         *             properties corresponding to the ones that should be killed like {x:true, y:true}. The values assigned
         *             to each property of the object don't matter - the sole purpose of the object is for iteration over the named
         *             properties (in this case, x and y). If no object (or null) is defined, all matched tweens will
         *             be killed in their entirety.
         * @return
         */
        public static final native void killTweensOf(JsObject target, boolean onlyActive, JsObject vars) /*-{
            $wnd.TweenLite.killTweensOf(target, onlyActive, vars);
        }-*/;

        /**
         *
         * Immediately sets properties of the target accordingly - essentially a zero-duration to() tween with a more intuitive name.
         *
         * @param target - Target object (or array of objects) whose properties will be affected.
         * @param vars - An object defining the value for each property that should be set. For example,
         *             to set mc.x to 100 and mc.y to 200, do this:TweenLite.set(mc, {x:100, y:200});
         * @return
         */
        public static final native TweenLite set(JsObject target, JsObject vars) /*-{
            $wnd.TweenLite.set(target, vars);
        }-*/;

        /**
         *
         * Static method for creating a TweenLite instance that animates to the specified destination values (from the current values).
         *
         * @param target - [READ-ONLY] Target object (or array of objects) whose properties the tween affects.
         * @param duration - Duration in seconds (or frames if useFrames:true is set in the vars parameter).
         * @param vars - An object defining the end value for each property that should be tweened as well as
         *             any special properties like onComplete, ease, etc. For example, to tween mc.x to 100 and mc.y to
         *             200 and then call myFunction, do this: TweenMax.to(mc, 1, {x:100, y:200, onComplete:myFunction});
         *             Below is a full list of special properties.
         * @return
         */
        public static final native TweenLite to(JsObject target, double duration, JsObject vars) /*-{
            $wnd.TweenLite.to(target, duration, vars);
        }-*/;

    }

    /**
     *
     * [READ-ONLY] Target object (or array of objects) whose properties the tween affects.
     *
     * @return JsObject
     *
     */
    @JsProperty(value = "target")
    JsObject getTarget();

    /**
     *
     * Clears any initialization data (like starting/ending values in tweens) which can be
     * useful if, for example, you want to restart a tween without reverting to any previously
     * recorded starting values.
     *
     * @return - self (makes chaining easier)
     */
    @Override
    TweenLite invalidate();

    /**
     *
     * @param threshold - Amount of lag (in millisecond) after which the engine will adjust the
     *                  internal clock to act like the adjustedLag elapsed instead. The lower the
     *                  number, the more likely (and frequently) lagSmoothing() will be triggered.
     *                  For example, if the threshold is 500 and the adjustedLag is 33 (those are the
     *                  defaults), the only time an adjustment will occur is when more than 500ms elapses
     *                  between two ticks in which case it will act as though only 33ms elapsed. So if the
     *                  CPU bogs down for 2 full seconds (yikes!), your animations will move 33ms worth of
     *                  time on the next render instead of jumping a full 2-seconds. Note: this has no affect
     *                  on the device’s performance or true frame rate – this merely affects how GSAP reacts
     *                  when the browser drops frames.
     * @param adjustedLag - The new (adjusted) amount of time (in milliseconds) from the previous tick.
     *                    Typically it is best to set this to at least 16 because that's the normal amount
     *                    of time between ticks when the engine is running at 60 frames per second. It is more
     *                    common to set it to at least 33 (which is 2 normal "ticks"). If you set the threshold
     *                    and the adjustedLag too low, your animations can appear to slow down under heavy pressure.
     *                    The higher the adjustedLag, the more of a "jump" you'll see when lagSmoothing kicks in.
     */
    void lagSmoothing(double threshold, double adjustedLag);

    /**
     * Forces a render of all active tweens which can be useful if, for example, you set up a bunch of from()
     * tweens and then you need to force an immediate render (even of "lazy" tweens) to avoid a brief delay before
     * things render on the very next tick.
     */
    void render();

}
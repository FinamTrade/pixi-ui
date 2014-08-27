package ru.finam.canvasui.client.js.gsap.easing;

import com.google.gwt.core.client.js.JsType;

/**
 * Created by ikusch on 27.08.14.
 *
 * Most easing equations give a smooth, gradual transition between the start and end values,
 * but SteppedEase provides an easy way to define a specific number of steps that the transition
 * should take. For example, if mc.x is 0 and you want to tween it to 100 with 5
 * steps (20, 40, 60, 80, and 100) over the course of 2 seconds, you'd do:
 *
 *  TweenLite.to(mc, 2, {x:100, ease:SteppedEase.config(5)});
 *  //or create an instance directly
 *  var steppedEase = new SteppedEase(5);
 *  TweenLite.to(mc, 3, {y:300, ease:steppedEase});
 *
 * Note: SteppedEase is optimized for use with the GreenSock tweenining platform, so it isn't
 * intended to be used with other engines. Specifically, its easing equation always
 * returns values between 0 and 1.
 *
 */
@JsType(isNative = true, prototype = "$wnd.easing.SteppedEase")
public interface SteppedEase extends Ease {

    public static class Static {

        /**
         *
         * Permits customization of the ease (defining a number of steps).
         *
         * @param steps - Number of steps between the start and the end values.
         *
         * @return - new SteppedEase instance that is configured according to the parameters provided
         *
         */
        public static final native SteppedEase config(int steps) /*-{
            return $wnd.easing.SteppedEase.config(steps);
        }-*/;

    }

    /**
     *
     * [override] Translates the tween's progress ratio into the corresponding ease ratio. This is the heart of the Ease, where it does all its work.
     *
     * @param p - progress ratio (a value between 0 and 1 indicating the progress of the tween/ease)
     *
     * @return - translated number
     */
    @Override
    double getRatio(double p);

    public static class Factory {

        /**
         *
         * Constructor
         *
         * @param steps - Number of steps between the start and the end values.
         *
         * @return - SteppedEase
         *
         */
        public static final native SteppedEase construct(int steps) /*-{
            return new SteppedEase(steps);
        }-*/;

    }

}

package ru.finam.canvasui.client.js.gsap.easing;

import com.google.gwt.core.client.js.JsType;

/**
 * Created by ikusch on 27.08.14.
 *
 * SlowMo is a configurable ease that produces a slow-motion effect that decelerates initially,
 * then moves linearly for a certain portion of the ease (which you can choose) and then
 * accelerates again at the end; it's great for effects like zooming text onto the screen,
 * smoothly moving it long enough for people to read it, and then zooming it off the screen.
 * Without SlowMo, animators would often try to get the same effect by sequencing 3 tweens,
 * one with an easeOut, then another with a Linear.easeNone, and finally an easeIn but the
 * problem was that the eases didn't smoothly transition into one another, so you'd see sudden
 * shifts in velocity at the joints. SlowMo solves this problem and gives you complete control
 * over how strong the eases are on each end and what portion of the movement in the middle
 * is linear.
 *
 * The first parameter, linearRatio, determines the proportion of the ease during which the
 * rate of change will be linear (steady pace). This should be a number between 0 and 1.
 * For example, 0.5 would be half, so the first 25% of the ease would be easing out (decelerating),
 * then 50% would be linear, then the final 25% would be easing in (accelerating). If you choose 0.8,
 * that would mean 80% of the ease would be linear, leaving 10% on each end to ease. The default is 0.7.
 *
 * The second parameter, power, determines the strength of the ease at each end. If you define a
 * value greater than 1, it will actually reverse the linear portion in the middle which can
 * create interesting effects. The default is 0.7.
 *
 * The third parameter, yoyoMode, provides an easy way to create companion tweens that sync with
 * normal SlowMo tweens. For example, let's say you have a SlowMo tween that is zooming some text
 * onto the screen and moving it linearly for a while and then zooming off, but you want to tween
 * that alpha of the text at the beginning and end of the positional tween. Normally, you'd need to
 * create 2 separate alpha tweens, 1 for the fade-in at the beginning and 1 for the fade-out at the
 * end and you'd need to calculate their durations manually to ensure that they finish fading in
 * by the time the linear motion begins and then they start fading out at the end right when the
 * linear motion completes. But to make this whole process much easier, all you'd need to do is
 * create a separate tween for the alpha and use the same duration but a SlowMo ease that has
 * its yoyoMode parameter set to true.
 *
 * //use the default SlowMo ease (linearRatio of 0.7 and power of 0.7)
 * TweenLite.to(myText, 5, {x:600, ease:SlowMo.ease});
 *
 * //use a new SlowMo ease with 50% of the tween being linear (2.5 seconds) and a power of 0.8
 * TweenLite.to(myText, 5, {x:600, ease:new SlowMo(0.5, 0.8)});
 *
 * //this gives the exact same effect as the line above, but uses a different syntax
 * TweenLite.to(myText, 5, {x:600, ease:SlowMo.ease.config(0.5, 0.8)});
 *
 * //now let's create an opacity tween that syncs with the above positional tween, fading it in at the beginning and out at the end
 * TweenLite.from(myText, 5, {opacity:0, ease:SlowMo.ease.config(0.5, 0.8, true)});
 *
 */
@JsType(isNative = true, prototype = "$wnd.easing.SlowMo")
public interface SlowMo extends Ease {

    public static class Factory {

        /**
         * Constructor
         *
         * @param linearRatio - Number (default = 0.7) — the proportion of
         *  the ease during which the rate of change will be linear (steady pace). This should be a
         *  number between 0 and 1. For example, 0.5 would be half, so the first 25% of the ease
         *  would be easing out (decelerating), then 50% would be linear, then the final 25% would be
         *  easing in (accelerating). If you choose 0.8, that would mean 80% of the ease would be linear,
         *  leaving 10% on each end to ease. The default is 0.7.
         *
         * @param power - Number (default = 0.7) — The strength of the
         *  ease at each end. If you define a value above 1, it will actually reverse the linear
         *  portion in the middle which can create interesting effects. The default is 0.7.
         *
         * @param yoyoMode - Boolean (default = false) — If true, the ease will
         *  reach its destination value mid-tween and maintain it during the entire linear mode and
         *  then go back to the original value at the end (like a yoyo of sorts). This can be very
         *  useful if, for example, you want the alpha (or some other property) of some text to fade at
         *  the front end of a SlowMo positional ease and then back down again at the end of that positional
         *  SlowMo tween. Otherwise you would need to create separate tweens for the beginning and ending
         *  fades that match up with that positional tween.
         *
         *  Example:
         *      TweenLite.to(myText, 5, {x:600, ease:SlowMo.ease.config(0.7, 0.7, false)});
         *      TweenLite.from(myText, 5, {alpha:0, ease:SlowMo.ease.config(0.7, 0.7, true)});
         *
         * @return - SlowMo
         *
         */
        public static final native SlowMo construct(double linearRatio, double power, boolean yoyoMode) /*-{
            return new $wnd.SlowMo(linearRatio, power, yoyoMode);
        }-*/;

    }

    public static class Static {

        /**
         * The default ease instance which can be reused many times in various tweens
         * in order to conserve memory and improve performance slightly compared to
         * creating a new instance each time.
         */
        public static final native SlowMo ease() /*-{
            return $wnd.SlowMo.ease;
        }-*/;

    }

    /**
     *
     * Permits customization of the ease with various parameters.
     *
     * @param linearRatio - Number (default = 0.7) — the proportion of the ease
     *  during which the rate of change will be linear (steady pace). This should
     *  be a number between 0 and 1. For example, 0.5 would be half, so the first
     *  25% of the ease would be easing out (decelerating), then 50% would be linear,
     *  then the final 25% would be easing in (accelerating). If you choose 0.8, that
     *  would mean 80% of the ease would be linear, leaving 10% on each end to ease.
     *  The default is 0.7.
     *
     * @param power - Number (default = 0.7) — The strength of the ease at each end.
     *  If you define a value above 1, it will actually reverse the linear portion
     *  in the middle which can create interesting effects. The default is 0.7.
     *
     * @param yoyoMode - Boolean (default = false) — If true, the ease will reach
     *  its destination value mid-tween and maintain it during the entire linear mode
     *  and then go back to the original value at the end (like a yoyo of sorts). This can
     *  be very useful if, for example, you want the alpha (or some other property) of some
     *  text to fade at the front end of a SlowMo positional ease and then back down again
     *  at the end of that positional SlowMo tween. Otherwise you would need to create separate
     *  tweens for the beginning and ending fades that match up with that positional tween.
     *
     *  Example:
     *      TweenLite.to(myText, 5, {x:600, ease:SlowMo.ease.config(0.7, 0.7, false)});
     *      TweenLite.from(myText, 5, {alpha:0, ease:SlowMo.ease.config(0.7, 0.7, true)});
     *
     * @return - new SlowMo instance that is configured according to the parameters provided
     *
     */
    SlowMo config(double linearRatio, double power, boolean yoyoMode);

    /**
     *
     * Translates the tween's progress ratio into the corresponding ease ratio. This is the heart of the
     * Ease, where it does all its work.
     *
     * @param p - progress ratio (a value between 0 and 1 indicating the progress of the tween/ease)
     *
     * @return - translated number
     *
     */
    double getRatio(double p);

}

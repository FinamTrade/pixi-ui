package ru.finam.canvasui.client.js.gsap.easing;

import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.Array;
import ru.finam.canvasui.client.js.JsObject;

/**
 * Created by ikusch on 27.08.14.
 */
@JsType(isNative = true, prototype = "$wnd.easing.Ease")
public interface Ease extends JsObject {

    /**
     *
     * Translates the tween's progress ratio into the corresponding ease ratio.
     *
     * @param p - progress ratio (a value between 0 and 1 indicating the progress of the tween/ease)
     * @return
     */
    public double getRatio(double p);

    public static class Factory {

        /**
         *
         * Constructor
         *
         * @param func  Function (default = null) — Function (if any) that should be proxied.
         * This is completely optional and is in fact rarely used except when you have your
         * own custom ease function that follows the standard ease parameter pattern like time,
         * start, change, duration.
         *
         * @param extraParams Array (default = null) — If any extra parameters beyond the standard
         * 4 (time, start, change, duration) need to be fed to the func function, define them
         * as an array here. For example, the old Elastic.easeOut accepts 2 extra parameters in
         * its standard equation (although the newer GreenSock version uses the more modern
         * config() method for configuring the ease and doesn't require any extraPrams here)
         *
         * @param type Number (default = 0) — Integer indicating the type of ease where 1
         * is easeOut, 2 is easeIn, 3 is easeInOut, and 0 is none of these.
         *
         * @param power Number (default = 0) — Power of the ease where Linear is 0, Quad
         * is 1, Cubic is 2, Quart is 3, Quint (and Strong) is 4, etc.
         *
         * @return Ease
         */
        public static native Ease construct(JsObject func, Array<JsObject> extraParams, double type, double power) /*-{
            return new $wnd.easing.Ease(func, extraParams, type, power);
        }-*/;

    }

}

package ru.finam.canvasui.client.js.gsap.easing;

import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.JsObject;

/**
 * Created by ikusch on 27.08.14.
 *
 * Provides an easeIn, easeOut, and easeInOut with a power (or strength) of 0 which is
 * identical to Linear but with a more intuitive name. The more power, the more
 * exaggerated the easing effect. So Power0 actually has no power at all, providing a linear transition.
 * This is one of the eases that is natively accelerated in TweenLite and TweenMax. All of the
 * "Power" eases and their counterparts (Linear (0), Quad (1), Cubic (2), Quart (3), Quint (4),
 * and Strong (4)) are accelerated.
 *
 * Example usage:
 *  TweenLite.to(obj, 1, {x:100, ease:Power0.easeOut});
 *
 */
@JsType(isNative = true, prototype = "$wnd.easing.Power0")
public interface Power0 extends JsObject {

    public static class Static {

        /**
         * Eases in with a power of 0 (linear). Power0.easeIn, Power0.easeOut,
         * and Power0.easeInOut are all identical because there is no power - they're
         * all linear but use the common naming convention for ease of use.
         *
         * @return - Ease
         *
         */
        public static final native Ease easeIn() /*-{
            return $wnd.Power0.easeIn;
        }-*/;

        /**
         * Eases in and then out with a power of 0 (linear). Power0.easeIn, Power0.easeOut,
         * and Power0.easeInOut are all identical because there is no power - they're all
         * linear but use the common naming convention for ease of use.
         *
         * @return - Ease
         *
         */
        public static final native Ease easeInOut() /*-{
            return $wnd.Power0.easeInOut;
        }-*/;

        /**
         * Eases out with a power of 0 (linear). Power0.easeIn, Power0.easeOut, and Power0.easeInOut
         * are all identical because there is no power - they're all linear but use the common
         * naming convention for ease of use.
         *
         * @return - Ease
         *
         */
        public static final native Ease easeOut () /*-{
            return $wnd.Power0.easeOut;
        }-*/;

    }

}

package ru.finam.canvasui.client.js.gsap.easing;

import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.JsObject;

/**
 * Created by ikusch on 27.08.14.
 *
 * Provides an easeIn, easeOut, and easeInOut with a power (or strength) of 1 which is identical to
 * the Power1 ease. The more power, the more exaggerated the easing effect. Using a numeric approach
 * like Power1 instead of Quad makes experimenting easier and the code reads more intuitively.
 * This is one of the eases that is natively accelerated in TweenLite and TweenMax. All of the "Power"
 * eases and their counterparts (Linear (0), Quad (1), Cubic (2), Quart (3), Quint (4), and Strong (4))
 * are accelerated.
 *
 * Example usage:
 *
 * TweenLite.to(obj, 1, {x:100, ease:Quad.easeOut});
 *
 */
@JsType(isNative = true, prototype = "$wnd.easing.Quad")
public interface Quad extends JsObject {

    public static class Static {

        /**
         * Eases in with a power of 1.
         *
         * @return - Ease
         *
         */
        public static final native Ease easeIn() /*-{
            return $wnd.Quad.easeIn;
        }-*/;

        /**
         * Eases in and then out with a power of 1.
         *
         * @return - Ease
         *
         */
        public static final native Ease easeInOut() /*-{
            return $wnd.Quad.easeInOut;
        }-*/;

        /**
         * Eases out with a power of 1.
         *
         * @return - Ease
         *
         */
        public static final native Ease easeOut() /*-{
            return $wnd.Quad.easeOut;
        }-*/;

    }

}

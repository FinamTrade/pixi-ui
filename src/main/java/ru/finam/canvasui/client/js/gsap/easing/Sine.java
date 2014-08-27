package ru.finam.canvasui.client.js.gsap.easing;

import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.JsObject;

/**
 * Created by ikusch on 27.08.14.
 *
 * Eases with a relatively low power either at the beginning (easeIn), the end (easeOut),
 * or both (easeInOut). Sine is a convenience class that congregates the 3 types of Sine
 * eases (SineIn, SineOut, and SineInOut) as static properties so that they can be referenced
 * using the standard synatax, like Sine.easeIn, Sine.easeOut, and Sine.easeInOut.
 */
@JsType(isNative = true, prototype = "$wnd.easing.Sine")
public interface Sine extends JsObject {

    public static class Static {

        /**
         * Eases in with slight acceleration.
         *
         * @return - Ease
         *
         */
        public static final native Ease easeIn() /*-{
            return $wnd.easing.Sine.easeIn;
        }-*/;

        /**
         * Eases in and then out with slight acceleration/deceleration.
         *
         * @return - Ease
         *
         */
        public static final native Ease easeInOut() /*-{
            return $wnd.easing.Sine.easeInOut;
        }-*/;

        /**
         * Eases out with slight deceleration.
         *
         * @return - Ease
         *
         */
        public static final native Ease easeOut () /*-{
            return $wnd.easing.Sine.easeOut;
        }-*/;

    }

}

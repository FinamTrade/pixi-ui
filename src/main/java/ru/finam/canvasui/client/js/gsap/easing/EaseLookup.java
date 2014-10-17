package ru.finam.canvasui.client.js.gsap.easing;

import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.JsObject;

/**
 * Created by ikusch on 27.08.14.
 *
 * EaseLookup enables you to find the easing function associated with a particular
 * name (String), like "strongEaseOut" which can be useful when loading in XML
 * data that comes in as Strings but needs to be translated to native function
 * references.
 *
 */
@JsType(isNative = true, prototype = "$wnd.easing.EaseLookup")
public interface EaseLookup extends JsObject {

    public static class Static {

        /**
         * Finds the easing function associated with a particular name (String), like "easeOutStrong".
         * This can be useful when loading in XML data that comes in as Strings but needs to be translated
         * to native function references. You can pass in the name with or without the period, and it
         * is case insensitive, so any of the following will find the Strong.easeOut function:
         *
         *  EaseLookup.find("Strong.easeOut")
         *  EaseLookup.find("easeOutStrong")
         *
         * You can translate strings directly when tweening, like this:
         *
         *  TweenLite.to(mc, 1, {x:100, ease:EaseLookup.find(myString)});
         *
         * @param name - The name of the easing function, with or without the period and case
         *  insensitive (i.e. "Strong.easeOut" or "easeOutStrong")
         *
         * @return - Ease
         *
         */
        public static final native Ease find(String name) /*-{
            return $wnd.easing.EaseLookup.find(name);
        }-*/;

    }

}

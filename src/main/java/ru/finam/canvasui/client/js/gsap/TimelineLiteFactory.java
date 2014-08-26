package ru.finam.canvasui.client.js.gsap;

import ru.finam.canvasui.client.js.pixi.JsObject;

/**
 * Created by ikusch on 22.08.14.
 */
public class TimelineLiteFactory {

    public static final native TimelineLite newInstance() /*-{
        return new $wnd.TimelineLite();
    }-*/;

    public static final native TimelineLite exportRoot(JsObject vars, boolean omitDelayedCalls) /*-{
        return new $wnd.TimelineLite.exportRoot(vars, omitDelayedCalls);
    }-*/;

}

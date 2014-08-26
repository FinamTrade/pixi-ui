package ru.finam.canvasui.client.js.gsap;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.pixi.JsObject;

/**
 * Created by ikusch on 25.08.14.
 */
@JsType(isNative = true, prototype = "$wnd.core.SimpleTimeline")
public interface SimpleTimeline extends Animation {

    @JsProperty(value = "autoRemoveChildren")
    boolean getAutoRemoveChildren();

    @JsProperty(value = "autoRemoveChildren")
    void setAutoRemoveChildren(boolean b);

    @JsProperty(value = "smoothChildTiming")
    boolean getSmoothChildTiming();

    @JsProperty(value = "smoothChildTiming")
    void setSmoothChildTiming(boolean b);

    SimpleTimeline add(JsObject child, JsObject position, String align, double stagger);

    void render(double time, boolean suppressEvents, boolean force);

}
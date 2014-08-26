package ru.finam.canvasui.client.js.gsap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.pixi.Array;
import ru.finam.canvasui.client.js.pixi.JsObject;

/**
 * Created by ikusch on 25.08.14.
 */
@JsType(isNative = true, prototype = "$wnd.core.Animation")
public interface Animation extends JsObject {

    @JsProperty(value = "data")
    void setData(JsObject data);

    @JsProperty(value = "data")
    JsObject getData();

    @JsProperty(value = "timeline")
    SimpleTimeline getTimeline();

    @JsProperty(value = "vars")
    JsObject getVars();

    Animation delay(Double value);

    Animation duration(double d);

    Animation eventCallback(String type, JsObject callback, Array<JsObject> params, JsObject scope);

    Animation invalidate();

    boolean isActive();

    Animation kill(JsObject vars, JsObject target);

    Animation pause(JsObject atTime, boolean suppressEvents);

    Animation paused(boolean value);

    Animation play(JsObject from, boolean suppressEvents);

    Animation progress(double value, boolean suppressEvents);

    Animation restart(boolean includeDelay , boolean suppressEvents);

    Animation resume(JsObject from, boolean suppressEvents);

    Animation reverse(JsObject from, boolean suppressEvents);

    Animation reversed(boolean value);

    Animation seek(JsObject time, boolean suppressEvents);

    Animation startTime(double value);

    Animation time(double value, boolean suppressEvents);

    Animation timeScale(double value);

    Animation totalDuration(double value);

    Animation totalProgress(double value, boolean suppressEvents);

    Animation totalTime(double value, boolean suppressEvents);

}
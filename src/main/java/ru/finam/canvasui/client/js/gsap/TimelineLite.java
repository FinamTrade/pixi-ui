package ru.finam.canvasui.client.js.gsap;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.js.impl.PrototypeOfJsType;
import com.google.gwt.user.client.Window;
import ru.finam.canvasui.client.js.pixi.Array;
import ru.finam.canvasui.client.js.pixi.JsObject;

/**
 * Created by ikusch on 22.08.14.
 */
@JsType(prototype = "$wnd.TimelineLite")
public interface TimelineLite extends SimpleTimeline {

    TimelineLite add(JsObject child, JsObject position, String align, double stagger);

    TimelineLite addLabel(String label, JsObject position);

    TimelineLite addPause(JsObject position, JsObject callback, Array<JsObject> params, JsObject scope);

    TimelineLite call(JsObject callback, Array<JsObject> params, JsObject scope, JsObject position);

    TimelineLite clear(boolean labels);

    TimelineLite duration(double d);

    TimelineLite from(JsObject object, double duration, JsObject vars);

    TimelineLite fromTo(JsObject object, double duration, JsObject fromVars, JsObject toVars, JsObject position);

    Array<Animation> getChildren(boolean neted, boolean tweens, boolean timelines, double ignoreBeforeTime);

    double getLabelTime(String label);

    Array<Animation> getTweensOf(JsObject target, boolean nested);

    TimelineLite invalidate();

    TimelineLite remove(JsObject value);

    JsObject removeLabel(String label);

    TimelineLite seek(JsObject position, boolean suppressEvents);

    TimelineLite set(JsObject target, JsObject vars, JsObject position);

    TimelineLite shiftChildren(double amount, boolean adjustLabels, double ignoreBeforeTime);


    TimelineLite staggerFrom(Array<JsObject> targets, double duration, JsObject vars, double stagger, JsObject position,
                             JsObject onCompleteAll, Array<JsObject> onCompleteAllParams, JsObject onCompleteScope);

    TimelineLite staggerFromTo(Array<JsObject> targets, double duration, JsObject fromVars, JsObject toVars, double stagger, JsObject position,
                             JsObject onCompleteAll, Array<JsObject> onCompleteAllParams, JsObject onCompleteScope);

    TimelineLite staggerTo(Array<JsObject> targets, double duration, JsObject vars, double stagger, JsObject position,
                               JsObject onCompleteAll, Array<JsObject> onCompleteAllParams, JsObject onCompleteScope);

    TimelineLite to(JsObject target, double duration, JsObject vars, JsObject position);

    TimelineLite totalDuration(double value);

    boolean useFrames();

}
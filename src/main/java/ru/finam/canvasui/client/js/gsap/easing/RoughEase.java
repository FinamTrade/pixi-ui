package ru.finam.canvasui.client.js.gsap.easing;

import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.JsObject;

/**
 * Created by ikusch on 27.08.14.
 *
 * Most easing equations give a smooth, gradual transition between the start and end values,
 * but RoughEase provides an easy way to get a rough, jagged effect instead, or you can also
 * get an evenly-spaced back-and-forth movement if you prefer. Configure the RoughEase by passing
 * an object to the constructor or config() method with any of the following properties (all are optional)
 *
 * clamp : Boolean - setting clamp to true will prevent points from exceeding the end
 *  value or dropping below the starting value. For example, if you're tweening the x property
 *  from 0 to 100, the RoughEase would force all random points to stay between 0 and 100 if
 *  clamp is true, but if it is false, x could potentially jump above 100 or below 0 at some point during the
 *  tween (it would always end at 100 though in this example) (Default: false).
 *
 * points : Number - the number of points to be plotted along the ease, making it jerk more or less frequently. (Default: 20)
 *
 * randomize : Boolean - by default, the placement of points will be randomized (creating the roughness)
 *  but you can set randomize to false to make the points zig-zag evenly across the ease. Using this in
 *  conjunction with a taper value can create a nice effect. (Default: true)
 *
 * strength : Number - controls how far from the template ease the points are allowed to wander (a small
 *  number like 0.1 keeps it very close to the template ease whereas a larger number like 5 creates much
 *  bigger variations). (Default: 1)
 *
 * taper : String - ("in" | "out" | "both" | "none") - to make the strength of the roughness taper towards
 *  the end or beginning or both, use "out", "in", or "both" respectively. (Default: "none")
 *
 * template : Ease - an ease that should be used as a template, like a general guide. The RoughEase will
 *  plot points that wander from that template. You can use this to influence the general shape of the
 *  RoughEase. (Default: Linear.easeNone)
 *
 */
@JsType(isNative = true, prototype = "$wnd.easing.RoughEase")
public interface RoughEase extends Ease {

    public static class Static {

        /**
         * The default ease instance which can be reused many times in various
         * tweens in order to conserve memory and improve performance slightly
         * compared to creating a new instance each time.
         */
        public static final native RoughEase ease() /*-{
            return $wnd.easing.RoughEase.ease;
        }-*/;

    }

    /**
     *
     * @param vars : Object (default = null) — a generic object with any of the
     * following properties (all are completely optional):
     *
     *      clamp : Boolean - setting clamp to true will prevent points from exceeding the end
     *          value or dropping below the starting value. For example, if you're tweening the x
     *          property from 0 to 100, the RoughEase would force all random points to stay between
     *          0 and 100 if clamp is true, but if it is false, x could potentially jump above 100 or
     *          below 0 at some point during the tween (it would always end at 100 though in this example) (Default: false).
     *
     *      points : Number - the number of points to be plotted along the ease, making it jerk more
     *          or less frequently. (Default: 20)
     *
     *      randomize : Boolean - by default, the placement of points will be randomized
     *          (creating the roughness) but you can set randomize to false to make the points
     *          zig-zag evenly across the ease. Using this in conjunction with a taper value can create a nice effect. (Default: true)
     *
     *      strength : Number - controls how far from the template ease the points are allowed to
     *          wander (a small number like 0.1 keeps it very close to the template ease whereas a
     *          larger number like 5 creates much bigger variations). (Default: 1)
     *
     *      taper : String - ("in" | "out" | "both" | "none") - to make the strength of the roughness
     *          taper towards the end or beginning or both, use "out", "in", or "both" respectively. (Default: "none")
     *
     *      template : Ease - an ease that should be used as a template, like a general guide. The RoughEase
     *          will plot points that wander from that template. You can use this to influence the general shape
     *          of the RoughEase. (Default: Linear.easeNone)
     *
     * @return - RoughEase
     *
     */
    public RoughEase config(JsObject vars);

    /**
     *
     * [override] Translates the tween's progress ratio into the corresponding ease ratio.
     *
     * @param p - progress ratio (a value between 0 and 1 indicating the progress of the tween/ease)
     *
     * @return - translated number
     *
     */
    public double getRatio(double p);

    public static class Factory {

        /**
         *
         * Constructor
         *
         * @param vars (default = null) — a generic object with any of the following
         *
         * properties (all are completely optional):
         *
         *      clamp : Boolean - setting clamp to true will prevent points from exceeding
         *          the end value or dropping below the starting value. For example,
         *          if you're tweening the x property from 0 to 100, the RoughEase would
         *          force all random points to stay between 0 and 100 if clamp is true, but
         *          if it is false, x could potentially jump above 100 or below 0 at some
         *          point during the tween (it would always end at 100 though in this example) (Default: false).
         *
         *      points : Number - the number of points to be plotted along the ease, making
         *          it jerk more or less frequently. (Default: 20)
         *
         *      randomize : Boolean - by default, the placement of points will be randomized
         *          (creating the roughness) but you can set randomize to false to make the points
         *          zig-zag evenly across the ease. Using this in conjunction with a taper value
         *          can create a nice effect. (Default: true)
         *
         *      strength : Number - controls how far from the template ease the points are allowed
         *          to wander (a small number like 0.1 keeps it very close to the template ease whereas
         *          a larger number like 5 creates much bigger variations). (Default: 1)
         *
         *      taper : String - ("in" | "out" | "both" | "none") - to make the strength of the roughness
         *          taper towards the end or beginning or both, use "out", "in", or "both" respectively. (Default: "none")
         *
         *      template : Ease - an ease that should be used as a template, like a general guide.
         *          The RoughEase will plot points that wander from that template. You can use this to influence
         *          the general shape of the RoughEase. (Default: Linear.easeNone)
         *
         * @return - RoughEase
         *
         */
        public static final native RoughEase construct(JsObject vars) /*-{
            return new $wnd.easing.RoughEase(vars);
        }-*/;

    }

}

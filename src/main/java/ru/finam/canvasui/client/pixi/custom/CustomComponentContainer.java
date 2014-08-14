package ru.finam.canvasui.client.pixi.custom;

import com.google.gwt.core.client.JavaScriptObject;
import ru.finam.canvasui.client.pixi.DisplayObject;
import ru.finam.canvasui.client.pixi.DisplayObjectContainer;

/**
 * Created by ikusch on 14.08.14.
 */
public class CustomComponentContainer extends DisplayObjectContainer implements UpdatableComponent {

    public static final double DEFAULT_ANIMATION_STEP = 0.1;

    protected CustomComponentContainer() {}

    public final native void setTargetAlpha(double a) /*-{
        this.targetAlpha = a;
    }-*/;

    public final native double getTargetAlpha() /*-{
        return this.targetAlpha;
    }-*/;

    public final native void setAnimationStep(double a) /*-{
        this.animationStep = a;
    }-*/;

    public final native double getAnimationStep() /*-{
        return this.animationStep;
    }-*/;

    public final native JavaScriptObject getUpdateFunction() /*-{
        return this.updateFunction;
    }-*/;

    public final native void setUpdateFunction( JavaScriptObject func ) /*-{
        this.updateFunction = func;
    }-*/;

    public final native JavaScriptObject newUpdateFunction() /*-{
        var d = this;
        var f = function(displayObject) {
            var aplhaDiff = displayObject.targetAlpha - displayObject.alpha;
            if (!displayObject.animationStep) {
                displayObject.animationStep = @ru.finam.canvasui.client.pixi.custom.CustomComponentContainer::DEFAULT_ANIMATION_STEP;
            }
            if (Math.abs(aplhaDiff) > Math.abs(displayObject.animationStep )
                && ( !displayObject.dragging)) {
                if (aplhaDiff > 0) {
                    displayObject.alpha += displayObject.animationStep
                }
                else
                    displayObject.alpha -= displayObject.animationStep;
            }
        }
        f.displayObject = d;
        return f;
    }-*/;

}

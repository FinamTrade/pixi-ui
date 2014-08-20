package ru.finam.canvasui.client.js.pixi.custom;

import com.google.gwt.core.client.JavaScriptObject;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.pixi.*;

/**
 * Created by ikusch on 19.08.14.
 */
public class CustomComponentContainer implements UpdatableComponent {

    public static final double DEFAULT_ANIMATION_STEP = 0.1;

    private Graphics mask;
    private Point position;

    protected CustomComponentContainer(DisplayObjectContainer displayObjectContainer) {
        displayObjectContainer(displayObjectContainer);
    }

    protected CustomComponentContainer() {
        this(DisplayObjectContainerFactory.newInstance());
    }

    protected final native void displayObjectContainer(DisplayObjectContainer displayObjectContainer) /*-{
        this.mainComponent = displayObjectContainer;
    }-*/;

    public final native DisplayObjectContainer displayObjectContainer() /*-{
        return this.mainComponent;
    }-*/;

    public final native void setTargetAlpha(double a) /*-{
        this.mainComponent.targetAlpha = a;
    }-*/;

    public final native double getTargetAlpha() /*-{
        return this.mainComponent.targetAlpha;
    }-*/;

    public final native void setAnimationStep(double a) /*-{
        this.mainComponent.animationStep = a;
    }-*/;

    public final native double getAnimationStep() /*-{
        return this.mainComponent.animationStep;
    }-*/;

    public final native JsObject getUpdateFunction() /*-{
        return this.mainComponent.updateFunction;
    }-*/;

    public final native void setUpdateFunction( JsObject func ) /*-{
        this.mainComponent.updateFunction = func;
    }-*/;

    public final native void setDragging( boolean b ) /*-{
        this.mainComponent.dragging = b;
    }-*/;

    public final native boolean isDragging() /*-{
        return this.mainComponent.dragging;
    }-*/;

    public final native JsObject newUpdateFunction() /*-{
        var d = this.mainComponent;
        var f = function(displayObject) {
            //console.log('UpdateFunction!');
            var aplhaDiff = displayObject.targetAlpha - displayObject.alpha;
            if (!displayObject.animationStep) {
                displayObject.animationStep = @ru.finam.canvasui.client.js.pixi.custom.CustomComponentContainer::DEFAULT_ANIMATION_STEP;
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

    public void addChild(DisplayObject child) {
        displayObjectContainer().addChild(child);
    }

    public void setMask(Graphics mask) {
        displayObjectContainer().setMask(mask);
    }

    public double getWidth() {
        return displayObjectContainer().getWidth();
    }

    public void setHitArea(Rectangle hitArea) {
        displayObjectContainer().setHitArea(hitArea);
    }

    public void setPosition(Point position) {
        displayObjectContainer().setPosition(position);
    }

    public void setAlpha(int alpha) {
        this.displayObjectContainer().setAlpha(alpha);
    }
}

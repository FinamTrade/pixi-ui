package ru.finam.canvasui.client.pixi;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 10.08.14
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */
public class DisplayObject extends JavaScriptObject {

    private static final double DEFAULT_ANIMATION_STEP = 0.1;

    protected DisplayObject() {};

    {
        setAnimationStep(DEFAULT_ANIMATION_STEP);
    }

    public final native Rectangle getBounds() /*-{
        return this.getBounds();
    }-*/;

    public final native Rectangle getLocalBounds() /*-{
        return this.getLocalBounds();
    }-*/;

    public final native void setMask(Graphics m) /*-{
       this.mask = m;
    }-*/;

    public final native Graphics getMask() /*-{
       return this.mask;
    }-*/;

    public final native Point getPosition() /*-{
        return this.position;
    }-*/;

    public final native void setPosition(Point p) /*-{
        this.position = p;
    }-*/;

    public final native void setInteractive(boolean b) /*-{
        this.interactive = b;
    }-*/;

    public final native void setButtonMode(boolean b) /*-{
        this.buttonMode  = b;
    }-*/;

    public final native void setAlpha(double a) /*-{
        this.alpha  = a;
    }-*/;

    public final native void setHitArea(Rectangle rect) /*-{
        this.hitArea = rect;
    }-*/;

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
                displayObject.animationStep = @ru.finam.canvasui.client.pixi.DisplayObject::DEFAULT_ANIMATION_STEP;
            }
            if (Math.abs(aplhaDiff) > Math.abs(displayObject.animationStep )
                && ( !displayObject.draging)) {
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

    public final void setPosition(double x, double y) {
        setPosition(Point.newInstance(x, y));
    }

    public final double getBoundedWidth() {
        return getBounds() == null ? 0 : ( getBounds().getWidth() + 2 * getBounds().getX() );
    }

    public final double getBoundedHeight() {
        return getBounds() == null ? 0 : ( getBounds().getHeight() + 2 * getBounds().getY() );
    }
}

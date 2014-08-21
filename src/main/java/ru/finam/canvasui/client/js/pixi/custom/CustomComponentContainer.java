package ru.finam.canvasui.client.js.pixi.custom;

import com.google.gwt.core.client.JavaScriptObject;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.pixi.*;

/**
 * Created by ikusch on 19.08.14.
 */
public class CustomComponentContainer {

    public static final double DEFAULT_ANIMATION_STEP = 0.1;

    private Graphics mask;
    private Point position;
    private double animationStep = DEFAULT_ANIMATION_STEP;
    private DisplayObjectContainer mainComponent;
    private double targetAlpha;
    private boolean dragging;

    protected CustomComponentContainer(DisplayObjectContainer mainComponent) {
        setMainComponent(mainComponent);
    }

    protected CustomComponentContainer() {
        this(DisplayObjectContainerFactory.newInstance());
    }

    protected void setMainComponent(DisplayObjectContainer mainComponent) {
        this.mainComponent = mainComponent;
    }

    public DisplayObjectContainer getMainComponent() {
        return this.mainComponent;
    }

    public void setTargetAlpha(double a) {
        this.targetAlpha = a;
    }

    public double getTargetAlpha() {
        return this.targetAlpha;
    }

    public void setAlpha(double a) {
        getMainComponent().setAlpha(a);
    }

    public double getAlpha() {
        return getMainComponent().getAlpha();
    }

    public void setAnimationStep(double a) {
        this.animationStep = a;
    }

    public double getAnimationStep() {
        return this.animationStep;
    }

    public JsObject getUpdateFunction(){
        return getMainComponent().getUpdateFunction();
    }

    public void setUpdateFunction( JsObject func ) {
        getMainComponent().setUpdateFunction(func);
    }

    public void setDragging( boolean b ) {
        this.dragging = b;
    }

    public boolean isDragging() {
        return this.dragging;
    }

    public JsObject newUpdateFunction() {
        return newUpdateFunction(this);
    }

    public final native JsObject newUpdateFunction(CustomComponentContainer that) /*-{
        var f = function() {
            that.@ru.finam.canvasui.client.js.pixi.custom.CustomComponentContainer::doUpdateFunction()();
        }
        return f;
    }-*/;

    public void doUpdateFunction() {
        double aplhaDiff = getTargetAlpha() - getAlpha();
        if (Math.abs(aplhaDiff) > Math.abs( getAnimationStep() )
                && ( !isDragging())) {
            if (aplhaDiff > 0) {
                setAlpha(getAlpha() + getAnimationStep());
            }
            else
                setAlpha(getAlpha() - getAnimationStep());
        }
    }

    public void addChild(DisplayObject child) {
        getMainComponent().addChild(child);
    }

    public void setMask(Graphics mask) {
        getMainComponent().setMask(mask);
    }

    public double getWidth() {
        return getMainComponent().getWidth();
    }

    public void setHitArea(Rectangle hitArea) {
        getMainComponent().setHitArea(hitArea);
    }

    public void setPosition(Point position) {
        getMainComponent().setPosition(position);
    }

    public void setAlpha(int alpha) {
        this.getMainComponent().setAlpha(alpha);
    }
}

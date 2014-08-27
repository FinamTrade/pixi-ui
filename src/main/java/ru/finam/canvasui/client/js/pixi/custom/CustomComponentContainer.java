package ru.finam.canvasui.client.js.pixi.custom;

import com.google.gwt.core.client.JavaScriptObject;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.gsap.TimelineLite;
import ru.finam.canvasui.client.js.pixi.*;

/**
 * Created by ikusch on 19.08.14.
 */
public class CustomComponentContainer {

    public static final double DEFAULT_ANIMATION_STEP = 0.1;

    private Graphics mask;
    private Point position;
    private DisplayObjectContainer mainComponent;
    private boolean dragging;
    private TimelineLite timeline;

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

    public TimelineLite timeline() {
        if (timeline == null)
            timeline = TimelineLite.Factory.newInstance();
        return timeline;
    }

    public void setAlpha(double a) {
        getMainComponent().setAlpha(a);
    }

    public double getAlpha() {
        return getMainComponent().getAlpha();
    }

    public void setDragging( boolean b ) {
        this.dragging = b;
    }

    public boolean isDragging() {
        return this.dragging;
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

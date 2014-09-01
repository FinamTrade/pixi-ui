package ru.finam.canvasui.client.js.pixi.custom;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.Array;
import ru.finam.canvasui.client.js.gsap.TimelineLite;
import ru.finam.canvasui.client.js.pixi.*;

/**
 * Created by ikusch on 19.08.14.
 */
public class CustomComponentContainer extends CustomComponent<DisplayObjectContainer> {

    public static final double DEFAULT_ANIMATION_STEP = 0.1;
    private boolean dragging;
    private TimelineLite timeline;

    protected CustomComponentContainer(DisplayObjectContainer mainComponent) {
        super(mainComponent);
    }

    protected CustomComponentContainer() {
        this(DisplayObjectContainer.Factory.newInstance());
    }

    public TimelineLite timeline() {
        if (timeline == null)
            timeline = TimelineLite.Factory.newInstance();
        return timeline;
    }

    public double getBoundedWidth() {
        return getBounds() == null ? 0 : ( getBounds().getWidth() + 2 * getBounds().getX() );
    }

    public double getBoundedHeight() {
        return getBounds() == null ? 0 : ( getBounds().getHeight() + 2 * getBounds().getY() );
    }

    /*
    public void setAlpha(double a) {
        getMainComponent().setAlpha(a);
    }

    public double getAlpha() {
        return getMainComponent().getAlpha();
    }

    public Rectangle getBounds() {
        return getMainComponent().getBounds();
    }

    public void setDragging( boolean b ) {
        this.dragging = b;
    }

    public boolean isDragging() {
        return this.dragging;
    }


    public void setMask(Graphics mask) {
        getMainComponent().setMask(mask);
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

    public Point getPosition() {
        return this.getMainComponent().getPosition();
    }

    public Rectangle getLocalBounds() {
        return this.getMainComponent().getLocalBounds();
    }
    */

    public double getWidth() {
        return getMainComponent().getWidth();
    }

    public void setWidth(double width) {
        getMainComponent().setWidth(width);
    }

    public void setHeight(double height) {
        getMainComponent().setHeight(height);
    }

    public double getHeight() {
        return this.getMainComponent().getHeight();
    }

    public void addChild(DisplayObject child) {
        getMainComponent().addChild(child);
    }

    public void addChild(CustomComponentContainer child) {
        getMainComponent().addChild(child.getMainComponent());
    }

    public void removeChild(DisplayObject displayObject) {
        this.getMainComponent().removeChild(displayObject);
    }

    public void removeChild(CustomComponentContainer customComponentContainer) {
        this.getMainComponent().removeChild(customComponentContainer.getMainComponent());
    }

    public DisplayObject getChildren(int i) {
        return Array.Static.<DisplayObject>get(i, getChildren());
    }

    public Array<DisplayObject> getChildren() {
        return getMainComponent().getChildren();
    }

    public void setChildren(Array<DisplayObject> children) {
        getMainComponent().setChildren(children);
    }

}

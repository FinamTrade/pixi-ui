package ru.finam.canvasui.client.js.pixi.custom.panel;

import ru.finam.canvasui.client.js.gsap.TimelineLite;
import ru.finam.canvasui.client.js.pixi.*;

/**
 * Created by ikusch on 28.08.2014.
 */
public class CustomComponentImpl<T extends DisplayObject> implements CustomComponent<T> {

    private T mainComponent;
    private boolean dragging;
    private TimelineLite timeline;

    protected CustomComponentImpl(T mainComponent) {
        setMainComponent(mainComponent);
    }

    protected void setMainComponent(T mainComponent) {
        this.mainComponent = mainComponent;
    }

    public T getMainComponent() {
        return this.mainComponent;
    }

    public TimelineLite timeline() {
        if (timeline == null)
            timeline = TimelineLite.Factory.newInstance();
        return timeline;
    }

    public void setDragging( boolean b ) {
        this.dragging = b;
    }

    public boolean isDragging() {
        return this.dragging;
    }

    public void setAlpha(double a) {
        getMainComponent().setAlpha(a);
    }

    public double getAlpha() {
        return getMainComponent().getAlpha();
    }

    public Rectangle getBounds() {
        return getMainComponent().getBounds();
    }

    public void setMask(Graphics mask) {
        getMainComponent().setMask(mask);
    }

    public Graphics getMask() {
        return getMainComponent().getMask();
    }

    public void setHitArea(Rectangle hitArea) {
        getMainComponent().setHitArea(hitArea);
    }

    public Rectangle getHitArea() {
        return getMainComponent().getHitArea();
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

    public static double getBoundedWidth(DisplayObject displayObject) {
        return displayObject.getBounds() == null ? 0 : ( displayObject.getBounds().getWidth() + 2 * displayObject.getBounds().getX() );
    }

    public static double getBoundedHeight(DisplayObject displayObject) {
        return displayObject.getBounds() == null ? 0 : ( displayObject.getBounds().getHeight() + 2 * displayObject.getBounds().getY() );
    }

    public double getBoundedWidth() {
        return getBounds() == null ? 0 : ( getBounds().getWidth() + 2 * getBounds().getX() );
    }

    public double getBoundedHeight() {
        return getBounds() == null ? 0 : ( getBounds().getHeight() + 2 * getBounds().getY() );
    }

    public Rectangle getLocalBounds() {
        return this.getMainComponent().getLocalBounds();
    }

    public DisplayObjectContainer getParent() {
        return getMainComponent().getParent();
    }

    public void setInteractive(boolean b) {
        getMainComponent().setInteractive(b);
    }

    public void setButtonMode(boolean b) {
        getMainComponent().setButtonMode(b);
    }

    public boolean getButtonMode() {
        return false;
    }

    public boolean getInteractive() {
        return getMainComponent().getInteractive();
    }

    public Rectangle croppedBounds() {
        return Rectangle.Factory.newInstance(0, 0, (int) getBoundedWidth(), (int) getBoundedHeight());
    }

}

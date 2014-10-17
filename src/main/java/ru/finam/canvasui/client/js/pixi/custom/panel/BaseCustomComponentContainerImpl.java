package ru.finam.canvasui.client.js.pixi.custom.panel;

import ru.finam.canvasui.client.js.Array;
import ru.finam.canvasui.client.js.JsObject;
import ru.finam.canvasui.client.js.gsap.TimelineLite;
import ru.finam.canvasui.client.js.pixi.*;

/**
 * Created by ikusch on 19.08.14.
 */
public class BaseCustomComponentContainerImpl<E extends DisplayObjectContainer> extends CustomComponentImpl<E> implements BaseCustomComponentContainer<E> {

    private TimelineLite timeline;

    protected BaseCustomComponentContainerImpl(E mainComponent) {
        super(mainComponent);
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

    public void addChild(CustomComponent child) {
        getMainComponent().addChild(child.getMainComponent());
    }

    public void removeChild(BaseCustomComponentContainer customComponentContainer) {
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

    public void removeChild(DisplayObject displayObject) {
        this.getMainComponent().removeChild(displayObject);
    }

    public void removeChild(CustomComponentContainer customComponentContainer) {
        this.getMainComponent().removeChild(customComponentContainer.getMainComponent());
    }

    public void clear() {
        Array<DisplayObject> childrens = getChildren();
        while (childrens.getLength() > 0) {
            DisplayObject child = getArrayEl(childrens, 0);
            getMainComponent().removeChild(child);
        }
    }

    protected final native <T extends JsObject> T getArrayEl(Array<T> array, int i) /*-{
        return array[i];
    }-*/;

    public void setVisible(boolean visible) {
        getMainComponent().setVisible(visible);
    }

    public boolean getVisible() {
        return getMainComponent().getVisible();
    }

}

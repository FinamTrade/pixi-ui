package ru.finam.canvasui.client.js.pixi.custom.panel;

import ru.finam.canvasui.client.js.Array;
import ru.finam.canvasui.client.js.JsObject;
import ru.finam.canvasui.client.js.gsap.TimelineLite;
import ru.finam.canvasui.client.js.pixi.DisplayObject;
import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;

/**
 * Created by ikusch on 14.10.2014.
 */
public interface BaseCustomComponentContainer<E extends DisplayObjectContainer> extends CustomComponent<E> {

    TimelineLite timeline();

    double getBoundedWidth();

    double getBoundedHeight();

    double getWidth();

    void setWidth(double width);

    void setHeight(double height);

    double getHeight();

    void addChild(DisplayObject child);

    void addChild(CustomComponent child);

    void removeChild(BaseCustomComponentContainer customComponentContainer);

    DisplayObject getChildren(int i);

    Array<DisplayObject> getChildren();

    void setChildren(Array<DisplayObject> children);

    void removeChild(DisplayObject displayObject);

    void removeChild(CustomComponentContainer customComponentContainer);

    void clear();

    void setVisible(boolean visible);

    boolean getVisible();

}

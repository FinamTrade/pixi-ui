package ru.finam.canvasui.client.js.pixi.custom;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.JsObject;
import ru.finam.canvasui.client.js.pixi.DisplayObject;
import ru.finam.canvasui.client.js.pixi.MouseEvent;
import ru.finam.canvasui.client.js.pixi.Point;

/**
 * Created by ikusch on 19.08.14.
 */
@JsType
public interface TouchEvent extends JsObject {

    @JsProperty(value = "parent")
    DisplayObject getParent();

    @JsProperty(value = "data")
    void setData(MouseEvent data);

    @JsProperty(value = "data")
    MouseEvent getData();

    @JsProperty(value = "dragging")
    void setDragging(boolean b);

    @JsProperty(value = "dragging")
    boolean getDragging();

    @JsProperty(value = "position")
    Point getPosition();

}

package ru.finam.canvasui.client.pixi.custom;

import com.google.gwt.core.client.JavaScriptObject;
import ru.finam.canvasui.client.pixi.DisplayObject;
import ru.finam.canvasui.client.pixi.MouseEvent;
import ru.finam.canvasui.client.pixi.Point;

/**
 * Created by ikusch on 14.08.14.
 */
public class TouchEvent extends JavaScriptObject {

    protected TouchEvent() {}

    public final native DisplayObject getParent() /*-{
        return this.parent;
    }-*/;

    public final native void setData(MouseEvent data) /*-{
        this.data = data;
    }-*/;

    public final native MouseEvent getData() /*-{
        return this.data;
    }-*/;

    public final native void setDragging(boolean b) /*-{
        this.dragging = b;
    }-*/;

    public final native boolean isDragging() /*-{
        return this.dragging;
    }-*/;

    public final native Point getPosition() /*-{
        return this.position;
    }-*/;

}

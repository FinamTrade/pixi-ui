package ru.finam.canvasui.client.pixi;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by ikusch on 14.08.14.
 */
public class MouseEvent extends JavaScriptObject {

    protected MouseEvent() {}

    public final native MouseEvent getOriginalEvent() /*-{
        return this.originalEvent;
    }-*/;

    public final native void preventDefault() /*-{
        this.preventDefault();
    }-*/;

    public final native Point getLocalPosition(DisplayObject object) /*-{
        return this.getLocalPosition(object);
    }-*/;

}

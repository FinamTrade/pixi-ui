package ru.finam.canvasui.client.pixi;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by ikusch on 13.08.14.
 */
public class BaseTexture extends JavaScriptObject {

    protected BaseTexture() {}

    public final native boolean hasLoaded() /*-{
        return this.hasLoaded;
    }-*/;

}

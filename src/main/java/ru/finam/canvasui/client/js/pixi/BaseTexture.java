package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import java_cup.version;

/**
 * Created by ikusch on 19.08.14.
 */
@JsType(prototype = "$wnd.PIXI.BaseTexture")
public interface BaseTexture {

    @JsProperty(value = "hasLoaded")
    boolean isHasLoaded();

    @JsProperty(value = "hasLoaded")
    void setHasLoaded(boolean b);

}

package ru.finam.canvasui.client.tests.js;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.pixi.DisplayObject;

/**
 * Created by ikusch on 15.08.14.
 */
@JsType(prototype = "PIXI.DisplayObjectContainer")
public interface PixiDisplayObjectContainer extends PixiDisplayObject {

    void addChild(PixiDisplayObject child);

    @JsProperty
    JsArray<DisplayObject> getChildrens();

    @JsProperty
    int getWidth();

    @JsProperty
    int getHeight();

    void setWidth(int w);

    int setHeight(int h);

}

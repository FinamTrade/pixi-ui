package ru.finam.canvasui.client.tests.js;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

/**
 * Created by ikusch on 15.08.14.
 */
@JsType(prototype = "PIXI.Stage")
public interface PixiStage extends PixiDisplayObjectContainer {

    @JsProperty
    int getBackgroundColor();

}

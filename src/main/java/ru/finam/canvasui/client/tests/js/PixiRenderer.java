package ru.finam.canvasui.client.tests.js;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Node;

/**
 * Created by ikusch on 15.08.14.
 */
@JsType(prototype = "$wnd.PIXI.autoDetectRenderer")
public interface PixiRenderer {

    @JsProperty(value = "view")
    public Node getView();

    @JsProperty(value = "view")
    void setView(Node node);

    void render(PixiStage stage);

}
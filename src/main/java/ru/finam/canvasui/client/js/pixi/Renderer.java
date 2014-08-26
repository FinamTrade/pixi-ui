package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Node;

/**
 * Created by ikusch on 19.08.14.
 *
 * Pixi.js autoDetectRenderer object for GWT.
 */
@JsType(isNative = true, prototype = "$wnd.PIXI.autoDetectRenderer")
public interface Renderer extends JsObject {

    @JsProperty(value = "view")
    Node getView();

    @JsProperty(value = "view")
    void setView(Node node);

    void render(Stage stage);

    @JsProperty(value = "clearBeforeRender")
    void setClearBeforeRender(boolean b);

    @JsProperty(value = "clearBeforeRender")
    boolean getClearBeforeRender();

}

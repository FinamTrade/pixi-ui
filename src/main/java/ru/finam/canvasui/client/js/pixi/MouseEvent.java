package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.JsObject;

/**
 * Created by ikusch on 19.08.14.
 */
@JsType
public interface MouseEvent extends JsObject {

    @JsProperty(value = "originalEvent")
    MouseEvent getOriginalEvent();

    void preventDefault();

    Point getLocalPosition(DisplayObject object);
}

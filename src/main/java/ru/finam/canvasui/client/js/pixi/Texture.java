package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.js.JsObject;

/**
 * Created by ikusch on 19.08.14.
 */
@JsType(prototype = "$wnd.PIXI.Texture")
public interface Texture extends JsObject {

    @JsProperty(value = "width")
    double getWidth();

    @JsProperty(value = "width")
    void setWidth(double width);

    @JsProperty(value = "height")
    double getHeight();

    @JsProperty(value = "height")
    void setHeight(double height);

    @JsProperty(value = "baseTexture")
    BaseTexture getBaseTexture();

    @JsProperty(value = "baseTexture")
    void setBaseTexture(BaseTexture baseTexture);

}

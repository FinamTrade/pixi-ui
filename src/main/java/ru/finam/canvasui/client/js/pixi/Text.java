package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.js.JsType;

/**
 * Created by ikusch on 27.08.14.
 *
 * A Text Object will create a line(s) of text. To split a line you can use '\n'
 * or add a wordWrap property set to true and and wordWrapWidth property with a
 * value in the style object
 *
 */
@JsType(prototype = "$wnd.PIXI.Text")
public interface Text extends Sprite {

    /**
     * Destroys this text object
     *
     * @param destroyTexture
     */
    void destroy(boolean destroyTexture);

    /**
     *
     * Set the style of the text
     *
     * @param style - The style parameters
     */
    void setStyle(TextStyle style);

    /**
     *
     * Set the copy for the text object. To split a line you can use '\n'
     *
     * @param text
     */
    void setText(String text);

    public static class Factory {

        public static final native Text construct(String text, TextStyle style) /*-{
            return new $wnd.PIXI.Text(text, style);
        }-*/;

        public static final native Text construct(String text) /*-{
            return new $wnd.PIXI.Text(text);
        }-*/;

    }

}

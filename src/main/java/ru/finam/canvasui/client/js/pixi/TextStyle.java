package ru.finam.canvasui.client.js.pixi;

import ru.finam.canvasui.client.js.JsObject;

/**
 * Created by ikusch on 27.08.14.
 */
public class TextStyle implements JsObject {

    public static final native TextStyle newInstance(String font, String fill, String align, String stroke, double strokeThickness, boolean wordWrap, double wordWrapWidth) /*-{
        return {
            font: font,
            fill: fill,
            align: align,
            stroke: stroke,
            strokeThickness: strokeThickness,
            wordWrap: wordWrap,
            wordWrapWidth: wordWrapWidth
        };
    }-*/;

    public static final native TextStyle newInstance(String font, String fill, String align, String stroke) /*-{
        return {
            font: font,
            fill: fill,
            align: align,
            stroke: stroke
        };
    }-*/;

}

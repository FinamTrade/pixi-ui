package ru.finam.canvasui.client.js.gsap;

import ru.finam.canvasui.client.js.JsObject;

/**
 * Created by ikusch on 22.08.14.
 */
public class PropertiesSet {

    private JsObject jsObject = newJsObject();

    public PropertiesSet() {}

    private final native JsObject newJsObject() /*-{
        return {};
    }-*/;

    public PropertiesSet addKeyValue(String key, String value) {
        addKeyValue(jsObject, key, value);
        return this;
    }

    public PropertiesSet addKeyValue(String key, JsObject value) {
        addKeyValue(jsObject, key, value);
        return this;
    }

    public PropertiesSet addKeyValue(String key, double value) {
        addKeyValue(jsObject, key, value);
        return this;
    }

    public JsObject getJsObject() {
        return jsObject;
    }

    private final native PropertiesSet addKeyValue(JsObject obj, String key, String value) /*-{
        obj[key] = value;
        return obj;
    }-*/;

    private final native PropertiesSet addKeyValue(JsObject obj, String key, double value) /*-{
        obj[key] = value;
        return obj;
    }-*/;

    private final native PropertiesSet addKeyValue(JsObject obj, String key, JsObject value) /*-{
        obj[key] = value;
        return obj;
    }-*/;

    public double doubleKeyValue(String key) {
        return doubleKeyValue(jsObject, key);
    }

    private final native double doubleKeyValue(JsObject obj, String key) /*-{
        return obj[key];
    }-*/;

}

package ru.finam.canvasui.client.js.pixi;

/**
 * Created by ikusch on 19.08.14.
 */
public class GraphicsFactory {

    public static native Graphics newInstance() /*-{
        return new $wnd.PIXI.Graphics();
    }-*/;

}

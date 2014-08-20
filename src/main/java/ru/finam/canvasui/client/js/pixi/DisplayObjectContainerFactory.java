package ru.finam.canvasui.client.js.pixi;

/**
 * Created by ikusch on 19.08.14.
 */
public class DisplayObjectContainerFactory {

    public static native DisplayObjectContainer newInstance() /*-{
        return new $wnd.PIXI.DisplayObjectContainer();
    }-*/;

}

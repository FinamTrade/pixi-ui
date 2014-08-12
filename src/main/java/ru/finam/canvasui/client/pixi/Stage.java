package ru.finam.canvasui.client.pixi;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 08.08.14
 * Time: 22:13
 * To change this template use File | Settings | File Templates.
 */
public class Stage extends DisplayObjectContainer {

    protected static final int DEAFULT_BG = 0xFFFF00;

    protected Stage() {}

    public static native Stage newInstance(int bgColor, boolean isInteractive) /*-{
        return new $wnd.PIXI.Stage(bgColor, isInteractive);
    }-*/;

    public static Stage newInstance(int bgColor) {
        return newInstance(bgColor, false);
    }

    public static Stage newInstance() {
        return newInstance(DEAFULT_BG, false);
    }

}
package ru.finam.canvasui.client.js.pixi;

/**
 * Created by ikusch on 19.08.14.
 */
public class StageFactory {

    private static final int DEAFULT_BG = 0xFFFF00;

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
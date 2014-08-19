package ru.finam.canvasui.client.tests.js;

/**
 * Created by ikusch on 15.08.14.
 */
public abstract class LayoutedPixiStage implements PixiStage {

    public static native PixiStage newInstance(int bgColor, boolean isInteractive) /*-{
        return new $wnd.PIXI.Stage(bgColor, isInteractive);
    }-*/;

}

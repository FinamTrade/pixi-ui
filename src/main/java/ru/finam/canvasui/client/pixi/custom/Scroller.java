package ru.finam.canvasui.client.pixi.custom;

import ru.finam.canvasui.client.pixi.DisplayObjectContainer;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 11.08.14
 * Time: 20:09
 * To change this template use File | Settings | File Templates.
 */
public class Scroller extends DisplayObjectContainer {

    protected Scroller() {}

    public static native Scroller newInstance(double pixelLength) /*{-
    -}*/;

}

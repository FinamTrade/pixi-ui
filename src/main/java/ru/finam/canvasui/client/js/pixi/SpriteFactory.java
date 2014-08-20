package ru.finam.canvasui.client.js.pixi;

/**
 * Created by ikusch on 19.08.14.
 */
public class SpriteFactory {

    public static native Sprite newInstance(Texture texture) /*-{
        return new $wnd.PIXI.Sprite(texture);
    }-*/;

}

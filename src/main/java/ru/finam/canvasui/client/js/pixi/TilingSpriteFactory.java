package ru.finam.canvasui.client.js.pixi;

/**
 * Created by ikusch on 19.08.14.
 */
public class TilingSpriteFactory {

    public static native TilingSprite newInstance(Texture texture, int width, int height) /*-{
        return new $wnd.PIXI.TilingSprite(texture, width, height);
    }-*/;

}

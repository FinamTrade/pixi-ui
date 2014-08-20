package ru.finam.canvasui.client.js.pixi;

import ru.finam.canvasui.client.pixi.*;

/**
 * Created by ikusch on 19.08.14.
 */
public class TilingSpriteFactory {

    public static native TilingSprite newInstance(ru.finam.canvasui.client.pixi.Texture texture, int width, int height) /*-{
        return new $wnd.PIXI.TilingSprite(texture, width, height);
    }-*/;

}

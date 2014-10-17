package ru.finam.canvasui.client.js.pixi;

/**
 * Created by ikusch on 19.08.14.
 */
public class TextureFactory {

    public static native Texture fromImage(String imageUrl, boolean crossorigin) /*-{
        return new $wnd.PIXI.Texture.fromImage(imageUrl, crossorigin);
    }-*/;

    public static Texture fromImage(String imageUrl) {
        return fromImage(imageUrl, false);
    }

}

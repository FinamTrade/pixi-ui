package ru.finam.canvasui.client.pixi;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 10.08.14
 * Time: 13:18
 * To change this template use File | Settings | File Templates.
 */
public class Texture extends JavaScriptObject {

    protected Texture() {}

    public static native Texture fromImage(String imageUrl, boolean crossorigin) /*-{
        return new $wnd.PIXI.Texture.fromImage(imageUrl, crossorigin);
    }-*/;

    public static Texture fromImage(String imageUrl) {
        return fromImage(imageUrl, false);
    }

    public final native int getWidth() /*-{
        return this.width;
    }-*/;

    public final native int getHeight() /*-{
        return this.height;
    }-*/;

    public final native BaseTexture getBaseTexture() /*-{
        return this.baseTexture;
    }-*/;

}

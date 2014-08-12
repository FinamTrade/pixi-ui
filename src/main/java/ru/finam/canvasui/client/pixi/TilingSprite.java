package ru.finam.canvasui.client.pixi;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 10.08.14
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public class TilingSprite extends DisplayObjectContainer {

    protected TilingSprite() {}

    public static native TilingSprite newInstance(Texture texture, int width, int height) /*-{
        return new $wnd.PIXI.TilingSprite(texture, width, height);
    }-*/;

    public static native TilingSprite newInstance(String path, int width, int height) /*-{
        var texture = $wnd.PIXI.Texture.fromImage("img/city1.png");
	    var ts = new $wnd.PIXI.TilingSprite(texture, 512, 256);
	    ts.position.x = 0;
	    ts.position.y = 128;
	    ts.tilePosition.x = 0;
	    ts.tilePosition.y = 0;
	    return ts;
    }-*/;

}

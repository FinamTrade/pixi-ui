package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.dom.client.Node;

/**
 * Created by ikusch on 19.08.14.
 */
public class RendererFactory {

    public static native Renderer autoDetectRenderer(double width, double height,
                                                     Node canvas, boolean transparent, boolean antialias) /*-{
        return new $wnd.PIXI.autoDetectRenderer(width, height, canvas, transparent, antialias);
    }-*/;

    public static native Renderer autoDetectRenderer(double width, double height) /*-{
        return new $wnd.PIXI.autoDetectRenderer(width, height);
    }-*/;

    public static native Renderer newCanvasRenderer(double width, double height) /*-{
        return new $wnd.PIXI.CanvasRenderer(width, height);
    }-*/;

    public static Renderer autoDetectRenderer(int width, int height, boolean clearBeforeRender) {
        Renderer renderer = autoDetectRenderer(width, height);
        renderer.setClearBeforeRender(clearBeforeRender);
        return renderer;
    }

    public static Renderer autoDetectRenderer(int width, int height,
                                              Node canvas, boolean transparent,
                                              boolean antialias, boolean clearBeforeRender) {
        Renderer renderer = autoDetectRenderer(width, height, canvas, transparent, antialias);
        renderer.setClearBeforeRender(clearBeforeRender);
        return renderer;
    }

}

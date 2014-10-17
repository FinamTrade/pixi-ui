package ru.finam.canvasui.client.tests;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Created by ikusch on 15.08.14.
 */
public class PixiScrollerTest5 extends PixiScrollerTest {

    public static final String NAME = "Test5";

    public static void createRender(String rendererContainerId) {
        RootPanel element = RootPanel.get(rendererContainerId);
        element.getElement().getStyle().setPadding(0, Style.Unit.PX);
        element.clear(true);
        Renderer2 renderer2 = new Renderer2();
        Window.alert("renderer2.getView() = " + renderer2.getView());
        RootPanel.get().getElement().appendChild(renderer2.getView());

    }

    private static class Renderer2 implements TRenderer {

        TRenderer r;

        Renderer2() {
            r = autoDetectRenderer(200, 500);
        }

        public Node getView() {
            return r.getView();
        }

    }

    private static native TRenderer autoDetectRenderer(double x, double y) /*-{
        console.log('hi!');
        var t = new $wnd.PIXI.autoDetectRenderer(x, y);
        console.log('hi! t = ' + t);
        return t;
    }-*/;

    @JsType(prototype = "$wnd.PIXI.autoDetectRenderer")
    public interface TRenderer {

        @JsProperty(value = "view")
        public Node getView();

    }

    @Override
    public void fillStage(int width, int height, String... images) {
    }

}
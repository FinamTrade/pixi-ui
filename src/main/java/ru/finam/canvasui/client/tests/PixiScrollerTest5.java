package ru.finam.canvasui.client.tests;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;

/**
 * Created by ikusch on 15.08.14.
 */
public class PixiScrollerTest5 extends PixiScrollerTest {

    public static void createRender(String rendererContainerId) {
        RootPanel element = RootPanel.get(rendererContainerId);
        element.getElement().getStyle().setPadding(0, Style.Unit.PX);
        element.clear(true);
        int width = element.getElement().getOffsetWidth();
        int height = element.getElement().getClientHeight();
        JsConsole.log("Renderer width = " + width);
        JsConsole.log("Renderer height = " + height);
        //PixiRenderer renderer = PixiRendererFactory.autoDetectRenderer(width, height);

        /*
        CustomRenderer renderer = new CustomRenderer(width, height);
        JsConsole.log("Renderer = " + renderer);
        JsConsole.log("CustomRenderer.getView() = " + renderer.getView());
        element.getElement().appendChild(renderer.getView());

        PixiStage stage = LayoutedPixiStage.newInstance(0xFFFF00, true);
        JsConsole.log("stage = " + stage);
        JsConsole.log("stage.getBackgroundColor = " + stage.getBackgroundColor());
        //ScrollPanel scrollPanel = fixedSizeScrollPanel(newSampleImage(images[1]));
        //stage.addChildToCenter(scrollPanel, width, height);

        PixiGraphics pixiGraphics = PixiGraphicsFactory.newInstance();
        //pixiGraphics.beginFill(0xFF0000, 1);

        renderer.render(stage);
        */

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
    LayoutedStage newTestStage(int width, int height, String... images) {
        return null;
    }

    public String name() {
        return "Test5";
    }

}
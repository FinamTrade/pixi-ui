package ru.finam.canvasui.client.tests.js;

import com.google.gwt.dom.client.Node;

/**
 * Created by ikusch on 18.08.14.
 */
public class CustomRenderer implements PixiRenderer {

    private PixiRenderer renderer;

    public CustomRenderer(double width, double height) {
        renderer = PixiRendererFactory.autoDetectRenderer(width, height);
    }

    public void render2(PixiStage stage, boolean b) {
        renderer.render(stage);
    }

    @Override
    public Node getView() {
        return renderer.getView();
    }

    @Override
    public void setView(Node node) {
        renderer.setView(node);
    }

    @Override
    public void render(PixiStage stage) {
        renderer.render(stage);
    }
}

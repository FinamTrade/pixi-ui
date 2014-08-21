package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Created by ikusch on 19.08.14.
 */
public class UpdatableRenderer implements Renderer {

    final Renderer renderer;

    protected UpdatableRenderer(Renderer renderer) {
        this.renderer = renderer;
        initRenderer(renderer);
    }

    protected final void initRenderer(Renderer renderer) {
        setView(renderer.getView());
        setClearBeforeRender(renderer.getClearBeforeRender());
    }

    @Override
    public Node getView() {
        return null;
    }

    @Override
    public void setView(Node node) {

    }

    @Override
    public void render(Stage stage) {
        renderer.render(stage);
    }

    @Override
    public void setClearBeforeRender(boolean b) {

    }

    @Override
    public boolean getClearBeforeRender() {
        return false;
    }

    public static UpdatableRenderer newInstance(double x, double y) {
        Renderer tRenderer = RendererFactory.autoDetectRenderer(x, y);
        return new UpdatableRenderer(tRenderer);
    }

    public static UpdatableRenderer addNewAuoDetectRenderer(RootPanel element, int width, int height) {
        UpdatableRenderer renderer = newInstance(width, height);
        //Renderer renderer = RendererFactory.autoDetectRenderer(width, height);
        element.getElement().appendChild(renderer.getView());
        return renderer;
    }

}

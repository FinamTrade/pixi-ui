package ru.finam.canvasui.client.js.pixi;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.Node;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import ru.finam.canvasui.client.js.pixi.custom.channel.MouseWheelEventChannel;

/**
 * Created by ikusch on 19.08.14.
 */
public class UpdatableRenderer implements Renderer {

    private static final String UPGRADE_MESSGAE = "Your browser does not support the HTML5 Canvas. " +
            "Please upgrade your browser to view this page.";

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

    private HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler, Widget widget) {
        return widget.addDomHandler(handler, MouseWheelEvent.getType());
    }

    public static UpdatableRenderer addNewAuoDetectRenderer(RootPanel element, int width, int height) {
        Canvas canvas = Canvas.createIfSupported();
        if (canvas == null) {
            element.add(new Label(UPGRADE_MESSGAE));
            return null;
        }
        canvas.setCoordinateSpaceHeight(height);
        canvas.setCoordinateSpaceWidth(width);
        canvas.setWidth(width + "px");
        canvas.setHeight(height + "px");
        canvas.addMouseWheelHandler(new MouseWheelHandler() {
            @Override
            public void onMouseWheel(MouseWheelEvent mouseWheelEvent) {
                MouseWheelEventChannel.fireNew(mouseWheelEvent);
            }
        });
        element.add(canvas);
        return addNewAuoDetectRenderer(element, width, height, canvas.getElement(), false, false);
    }

    public static UpdatableRenderer addNewAuoDetectRenderer(RootPanel element, int width, int height, Node canvas, boolean transparent, boolean antialias) {
        Renderer tRenderer = RendererFactory.autoDetectRenderer(width, height, canvas, transparent, antialias);
        UpdatableRenderer renderer =  new UpdatableRenderer(tRenderer);
        element.getElement().appendChild(renderer.getView());
        return renderer;
    }

}

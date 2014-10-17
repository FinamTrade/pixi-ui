package ru.finam.canvasui.client.js.pixi.custom;

import ru.finam.canvasui.client.js.Array;
import ru.finam.canvasui.client.js.pixi.*;
import ru.finam.canvasui.client.js.pixi.DisplayObject;
import ru.finam.canvasui.client.js.pixi.Graphics;
import ru.finam.canvasui.client.js.pixi.Rectangle;
import ru.finam.canvasui.client.js.pixi.Stage;
import ru.finam.canvasui.client.js.pixi.custom.panel.BaseCustomComponentContainerImpl;
import ru.finam.canvasui.client.js.pixi.custom.panel.CustomComponent;
import ru.finam.canvasui.client.js.pixi.custom.panel.CustomComponentContainer;

/**
 * Created by ikusch on 19.08.14.
 */
public class LayoutedStage extends BaseCustomComponentContainerImpl<Stage> {

    protected LayoutedStage(Stage stage) {
        super(stage);
    }

    protected LayoutedStage() {
        this(StageFactory.newInstance());
    }

    public LayoutedStage(int bgColor, boolean b) {
        this(StageFactory.newInstance(bgColor, b));
    }

    public final void addChildToCenter(CustomComponent child, int width, int height) {
        addChildToCenter(child.getMainComponent(), width, height);
    }

    public void addChild(CustomComponent customComponent) {
        addChild(customComponent.getMainComponent());
    }

    public final void addChildToCenter(DisplayObject child, int width, int height) {
        getMainComponent().addChild(child);
        setWidth(width);
        setHeight(height);
        int newX = (int) (getWidth() / 2);
        int newY = (int) (getHeight() / 2);
        Graphics mask = child.getMask();
        double subWidth = child.getBounds().getWidth() + child.getBounds().getX();
        double subHeigt = child.getBounds().getHeight() + child.getBounds().getY();
        if (mask != null) {
            subWidth = mask.getBounds().getWidth() + mask.getBounds().getX() + child.getBounds().getX();
            subHeigt = mask.getBounds().getHeight() + mask.getBounds().getY() + child.getBounds().getY();
        }
        newX = (int) (newX - subWidth / 2);
        newY = (int) (newY - subHeigt / 2);
        child.setPosition(PointFactory.newInstance(newX, newY));
    }

    public void startAnimatedRendering(Renderer renderer) {
        startAnimatedRendering(this, this.getMainComponent(), renderer);
    }

    private final native void startAnimatedRendering(LayoutedStage inst, Stage stage, Renderer renderer) /*-{
        $wnd.animate = function() {
            $wnd.requestAnimFrame($wnd.animate);
            renderer.render(stage);
        }
        $wnd.requestAnimFrame( $wnd.animate );
    }-*/;

    public void addChild(DisplayObject child) {
        getMainComponent().addChild(child);
    }

    public void setWidth(int width) {
        getMainComponent().setWidth(width);
    }

    public void setHeight(int height) {
        getMainComponent().setHeight(height);
    }

    public double getWidth() {
        return getMainComponent().getWidth();
    }

    public double getHeight() {
        return getMainComponent().getHeight();
    }

    public Rectangle getBounds() {
        return getMainComponent().getBounds();
    }

    public Array<DisplayObject> getChildren() {
        return getMainComponent().getChildren();
    }
}

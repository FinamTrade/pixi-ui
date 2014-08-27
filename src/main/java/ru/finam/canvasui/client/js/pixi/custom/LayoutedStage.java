package ru.finam.canvasui.client.js.pixi.custom;

import com.google.gwt.user.client.Window;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.Array;
import ru.finam.canvasui.client.js.JsObject;
import ru.finam.canvasui.client.js.pixi.*;
import ru.finam.canvasui.client.js.pixi.DisplayObject;
import ru.finam.canvasui.client.js.pixi.Graphics;
import ru.finam.canvasui.client.js.pixi.Rectangle;
import ru.finam.canvasui.client.js.pixi.Stage;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ikusch on 19.08.14.
 */
public class LayoutedStage {

    private Set<JsObject> updateFunctions;
    private Stage stage;

    protected LayoutedStage(Stage stage) {
        setStage(stage);
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }

    protected LayoutedStage() {
        this(StageFactory.newInstance());
    }

    public LayoutedStage(int bgColor, boolean b) {
        this(StageFactory.newInstance(bgColor, b));
    }

    public final void addChildToCenter(DisplayObject child, int width, int height) {
        stage.addChild(child);
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

    private static void addChildUpdatableFunction(DisplayObject child, Set<JsObject> updateFunctions) {
        JsObject updateFunc = child.getUpdateFunction();
        if (updateFunc != null) {
            updateFunctions.add(updateFunc);
        }
    }

    private static final native <T extends JsObject> T getArrayEl(Array<T> array, int i) /*-{
        return array[i];
    }-*/;

    public static void collectUpdateFunctionsRecursively(Set<JsObject> updateFunctions,
                                                         DisplayObjectContainer container) {
        Array<DisplayObject> childrens = container.getChildren();
        if (childrens != null)
            for (int i = 0; i < childrens.getLength(); ++i) {
                DisplayObject child = getArrayEl(childrens, i);
                addChildUpdatableFunction(child, updateFunctions);
                if (child instanceof DisplayObjectContainer) {
                    collectUpdateFunctionsRecursively(updateFunctions, (DisplayObjectContainer) child);
                }
            }
    }

    public final Set<JsObject> collectUpdateFunctions() {
        updateFunctions = new HashSet<>();
        Stage stage = getStage();
        collectUpdateFunctionsRecursively(updateFunctions, stage);
        return updateFunctions;
    }

    private Set<JsObject> updateFunctions() {
        if (updateFunctions == null)
            collectUpdateFunctions();
        return updateFunctions;
    }

    private void updateChildrens() {
        for (JsObject updateFunction : updateFunctions()) {
            doUpdateFunction(updateFunction);
        }
    }

    private final native void doUpdateFunction(JsObject updateFunction) /*-{
        updateFunction();
    }-*/;

    public void startAnimatedRendering(Renderer renderer) {
        startAnimatedRendering(this, this.stage, renderer);
    }

    private final native void startAnimatedRendering(LayoutedStage inst, Stage stage, Renderer renderer) /*-{
        $wnd.animate = function() {
            inst.@ru.finam.canvasui.client.js.pixi.custom.LayoutedStage::updateChildrens()();
            $wnd.requestAnimFrame($wnd.animate);
            renderer.render(stage);
        }
        $wnd.requestAnimFrame( $wnd.animate );
    }-*/;

    public void addChild(DisplayObject child) {
        stage.addChild(child);
    }

    public void setWidth(int width) {
        stage.setWidth(width);
    }

    public void setHeight(int height) {
        stage.setHeight(height);
    }

    public double getWidth() {
        return stage.getWidth();
    }

    public double getHeight() {
        return stage.getHeight();
    }

    public Rectangle getBounds() {
        return stage.getBounds();
    }
}

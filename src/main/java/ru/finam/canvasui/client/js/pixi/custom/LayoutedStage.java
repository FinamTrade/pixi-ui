package ru.finam.canvasui.client.js.pixi.custom;

import ru.finam.canvasui.client.js.pixi.*;
import ru.finam.canvasui.client.js.pixi.DisplayObject;
import ru.finam.canvasui.client.js.pixi.Graphics;
import ru.finam.canvasui.client.js.pixi.Rectangle;
import ru.finam.canvasui.client.js.pixi.Stage;

/**
 * Created by ikusch on 19.08.14.
 */
public class LayoutedStage {

    protected LayoutedStage(Stage stage) {
        stage(stage);
    }

    private final native void stage(Stage stage) /*-{
        this.mainStage = stage;
    }-*/;

    public final native Stage stage() /*-{
        return this.mainStage;
    }-*/;

    protected LayoutedStage() {
        this(StageFactory.newInstance());
    }

    public LayoutedStage(int bgColor, boolean b) {
        this(StageFactory.newInstance(bgColor, b));
    }

    public final void addChildToCenter(DisplayObject child, int width, int height) {
        stage().addChild(child);
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

    private static void addChildUpdatableFunction(DisplayObject child, Array<JsObject> updateFunctions) {
        JsObject updateFunc = child.getUpdateFunction();
        if (updateFunc != null) {
            updateFunctions.push(updateFunc);
        }
    }

    private static final native <T extends JsObject> T getArrayEl(Array<T> array, int i) /*-{
        return array[i];
    }-*/;

    public static void collectUpdateFunctionsRecursively(Array<JsObject> updateFunctions,
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

    public final Array<JsObject> collectUpdateFunctions() {
        Array<JsObject> updateFunctions = ArrayFactory.newArray();
        Stage stage = stage();
        collectUpdateFunctionsRecursively(updateFunctions, stage);
        return updateFunctions;
    }

    public final double getBoundedWidth(DisplayObject displayObject) {
        return displayObject.getBounds() == null ? 0 : ( displayObject.getBounds().getWidth() + 2 * displayObject.getBounds().getX() );
    }

    public final double getBoundedHeight(DisplayObject displayObject) {
        return displayObject.getBounds() == null ? 0 : ( displayObject.getBounds().getHeight() + 2 * displayObject.getBounds().getY() );
    }

    public void addChild(DisplayObject child) {
        stage().addChild(child);
    }

    public void setWidth(int width) {
        stage().setWidth(width);
    }

    public void setHeight(int height) {
        stage().setHeight(height);
    }

    public double getWidth() {
        return stage().getWidth();
    }

    public double getHeight() {
        return stage().getHeight();
    }

    public Rectangle getBounds() {
        return stage().getBounds();
    }
}

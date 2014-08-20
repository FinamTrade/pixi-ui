package ru.finam.canvasui.client.pixi.custom;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.pixi.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 11.08.14
 * Time: 11:52
 * To change this template use File | Settings | File Templates.
 */
public class LayoutedStage extends Stage {

    static {
        exportJsObject();
    }

    protected LayoutedStage() {}

    public static native void exportJsObject() /*-{
        $wnd.LayoutedStage = function(backgroundColor, interactive) {
            $wnd.PIXI.Stage.call(this, backgroundColor, interactive);
        }
        $wnd.LayoutedStage.constructor = $wnd.LayoutedStage;
        $wnd.LayoutedStage.prototype = Object.create($wnd.PIXI.Stage.prototype);
    }-*/;

    public static native LayoutedStage newInstance(int bgColor, boolean isInteractive) /*-{
        return new $wnd.LayoutedStage(bgColor, isInteractive);
    }-*/;

    public static LayoutedStage newInstance(int bgColor) {
        return newInstance(bgColor, false);
    }

    public static LayoutedStage newInstance() {
        return newInstance(DEAFULT_BG, false);
    }

    public final void addChildToCenter(DisplayObject child, int width, int height) {
        this.addChild(child);
        setWidth(width);
        setHeight(height);
        int newX = getWidth() / 2;
        int newY = getHeight() / 2;
        Graphics mask = child.getMask();
        double subWidth = child.getBounds().getWidth() + child.getBounds().getX();
        double subHeigt = child.getBounds().getHeight() + child.getBounds().getY();
        if (mask != null) {
            subWidth = mask.getBounds().getWidth() + mask.getBounds().getX() + child.getBounds().getX();
            subHeigt = mask.getBounds().getHeight() + mask.getBounds().getY() + child.getBounds().getY();
        }
        newX = (int) ( newX - subWidth / 2 );
        newY = (int) ( newY - subHeigt / 2 );
        child.setPosition(Point.newInstance(newX, newY));
    }

    private static void addChildUpdatableFunction(DisplayObject child, JsArray<JavaScriptObject> updateFunctions) {
        if (child instanceof UpdatableComponent) {
            UpdatableComponent updatableChild = (UpdatableComponent) child;
            JavaScriptObject updateFunc = updatableChild.getUpdateFunction();
            if (updateFunc != null) {
                updateFunctions.push(updateFunc);
            }
        }
    }

    public static void collectUpdateFunctionsRecursively(JsArray<JavaScriptObject> updateFunctions,
                                             DisplayObjectContainer container) {
        JsArray<DisplayObject> childrens = container.getChildrens();
        if (childrens != null)
            for (int i = 0; i < childrens.length(); ++i) {
                DisplayObject child = childrens.get(i);
                addChildUpdatableFunction(child, updateFunctions);
                if (child instanceof DisplayObjectContainer) {
                    collectUpdateFunctionsRecursively(updateFunctions, (DisplayObjectContainer) child);
                }
            }
    }

    public final JsArray<JavaScriptObject> collectUpdateFunctions() {
        JsArray<JavaScriptObject> updateFunctions = JsArray.createArray().cast();
        LayoutedStage stage = this;
        collectUpdateFunctionsRecursively(updateFunctions, stage);
        return updateFunctions;
    }

}

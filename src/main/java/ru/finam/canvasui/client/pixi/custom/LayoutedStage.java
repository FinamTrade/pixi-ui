package ru.finam.canvasui.client.pixi.custom;

import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.pixi.*;

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
        JsConsole.log(" STAGE width ="+getWidth());
        JsConsole.log(" child x ="+child.getPosition().getX());
        JsConsole.log(" child bounds x ="+child.getBounds().getX());
        JsConsole.log(" child bounds w ="+child.getBounds().getWidth());
        int newX = getWidth() / 2;
        int newY = getHeight() / 2;
        Graphics mask = child.getMask();
        JsConsole.log(" stage bounds x =" + getBounds().getX());
        JsConsole.log(" stage bounds w =" + getBounds().getWidth());
        double subWidth = child.getBounds().getWidth() + child.getBounds().getX();
        double subHeigt = child.getBounds().getHeight() + child.getBounds().getY();
        if (mask != null) {
            subWidth = mask.getBounds().getWidth() + mask.getBounds().getX() + child.getBounds().getX();
            subHeigt = mask.getBounds().getHeight() + mask.getBounds().getY() + child.getBounds().getY();
            JsConsole.log(" mask bounds x ="+mask.getBounds().getX());
            JsConsole.log(" mask bounds w ="+mask.getBounds().getWidth());
            JsConsole.log(" mask position x ="+mask.getPosition().getX());
            JsConsole.log(" mask position y ="+mask.getPosition().getY());
        }
        newX = (int) ( newX - subWidth / 2 );
        newY = (int) ( newY - subHeigt / 2 );
        child.setPosition(Point.newInstance(newX, newY));
        //child.setPosition(Point.newInstance(0, 0));
        JsConsole.log(" child x ="+child.getPosition().getX());
        JsConsole.log(" child y ="+child.getPosition().getY());
    }

}

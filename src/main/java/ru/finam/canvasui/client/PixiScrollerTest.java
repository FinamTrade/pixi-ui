package ru.finam.canvasui.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import ru.finam.canvasui.client.pixi.*;
import ru.finam.canvasui.client.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.pixi.custom.ScrollPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 09.08.14
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */
public class PixiScrollerTest {

    private static final int BG_COLOR = 0xFFFFFF;
    private static List<Texture> textures = new ArrayList<Texture>();
    private static final String SAMPLE_IMAGE1 = "img/city1.png";
    private static final String SAMPLE_IMAGE2 = "img/city2.png";
    private static final String SAMPLE_IMAGE3 = "img/funny.jpg";
    private static Renderer renderer;

    public static void start(String rendererContainerId) {
        exportMyFunction(rendererContainerId);
        JsArrayString assets = JsArrayString.createArray().cast();
        assets.push(SAMPLE_IMAGE1);
        assets.push(SAMPLE_IMAGE2);
        assets.push(SAMPLE_IMAGE3);
        loadAssets(rendererContainerId, assets);
    }

    private static native void loadAssets(String rendererContainerId, JsArrayString assetsToLoad) /*-{
        var loader = new $wnd.PIXI.AssetLoader(assetsToLoad);
        loader.onComplete = function() {
            $wnd.PixiScrollerTest.onAssetsLoaded(rendererContainerId);
        };
        loader.load();
    }-*/;

    public static native void exportMyFunction(String rendererContainerId) /*-{
        $wnd.PixiScrollerTest = {};
        $wnd.PixiScrollerTest.onAssetsLoaded = function() {
            $entry(@ru.finam.canvasui.client.PixiScrollerTest::assetsLoaded(Ljava/lang/String;)(rendererContainerId));
        }
    }-*/;

    public static void assetsLoaded(String rendererContainerId) {
        RootPanel element = RootPanel.get(rendererContainerId);
        element.getElement().getStyle().setPadding(0, Style.Unit.PX);
        int width = element.getElement().getOffsetWidth();
        int height = element.getElement().getClientHeight();
        //JsConsole.log("Renderer width = "+width);
        //JsConsole.log("Renderer height = "+height);
        LayoutedStage.exportJsObject();
        renderer = Renderer.addNewAuoDetectRenderer(element, width, height);
        LayoutedStage stage = LayoutedStage.newInstance(BG_COLOR, true);
        //stage.addChild(newSampleContainerWithGraphics());
        //stage.addChild(newSampleImage(SAMPLE_IMAGE1));
        //stage.addChild(newSampleImage(SAMPLE_IMAGE2));
        ScrollPanel scrollPanel = fixedSizeScrollPanel1(newSampleImage(SAMPLE_IMAGE3));
        stage.addChildToCenter(scrollPanel, width, height);
        //JsConsole.log("displayObjectContainer getPosition x = "+displayObjectContainer.getPosition().getX());
        //stage.addChild(newSampleImage(SAMPLE_IMAGE2));
        JsArray<JavaScriptObject> updateFunctions = JsArray.createArray().cast();
        updateFunctions.push(scrollPanel.getUpdateFunction());
        renderer.startAnimatedRendering(stage, updateFunctions);
    }

    private static DisplayObject testSprite() {
        Texture texture = Texture.fromImage(SAMPLE_IMAGE2);
        Stage stage = Stage.newInstance(0xFFFFFF, true);

        Sprite sprite = Sprite.newInstance(texture);
        Graphics graphics = Graphics.newInstance();
        graphics.addChild(sprite);
        graphics.setHeight(128);
        graphics.setWidth(256);

        stage.addChild(sprite);
        stage.setHeight(128);
        stage.setWidth(256);
        //return sprite;  //To change body of created methods use File | Settings | File Templates.
        return stage;
    }

    private static DisplayObjectContainer newSampleContainerWithGraphics() {
        DisplayObjectContainer sampleContainer = DisplayObjectContainer.newInstance();
        Graphics graphics = Graphics.newInstance();
        sampleContainer.addChild(graphics);
        graphics.lineStyle(1, 0x000000, 1);
        graphics.drawRect(0, 0, 30, 30);
        //JsConsole.log("sampleContainer.width = " + sampleContainer.getWidth());
        //JsConsole.log("sampleContainer.height = " + sampleContainer.getHeight());
        return sampleContainer;
    }

    private static DisplayObjectContainer newSampleImage(String path) {
        Texture texture = Texture.fromImage(path);
        //TilingSprite sprite = TilingSprite.newInstance(path, 512, 256);
        Sprite sprite = Sprite.newInstance(texture);
        sprite.setWidth(texture.getWidth());
        sprite.setHeight(texture.getHeight());
        /*
        JsConsole.log("texture.width = " + texture.getWidth());
        JsConsole.log("texture.height = " + texture.getHeight());
        JsConsole.log("sprite.width = " + sprite.getWidth());
        JsConsole.log("sprite.height = " + sprite.getHeight());
        */
        return sprite;
    }

    private static ScrollPanel fixedSizeScrollPanel1(DisplayObjectContainer innerPanel) {
        int width = innerPanel.getWidth();
        int height = innerPanel.getHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width / 2, height / 2);
        return scrollPanel;
    }

}

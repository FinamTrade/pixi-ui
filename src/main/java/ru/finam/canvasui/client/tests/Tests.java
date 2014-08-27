package ru.finam.canvasui.client.tests;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import ru.finam.canvasui.client.js.pixi.*;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.UpdatableRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikusch on 14.08.14.
 */
public class Tests {

    private static final int BG_COLOR = 0xFFFFFF;
    private static final String MENU_ITEM_CLASSNAME = "menu-item";
    private static final String MENU_ITEM_HOLDER_CLASSNAME = "menu-item-holder";
    private static List<Texture> textures = new ArrayList<Texture>();
    private static final String SAMPLE_IMAGE1 = "img/city1.png";
    private static final String SAMPLE_IMAGE2 = "img/city2.png";
    private static final String SAMPLE_IMAGE3 = "img/funny.jpg";
    private static final String SAMPLE_IMAGE4 = "img/funny2.jpg";
    private static UpdatableRenderer renderer;
    private static List<PixiScrollerTest> tests = new ArrayList<PixiScrollerTest>();
    private static PixiScrollerTest currentTest;
    private static String[] images = newImagesArray();
    private static boolean assetsLoaded = false;

    private static String[] newImagesArray() {
        List<String> al = new ArrayList<String>();
        al.add(SAMPLE_IMAGE1);
        al.add(SAMPLE_IMAGE2);
        al.add(SAMPLE_IMAGE3);
        al.add(SAMPLE_IMAGE4);
        return al.toArray(new String[]{});
    }

    public static void start(String rendererContainerId) {
        exportMyFunction(rendererContainerId);
        JsArrayString assets = JsArrayString.createArray().cast();
        for (String i:images) {
            assets.push(i);
        }
        if (!assetsLoaded)
            loadAssets(rendererContainerId, assets);
        else
            assetsLoaded(rendererContainerId);
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
            $entry(@ru.finam.canvasui.client.tests.Tests::assetsLoaded(Ljava/lang/String;)(rendererContainerId));
        }
    }-*/;

    public static void assetsLoaded(String rendererContainerId) {
        assetsLoaded = true;
        RootPanel element = RootPanel.get(rendererContainerId);
        element.getElement().getStyle().setPadding(0, Style.Unit.PX);
        element.clear(true);
        int width = element.getElement().getOffsetWidth();
        int height = element.getElement().getClientHeight();
        renderer = UpdatableRenderer.addNewAuoDetectRenderer(element, width, height);
        element.getElement().appendChild(renderer.getView());
        LayoutedStage stage = currentTest.newTestStage(width, height, images);
        stage.startAnimatedRendering(renderer);
    }

    public static void load(final String rendererContainerId, String menuContainerId) {
        addNewTest(rendererContainerId, new PixiScrollerTest1(), menuContainerId);
        addNewTest(rendererContainerId, new PixiScrollerTest2(), menuContainerId);
        addNewTest(rendererContainerId, new PixiScrollerTest3(), menuContainerId);
        addNewTest(rendererContainerId, new PixiScrollerTest4(), menuContainerId);
        addNewTest(rendererContainerId, new PixiScrollerTest6(), menuContainerId);
        addNewTest(rendererContainerId, new TimelineTest1(), menuContainerId);
        //addNewTest(rendererContainerId, new TimelineTest2(), menuContainerId);
        currentTest = tests.get(0);
        start(rendererContainerId);
    }

    private static void addNewTest(final String rendererContainerId, final PixiScrollerTest pixiScrollerTest, String menuContainerId) {
        addNewTest(rendererContainerId, pixiScrollerTest, menuContainerId, new ClickHandler() {
            public void onClick(ClickEvent event) {
                currentTest = pixiScrollerTest;
                start(rendererContainerId);
            }
        }, pixiScrollerTest.name());
    }

    private static void addNewTest(final String rendererContainerId, final PixiScrollerTest pixiScrollerTest, String menuContainerId, ClickHandler clickHandler, String name) {
        tests.add(pixiScrollerTest);
        FlowPanel flowPanel = new FlowPanel();
        flowPanel.getElement().addClassName(MENU_ITEM_HOLDER_CLASSNAME);
        FocusPanel panel = new FocusPanel();
        panel.getElement().setInnerHTML(""+pixiScrollerTest.name());
        panel.getElement().addClassName(MENU_ITEM_CLASSNAME);
        panel.addClickHandler(clickHandler);
        flowPanel.add(panel);
        RootPanel.get(menuContainerId).add(flowPanel);
    }

}

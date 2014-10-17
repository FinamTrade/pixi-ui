package ru.finam.canvasui.client.tests;


import ru.finam.canvasui.client.js.pixi.*;
import ru.finam.canvasui.client.js.pixi.custom.panel.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.panel.SimplePixiPanel;
import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ComponentWithShowSizes;
import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ComponentWithShowSizesImpl;
import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ScrollPanel;

/**
 * Created with IntelliJ IDEA.
 * User: Superman
 * Date: 09.08.14
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */
public class PixiScrollerTest1 extends PixiScrollerTest {

    private static final int BG_COLOR = 0xFFFFFF;
    public static final String NAME = "Test1";

    private static DisplayObjectContainer newSampleContainerWithGraphics() {
        DisplayObjectContainer sampleContainer = DisplayObjectContainer.Factory.newInstance();
        Graphics graphics = Graphics.Factory.newInstance();
        sampleContainer.addChild(graphics);
        graphics.lineStyle(1, 0x000000, 1);
        graphics.drawRect(0, 0, 30, 30);
        //JsConsole.log("sampleContainer.width = " + sampleContainer.getWidth());
        //JsConsole.log("sampleContainer.height = " + sampleContainer.getHeight());
        return sampleContainer;
    }

    public void fillStage(int width, int height, String... images) {
        stage.clear();
        ScrollPanel scrollPanel = fixedSizeScrollPanel1(new ComponentWithShowSizesImpl(newSampleImage(images[2])));
        stage.addChildToCenter(scrollPanel, width, height);
    }

    private static ScrollPanel fixedSizeScrollPanel1(ComponentWithShowSizes innerPanel) {
        int width = (int) innerPanel.showWidth();
        int height = (int) innerPanel.showHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width / 2, height / 2);
        return scrollPanel;
    }

}
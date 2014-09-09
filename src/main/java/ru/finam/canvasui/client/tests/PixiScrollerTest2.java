package ru.finam.canvasui.client.tests;


import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.js.pixi.custom.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.SimplePixiPanel;
import ru.finam.canvasui.client.js.pixi.custom.scroller.ScrollPanel;

/**
 * Created by ikusch on 14.08.14.
 */
public class PixiScrollerTest2 extends PixiScrollerTest {

    public static final String NAME = "Test2";

    public void fillStage(int width, int height, String... images) {
        stage.clear();
        ScrollPanel scrollPanel = fixedSizeScrollPanel(new SimplePixiPanel(newSampleImage(images[1])));
        stage.addChildToCenter(scrollPanel.getMainComponent(), width, height);
    }

    private static ScrollPanel fixedSizeScrollPanel(CustomComponentContainer innerPanel) {
        int width = (int) innerPanel.getWidth();
        int height = (int) innerPanel.getHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width / 3, height / 3);
        return scrollPanel;
    }

}

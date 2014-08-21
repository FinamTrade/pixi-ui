package ru.finam.canvasui.client.tests;


import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.ScrollPanel;

/**
 * Created by ikusch on 14.08.14.
 */
public class PixiScrollerTest3 extends PixiScrollerTest {

    public LayoutedStage newTestStage(int width, int height, String... images) {
        LayoutedStage stage = new LayoutedStage(BG_COLOR, true);
        ScrollPanel scrollPanel = fixedSizeScrollPanel(newSampleImage(images[1]));
        stage.addChildToCenter(scrollPanel.getMainComponent(), width, height);
        return stage;
    }

    public String name() {
        return "Test3";
    }

    private static ScrollPanel fixedSizeScrollPanel(DisplayObjectContainer innerPanel) {
        int width = (int) innerPanel.getWidth();
        int height = (int) innerPanel.getHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width, height, true);
        return scrollPanel;
    }

}

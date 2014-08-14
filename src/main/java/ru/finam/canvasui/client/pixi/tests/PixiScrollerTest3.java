package ru.finam.canvasui.client.pixi.tests;

import ru.finam.canvasui.client.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.pixi.custom.ScrollPanel;

/**
 * Created by ikusch on 14.08.14.
 */
public class PixiScrollerTest3 extends PixiScrollerTest {

    public LayoutedStage newTestStage(int width, int height, String... images) {
        LayoutedStage stage = LayoutedStage.newInstance(BG_COLOR, true);
        ScrollPanel scrollPanel = fixedSizeScrollPanel(newSampleImage(images[1]));
        stage.addChildToCenter(scrollPanel, width, height);
        return stage;
    }

    public String name() {
        return "Test3";
    }

    private static ScrollPanel fixedSizeScrollPanel(DisplayObjectContainer innerPanel) {
        int width = innerPanel.getWidth();
        int height = innerPanel.getHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width, height, true);
        return scrollPanel;
    }
}

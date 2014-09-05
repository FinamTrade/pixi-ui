package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.js.pixi.Graphics;
import ru.finam.canvasui.client.js.pixi.custom.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.SimplePixiPanel;
import ru.finam.canvasui.client.js.pixi.custom.scroller.ScrollPanel;

/**
 * Created by ikusch on 29.08.2014.
 */
public class BorderedImageTest extends PixiScrollerTest {

    private static final int BG_COLOR = 0xFFFFFF;

    public LayoutedStage newTestStage(int width, int height, String... images) {
        LayoutedStage stage = new LayoutedStage(BG_COLOR, true);
        ScrollPanel scrollPanel = fixedSizeScrollPanel1(new SimplePixiPanel(newSampleImage(images[5])));
        stage.addChildToCenter(scrollPanel, width, height);
        return stage;
    }

    public String name() {
        return "Bordered Image Test";
    }

    private static ScrollPanel fixedSizeScrollPanel1(CustomComponentContainer innerPanel) {
        int width = (int) innerPanel.getWidth();
        int height = (int) innerPanel.getHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, (int)(width * 0.8), (int)(height * 0.8));
        return scrollPanel;
    }

}

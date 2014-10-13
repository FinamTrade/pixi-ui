package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.js.pixi.custom.panel.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.panel.SimplePixiPanel;
import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ScrollPanel;

/**
 * Created by ikusch on 29.08.2014.
 */
public class BorderedImageTest extends PixiScrollerTest {

    public static final String NAME = "Bordered Image Test";

    private static final int BG_COLOR = 0xFFFFFF;

    public void fillStage(int width, int height, String... images) {
        stage.clear();
        ScrollPanel scrollPanel = fixedSizeScrollPanel1(new SimplePixiPanel(newSampleImage(images[5])));
        stage.addChildToCenter(scrollPanel, width, height);
    }

    private static ScrollPanel fixedSizeScrollPanel1(CustomComponentContainer innerPanel) {
        int width = (int) innerPanel.getWidth();
        int height = (int) innerPanel.getHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, (int)(width * 0.8), (int)(height * 0.8));
        return scrollPanel;
    }

}

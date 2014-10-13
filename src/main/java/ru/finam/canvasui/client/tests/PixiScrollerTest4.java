package ru.finam.canvasui.client.tests;


import ru.finam.canvasui.client.js.pixi.custom.panel.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.panel.SimplePixiPanel;
import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ScrollPanel;

/**
 * Created by ikusch on 14.08.14.
 */
public class PixiScrollerTest4 extends PixiScrollerTest {

    public static final String NAME = "Test4";

    public void fillStage(int width, int height, String... images) {
        stage.clear();
        ScrollPanel scrollPanel = fixedSizeScrollPanel(new SimplePixiPanel(newSampleImage(images[1])));
        stage.addChildToCenter(scrollPanel, width, height);
    }

    private static ScrollPanel fixedSizeScrollPanel(CustomComponentContainer innerPanel) {
        int width = (int) innerPanel.getWidth();
        int height = (int) innerPanel.getHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, (int) (width * 1.5), (int)(height * 2), true);
        return scrollPanel;
    }

}

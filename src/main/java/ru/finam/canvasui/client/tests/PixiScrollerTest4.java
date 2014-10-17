package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ComponentWithShowSizes;
import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ComponentWithShowSizesImpl;
import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ScrollPanel;

/**
 * Created by ikusch on 14.08.14.
 */
public class PixiScrollerTest4 extends PixiScrollerTest {

    public static final String NAME = "Test4";

    public void fillStage(int width, int height, String... images) {
        stage.clear();
        ScrollPanel scrollPanel = fixedSizeScrollPanel(new ComponentWithShowSizesImpl(newSampleImage(images[1])));
        stage.addChildToCenter(scrollPanel, width, height);
    }

    private static ScrollPanel fixedSizeScrollPanel(ComponentWithShowSizes innerPanel) {
        int width = (int) innerPanel.showWidth();
        int height = (int) innerPanel.showHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, (int) (width * 1.5), (int)(height * 2), true);
        return scrollPanel;
    }

}

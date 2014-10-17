package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.js.gsap.easing.Quad;
import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.js.pixi.custom.panel.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ComponentWithShowSizes;
import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ScrollPanel;
import ru.finam.canvasui.client.js.pixi.custom.panel.table.RandomValuesTable;

/**
 * Created by ikusch on 08.09.2014.
 */
public class BigTableTest4 extends PixiScrollerTest {

    public static final String NAME = "Big table scrolling Quad ease";

    @Override
    public void fillStage(int width, int height, String... images) {
        stage.clear();
        //DisplayObjectContainer d = newSampleImage(images[1]);
        final RandomValuesTable d = new RandomValuesTable(5, 200);
        stage.addChildToCenter(fixedSizeScrollPanel1(d), width, height);
    }

    private static ScrollPanel fixedSizeScrollPanel1(ComponentWithShowSizes<DisplayObjectContainer> innerPanel) {
        //innerPanel.setWidth(innerPanel.getBoundedWidth() - 11);
        int width = (int) innerPanel.showWidth();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width, 500, false, Quad.Static.easeOut());
        return scrollPanel;
    }

}

package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ComponentWithShowSizes;
import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ScrollPanel;
import ru.finam.canvasui.client.js.pixi.custom.panel.table.RandomValuesTable;

/**
 * Created by ikusch on 28.08.2014.
 */
public class BigTableTest1 extends PixiScrollerTest {

    public static final String NAME = "BigTableTest1";

    @Override
    public void fillStage(int width, int height, String... images) {
        stage.clear();
        //DisplayObjectContainer d = newSampleImage(images[1]);
        final RandomValuesTable d = new RandomValuesTable(5, 600);
        stage.addChildToCenter(fixedSizeScrollPanel1(d), width, height);
    }

    private static ScrollPanel fixedSizeScrollPanel1(ComponentWithShowSizes innerPanel) {
        //innerPanel.setWidth(innerPanel.getBoundedWidth() - 11);
        int width = (int) innerPanel.showWidth();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width, 300);
        return scrollPanel;
    }

}

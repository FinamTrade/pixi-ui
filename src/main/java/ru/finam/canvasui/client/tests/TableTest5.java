package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.js.pixi.custom.panel.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ScrollPanel;
import ru.finam.canvasui.client.js.pixi.custom.panel.table.RandomValuesTable;

/**
 * Created by ikusch on 29.08.2014.
 */
public class TableTest5 extends PixiScrollerTest {

    public static final String NAME = "TableTest5";

    @Override
    public void fillStage(int width, int height, String... images) {
        stage.clear();
        //DisplayObjectContainer d = newSampleImage(images[1]);
        final RandomValuesTable d = new RandomValuesTable(5, 8);
        stage.addChildToCenter(fixedSizeScrollPanel1(d), width, height);
    }

    private static ScrollPanel fixedSizeScrollPanel1(CustomComponentContainer innerPanel) {
        int width = (int) innerPanel.getBoundedWidth();
        int height = (int) innerPanel.getBoundedHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width, height / 2);
        return scrollPanel;
    }

}

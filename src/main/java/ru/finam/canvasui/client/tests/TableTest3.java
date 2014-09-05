package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.js.pixi.custom.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.scroller.ScrollPanel;
import ru.finam.canvasui.client.js.pixi.custom.table.RandomValuesTable;

/**
 * Created by ikusch on 29.08.2014.
 */
public class TableTest3 extends PixiScrollerTest {

    @Override
    public LayoutedStage newTestStage(int width, int height, String... images) {
        LayoutedStage stage = new LayoutedStage(BG_COLOR, true);
        final RandomValuesTable d = new RandomValuesTable(5, 4);
        stage.addChildToCenter(fixedSizeScrollPanel1(d), width, height);
        return stage;
    }

    @Override
    public String name() {
        return "TableTest3";
    }

    private static ScrollPanel fixedSizeScrollPanel1(CustomComponentContainer innerPanel) {
        int width = (int) innerPanel.getBoundedWidth();
        int height = (int) innerPanel.getBoundedHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width * 2, height * 2);
        //innerPanel.setPosition(PointFactory.newInstance(5, 5));
        return scrollPanel;
    }

}

package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.js.pixi.custom.panel.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ComponentWithShowSizes;
import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ComponentWithShowSizesImpl;
import ru.finam.canvasui.client.js.pixi.custom.panel.scroller.ScrollPanel;
import ru.finam.canvasui.client.js.pixi.custom.panel.table.RandomValuesTable;

/**
 * Created by ikusch on 08.09.2014.
 */
public class DecreasingTableTest extends PixiScrollerTest {

    public static final String NAME = "Decreasing Table";

    public static RandomValuesTable randomValuesTable;

    @Override
    public void fillStage(int width, int height, String... images) {
        stage.clear();
        randomValuesTable = new RandomValuesTable(5, 16);
        stage.addChildToCenter(fixedSizeScrollPanel1(randomValuesTable), width, height);
    }

    private static ScrollPanel fixedSizeScrollPanel1(ComponentWithShowSizes innerPanel) {
        //innerPanel.setWidth(innerPanel.getBoundedWidth() - 11);
        int width = (int) innerPanel.showWidth();
        int height = (int) innerPanel.showHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width - 11, height / 3);
        return scrollPanel;
    }

}

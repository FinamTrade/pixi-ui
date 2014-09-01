package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.pixi.custom.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.ScrollPanel;
import ru.finam.canvasui.client.js.pixi.custom.table.RandomValuesTable;

/**
 * Created by ikusch on 29.08.2014.
 */
public class TableTest4 extends PixiScrollerTest {

    @Override
    public LayoutedStage newTestStage(int width, int height, String... images) {
        LayoutedStage stage = new LayoutedStage(BG_COLOR, true);
        //DisplayObjectContainer d = newSampleImage(images[1]);
        final RandomValuesTable d = new RandomValuesTable(5, 4);
        stage.addChildToCenter(fixedSizeScrollPanel1(d), width, height);
        return stage;
    }

    @Override
    public String name() {
        return "TableTest4";
    }

    private static ScrollPanel fixedSizeScrollPanel1(CustomComponentContainer innerPanel) {
        int width = (int) innerPanel.getBoundedWidth();
        int height = (int) innerPanel.getBoundedHeight();
        JsConsole.log("fixedSizeScrollPanel1! height = " + height);
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width, height);
        return scrollPanel;
    }

}

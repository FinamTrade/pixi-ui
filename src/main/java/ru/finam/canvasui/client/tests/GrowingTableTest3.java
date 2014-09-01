package ru.finam.canvasui.client.tests;

import com.google.gwt.user.client.Timer;
import ru.finam.canvasui.client.js.pixi.custom.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.ScrollPanel;
import ru.finam.canvasui.client.js.pixi.custom.table.RandomValuesTable;

/**
 * Created by ikusch on 28.08.2014.
 */
public class GrowingTableTest3 extends PixiScrollerTest {

    @Override
    public LayoutedStage newTestStage(int width, int height, String... images) {
        LayoutedStage stage = new LayoutedStage(BG_COLOR, true);
        //DisplayObjectContainer d = newSampleImage(images[1]);
        final RandomValuesTable d = new RandomValuesTable(5, 600);
        stage.addChildToCenter(fixedSizeScrollPanel1(d), width, height);
        return stage;
    }

    @Override
    public String name() {
        return "Big table scrolling";
    }

    private static ScrollPanel fixedSizeScrollPanel1(CustomComponentContainer innerPanel) {
        //innerPanel.setWidth(innerPanel.getBoundedWidth() - 11);
        int width = (int) innerPanel.getBoundedWidth();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width, 300);
        return scrollPanel;
    }

}

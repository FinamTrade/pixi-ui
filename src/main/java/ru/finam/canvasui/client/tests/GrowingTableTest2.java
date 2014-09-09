package ru.finam.canvasui.client.tests;

import com.google.gwt.user.client.Timer;
import ru.finam.canvasui.client.js.pixi.custom.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.scroller.ScrollPanel;
import ru.finam.canvasui.client.js.pixi.custom.table.RandomValuesTable;

/**
 * Created by ikusch on 28.08.2014.
 */
public class GrowingTableTest2 extends PixiScrollerTest {

    public static final String NAME = "GrowingTableTest2";

    int i = 0;
    int MAX = 500;

    @Override
    public void fillStage(int width, int height, String... images) {
        stage.clear();
        //DisplayObjectContainer d = newSampleImage(images[1]);
        i = 0;
        final RandomValuesTable d = new RandomValuesTable(5, 21);
        new Timer() {
            @Override
            public void run() {
                d.addRandomRow();
                ++i;
                if (i > MAX)
                    cancel();
            }
        }.scheduleRepeating(4000);
        //stage.addChildToCenter(d, width, height);
        stage.addChildToCenter(fixedSizeScrollPanel1(d), width, height);
    }

    private static ScrollPanel fixedSizeScrollPanel1(CustomComponentContainer innerPanel) {
        //innerPanel.setWidth(innerPanel.getBoundedWidth() - 11);
        int width = (int) innerPanel.getBoundedWidth();
        int height = (int) innerPanel.getBoundedHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width - 11, height / 2);
        return scrollPanel;
    }

}

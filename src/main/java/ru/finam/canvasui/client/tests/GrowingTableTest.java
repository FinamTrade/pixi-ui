package ru.finam.canvasui.client.tests;

import com.google.gwt.user.client.Timer;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.pixi.custom.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.scroller.ScrollPanel;
import ru.finam.canvasui.client.js.pixi.custom.table.RandomValuesTable;

/**
 * Created by ikusch on 27.08.14.
 */
public class GrowingTableTest extends PixiScrollerTest {

    public static final String NAME = "GrowingTableTest";

    int i = 0;
    int MAX = 2;

    @Override
    public void fillStage(int width, int height, String... images) {
        stage.clear();
        final RandomValuesTable d = new RandomValuesTable(5, 4);
        i = 0;
        new Timer() {
            @Override
            public void run() {
                d.addRandomRow();
                ++i;
                if (i > MAX)
                    cancel();
            }
        }
        .scheduleRepeating(1000);
        stage.addChildToCenter(fixedSizeScrollPanel1(d), width, height);
    }

    private static ScrollPanel fixedSizeScrollPanel1(CustomComponentContainer innerPanel) {
        int width = (int) innerPanel.getBoundedWidth();
        int height = (int) innerPanel.getBoundedHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width, height);
        return scrollPanel;
    }

}

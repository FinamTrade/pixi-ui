package ru.finam.canvasui.client.tests;

import com.google.gwt.user.client.Timer;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.pixi.custom.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.ScrollPanel;
import ru.finam.canvasui.client.js.pixi.custom.table.RandomValuesTable;

/**
 * Created by ikusch on 27.08.14.
 */
public class GrowingTableTest extends PixiScrollerTest {

    int i = 0;
    int MAX = 2;

    @Override
    public LayoutedStage newTestStage(int width, int height, String... images) {
        LayoutedStage stage = new LayoutedStage(BG_COLOR, true);
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
        return stage;
    }

    @Override
    public String name() {
        return "GrowingTableTest";
    }

    private static ScrollPanel fixedSizeScrollPanel1(CustomComponentContainer innerPanel) {
        int width = (int) innerPanel.getBoundedWidth();
        int height = (int) innerPanel.getBoundedHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width, height);
        return scrollPanel;
    }

}

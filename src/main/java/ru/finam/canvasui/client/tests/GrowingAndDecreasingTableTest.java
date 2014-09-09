package ru.finam.canvasui.client.tests;

import com.google.gwt.user.client.Timer;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.pixi.custom.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.scroller.ScrollPanel;
import ru.finam.canvasui.client.js.pixi.custom.table.RandomValuesTable;

/**
 * Created by ikusch on 29.08.2014.
 */
public class GrowingAndDecreasingTableTest extends PixiScrollerTest {

    public static final String NAME = "Growing And Decreasing Table";

    int i = 0;
    int MAX = 5;
    int MIN = 1;
    int mode = 0;
    private Timer timer;
    private RandomValuesTable randomValuesTable;

    private void runTimer() {
        if (mode == 0) {
            randomValuesTable.addRandomRow();
            ++i;
        }
        if (mode == 1) {
            randomValuesTable.removeLastRow();
            --i;
        }
        if (i > MAX)
            mode = 1;
        if (i < MIN)
            mode = 0;
        //cancel();
    }

    @Override
    public void fillStage(int width, int height, String... images) {
        stage.clear();
        //DisplayObjectContainer d = newSampleImage(images[1]);
        i = 0;
        if (timer != null)
            timer.cancel();
        timer =  new Timer() {
            @Override
            public void run() {
                runTimer();
            }
        };
        timer.scheduleRepeating(1000);
        randomValuesTable = new RandomValuesTable(5, 8);
        stage.addChildToCenter(fixedSizeScrollPanel1(randomValuesTable), width, height);
    }

    private static ScrollPanel fixedSizeScrollPanel1(CustomComponentContainer innerPanel) {
        //innerPanel.setWidth(innerPanel.getBoundedWidth() - 11);
        int width = (int) innerPanel.getBoundedWidth();
        int height = (int) innerPanel.getBoundedHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, width - 11, height / 2);
        return scrollPanel;
    }

}

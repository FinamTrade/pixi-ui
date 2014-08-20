package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;

/**
 * Created by ikusch on 19.08.14.
 */
public class PixiScrollerTest6 extends PixiScrollerTest {

    public LayoutedStage newTestStage(int width, int height, String... images) {
        LayoutedStage stage = new LayoutedStage(BG_COLOR, true);
        DisplayObjectContainer displayObjectContainer = newSampleImage(images[1]);
        stage.addChildToCenter(displayObjectContainer, width, height);
        return stage;
    }

    public String name() {
        return "Test6";
    }
}

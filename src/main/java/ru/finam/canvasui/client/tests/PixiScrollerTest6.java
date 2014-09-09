package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;

/**
 * Created by ikusch on 19.08.14.
 */
public class PixiScrollerTest6 extends PixiScrollerTest {

    public static final String NAME = "Test6";

    public void fillStage(int width, int height, String... images) {
        stage.clear();
        DisplayObjectContainer displayObjectContainer = newSampleImage(images[1]);
        stage.addChildToCenter(displayObjectContainer, width, height);
    }
}

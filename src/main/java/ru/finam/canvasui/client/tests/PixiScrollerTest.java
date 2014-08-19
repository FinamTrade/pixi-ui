package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.pixi.Sprite;
import ru.finam.canvasui.client.pixi.Texture;
import ru.finam.canvasui.client.pixi.custom.LayoutedStage;

/**
 * Created by ikusch on 14.08.14.
 */
public abstract class PixiScrollerTest {

    protected static final int BG_COLOR = 0xFFFFFF;

    abstract LayoutedStage newTestStage(int width, int height, String... images);

    abstract String name();

    protected static DisplayObjectContainer newSampleImage(String path) {
        Texture texture = Texture.fromImage(path);
        Sprite sprite = Sprite.newInstance(texture);
        sprite.setWidth(texture.getWidth());
        sprite.setHeight(texture.getHeight());
        return sprite;
    }

}

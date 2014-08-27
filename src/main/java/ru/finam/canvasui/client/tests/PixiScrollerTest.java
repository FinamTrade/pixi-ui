package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.js.pixi.*;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;

/**
 * Created by ikusch on 14.08.14.
 */
public abstract class PixiScrollerTest {

    protected static final int BG_COLOR = 0xFFFFFF;

    public abstract LayoutedStage newTestStage(int width, int height, String... images);

    public abstract String name();

    protected static Sprite newSampleImage(String path) {
        Texture texture = TextureFactory.fromImage(path);
        Sprite sprite = SpriteFactory.newInstance(texture);
        sprite.setWidth(texture.getWidth());
        sprite.setHeight(texture.getHeight());
        return sprite;
    }

}

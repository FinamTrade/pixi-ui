package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.js.pixi.*;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.UpdatableRenderer;

/**
 * Created by ikusch on 14.08.14.
 */
public abstract class PixiScrollerTest {

    protected LayoutedStage stage = new LayoutedStage(BG_COLOR, true);
    protected static UpdatableRenderer renderer;

    protected PixiScrollerTest() {
        stage.startAnimatedRendering(renderer);
    }

    protected static final int BG_COLOR = 0xFFFFFF;

    public abstract void fillStage(int width, int height, String... images);

    protected static Sprite newSampleImage(String path) {
        Texture texture = TextureFactory.fromImage(path);
        Sprite sprite = SpriteFactory.newInstance(texture);
        sprite.setWidth(texture.getWidth());
        sprite.setHeight(texture.getHeight());
        return sprite;
    }

}

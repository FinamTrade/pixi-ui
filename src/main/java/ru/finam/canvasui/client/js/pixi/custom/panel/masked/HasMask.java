package ru.finam.canvasui.client.js.pixi.custom.panel.masked;

import ru.finam.canvasui.client.js.pixi.Graphics;

/**
 * Created by ikusch on 10.10.2014.
 */
public interface HasMask {

    public void updateMaskHeight(double height);

    public void updateMaskWidth(double width);

    public void updateMaskX(double x);

    public void updateMaskY(double y);

}

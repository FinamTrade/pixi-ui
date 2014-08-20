package ru.finam.canvasui.client.js.pixi.custom;


import ru.finam.canvasui.client.js.pixi.JsObject;

/**
 * Created by ikusch on 14.08.14.
 */
public interface UpdatableComponent {

    JsObject getUpdateFunction();

    void setUpdateFunction(JsObject func);

    JsObject newUpdateFunction();

}

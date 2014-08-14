package ru.finam.canvasui.client.pixi.custom;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by ikusch on 14.08.14.
 */
public interface UpdatableComponent {

    JavaScriptObject getUpdateFunction();

    void setUpdateFunction( JavaScriptObject func );

    JavaScriptObject newUpdateFunction();

}

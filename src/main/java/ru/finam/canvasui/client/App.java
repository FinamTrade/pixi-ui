package ru.finam.canvasui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.js.JsType;
import ru.finam.canvasui.client.tests.Tests;

public class App implements EntryPoint {

    private static final String RENDERER_CONTAINER_ID = "show-area";
    private static final String MENU_CONTAINER_ID = "menu-area";

    @JsType
    public interface HTMLElement {

        void appendChild(HTMLElement element);

        void setAttribute(String key, String value);
    }

    @JsType
    public interface Document {
        HTMLElement createElement(String string);
    }


	@Override
	public void onModuleLoad() {
        Tests.load(RENDERER_CONTAINER_ID, MENU_CONTAINER_ID);
    }
	
}
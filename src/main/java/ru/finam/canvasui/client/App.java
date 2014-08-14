package ru.finam.canvasui.client;

import com.google.gwt.core.client.EntryPoint;
import ru.finam.canvasui.client.pixi.tests.Tests;

public class App implements EntryPoint {

    private static final String RENDERER_CONTAINER_ID = "show-area";
    private static final String MENU_CONTAINER_ID = "menu-area";

	@Override
	public void onModuleLoad() {
        //Window.alert("aaa");
        Tests.load(RENDERER_CONTAINER_ID, MENU_CONTAINER_ID);
    }
	
}
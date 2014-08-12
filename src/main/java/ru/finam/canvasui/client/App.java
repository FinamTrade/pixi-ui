package ru.finam.canvasui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

public class App implements EntryPoint {

    private static final String RENDERER_CONTAINER_ID = "show-area";

	@Override
	public void onModuleLoad() {
        //Window.alert("aaa");
        PixiScrollerTest.start(RENDERER_CONTAINER_ID);
    }
	
}
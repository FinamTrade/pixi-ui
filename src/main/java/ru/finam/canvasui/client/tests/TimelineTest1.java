package ru.finam.canvasui.client.tests;

import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.user.client.Window;
import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.gsap.Animation;
import ru.finam.canvasui.client.js.gsap.PropertiesSet;
import ru.finam.canvasui.client.js.gsap.TimelineLite;
import ru.finam.canvasui.client.js.gsap.TimelineLiteFactory;
import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.js.pixi.JsObject;
import ru.finam.canvasui.client.js.pixi.PointFactory;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.ScrollPanel;

/**
 * Created by ikusch on 22.08.14.
 */
public class TimelineTest1 extends PixiScrollerTest {

    @Override
    public LayoutedStage newTestStage(int width, int height, String... images) {


        LayoutedStage stage = new LayoutedStage(BG_COLOR, true);
        ScrollPanel scrollPanel = fixedSizeScrollPanel1(newSampleImage(images[2]));
        stage.addChildToCenter(scrollPanel.getMainComponent(), width, height);
        DisplayObjectContainer d = newSampleImage(images[1]);
        stage.addChild(d);
        d.setWidth(200);
        TimelineLite timelineLite = TimelineLiteFactory.newInstance();
        timelineLite.from(d, 9, new PropertiesSet().addKeyValue("alpha", 0).getJsObject());

        //Window.alert("dd = " + timelineLite._duration());
        d.setPosition(PointFactory.newInstance(23, 78));
        return stage;
    }

    private final native double getDuration(Animation a) /*-{
        return a.duration();
    }-*/;

    private final native JsObject newAnimateProps() /*-{
        return { alpha:0 };
    }-*/;

    @Override
    public String name() {
        return "TimelineTest1";
    }

    private static ScrollPanel fixedSizeScrollPanel1(DisplayObjectContainer innerPanel) {
        int width = (int) innerPanel.getWidth();
        int height = (int) innerPanel.getHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, (int) ( width / 1.4), (int) ( height / 1.4 ), true);
        return scrollPanel;
    }

}

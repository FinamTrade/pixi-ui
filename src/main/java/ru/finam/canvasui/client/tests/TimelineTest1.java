package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.js.JsObject;
import ru.finam.canvasui.client.js.gsap.Animation;
import ru.finam.canvasui.client.js.gsap.PropertiesSet;
import ru.finam.canvasui.client.js.gsap.TimelineLite;
import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
import ru.finam.canvasui.client.js.pixi.PointFactory;
import ru.finam.canvasui.client.js.pixi.custom.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.SimplePixiPanel;
import ru.finam.canvasui.client.js.pixi.custom.scroller.ScrollPanel;

/**
 * Created by ikusch on 22.08.14.
 */
public class TimelineTest1 extends PixiScrollerTest {

    public static final String NAME = "TimelineTest1";

    @Override
    public void fillStage(int width, int height, String... images) {


        stage.clear();
        ScrollPanel scrollPanel = fixedSizeScrollPanel1(new SimplePixiPanel(newSampleImage(images[2])));
        stage.addChildToCenter(scrollPanel.getMainComponent(), width, height);
        DisplayObjectContainer d = newSampleImage(images[1]);
        stage.addChild(d);
        d.setWidth(200);
        TimelineLite timelineLite = TimelineLite.Factory.newInstance();
        timelineLite.from(d, 9, new PropertiesSet().addKeyValue("alpha", 0).getJsObject());
        timelineLite.eventCallback("onComplete", newEventCallback(), null, null);

        /*
        timelineLite.delay(2.2);
        double delay = timelineLite.getDelay();
        //Window.alert("delay = " + delay);

        timelineLite.duration(2);
        double duration = timelineLite.getDuration();
        //Window.alert("duration = " + duration);

        //Window.alert("getEventCallback = " + timelineLite.getEventCallback());

        timelineLite.paused(true);
        //Window.alert("timelineLite.isPaused() = " + timelineLite.isPaused());
        timelineLite.paused(false);

        timelineLite.progress(0.3, false);
        //Window.alert("timelineLite.getProgress() = " + timelineLite.getProgress());

        //timelineLite.reversed(true);
        //Window.alert("timelineLite.isReversed() = " + timelineLite.isReversed());

        timelineLite.startTime(2.6);
        //Window.alert("timelineLite.startTime() = " + timelineLite.getStartTime());

        //Window.alert("timelineLite.getTime() = " + timelineLite.getTime());

        timelineLite.totalDuration(12.6);
        //Window.alert("timelineLite.getTotalDuration() = " + timelineLite.getTotalDuration());

        //Window.alert("timelineLite.getTotalProgress() = " + timelineLite.getTotalProgress());

        //Window.alert("timelineLite.getTotalTime() = " + timelineLite.getTotalTime());
        */
        d.setPosition(PointFactory.newInstance(23, 78));
    }

    private final native JsObject newEventCallback() /*-{
        return function() {
            alert('Animation completed!');
        };
    }-*/;

    private final native JsObject newAnimateProps() /*-{
        return { alpha:0 };
    }-*/;

    private static ScrollPanel fixedSizeScrollPanel1(CustomComponentContainer innerPanel) {
        int width = (int) innerPanel.getWidth();
        int height = (int) innerPanel.getHeight();
        ScrollPanel scrollPanel =  ScrollPanel.newInstance(innerPanel, (int) ( width / 1.4), (int) ( height / 1.4 ), true);
        return scrollPanel;
    }

}

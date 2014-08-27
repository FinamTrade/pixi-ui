package ru.finam.canvasui.client.tests;

import ru.finam.canvasui.client.js.JsObject;
import ru.finam.canvasui.client.js.gsap.Animation;
import ru.finam.canvasui.client.js.gsap.PropertiesSet;
import ru.finam.canvasui.client.js.gsap.TimelineLite;
import ru.finam.canvasui.client.js.pixi.DisplayObjectContainer;
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
        TimelineLite timelineLite = TimelineLite.Factory.newInstance();
        timelineLite.from(d, 9, new PropertiesSet().addKeyValue("alpha", 0).getJsObject());

        timelineLite.delay(2.2);
        double delay = timelineLite.getDelay();
        //Window.alert("delay = " + delay);

        timelineLite.duration(2);
        double duration = timelineLite.getDuration();
        //Window.alert("duration = " + duration);

        timelineLite.eventCallback("onComplete", newEventCallback(), null, null);
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

        d.setPosition(PointFactory.newInstance(23, 78));
        return stage;
    }

    private final native double getDuration(Animation a) /*-{
        return a.duration();
    }-*/;

    private final native JsObject newEventCallback() /*-{
        return function() {
            alert('Animation completed!');
        };
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

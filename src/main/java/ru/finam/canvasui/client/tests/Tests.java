package ru.finam.canvasui.client.tests;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import ru.finam.canvasui.client.js.gsap.TweenLite;
import ru.finam.canvasui.client.js.gsap.easing.*;
import ru.finam.canvasui.client.js.pixi.*;
import ru.finam.canvasui.client.js.pixi.custom.LayoutedStage;
import ru.finam.canvasui.client.js.pixi.custom.UpdatableRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikusch on 14.08.14.
 */
public class Tests {

    private static final int BG_COLOR = 0xFFFFFF;
    private static final String MENU_ITEM_CLASSNAME = "menu-item";
    private static final String MENU_ITEM_HOLDER_CLASSNAME = "menu-item-holder";
    private static List<Texture> textures = new ArrayList<Texture>();
    private static final String SAMPLE_IMAGE1 = "img/city1.png";
    private static final String SAMPLE_IMAGE2 = "img/city2.png";
    private static final String SAMPLE_IMAGE3 = "img/funny.jpg";
    private static final String SAMPLE_IMAGE4 = "img/funny2.jpg";
    private static final String SAMPLE_IMAGE5 = "img/city2bordered.png";
    private static final String SAMPLE_IMAGE6 = "img/funnyBordered.jpg";
    private static List<TestHolder> tests = new ArrayList<TestHolder>();
    private TestHolder currentTest;
    private static String[] images = newImagesArray();
    private static boolean assetsLoaded = false;
    private String menuContainerId;
    private String rendererContainerId;
    private static Tests inst;

    private abstract class TestHolder {
        abstract PixiScrollerTest newInst();
    }

    public Tests(String rendererContainerId, String menuContainerId) {
        inst = this;
        this.rendererContainerId = rendererContainerId;
        this.menuContainerId = menuContainerId;
    }

    private static String[] newImagesArray() {
        List<String> al = new ArrayList<String>();
        al.add(SAMPLE_IMAGE1);
        al.add(SAMPLE_IMAGE2);
        al.add(SAMPLE_IMAGE3);
        al.add(SAMPLE_IMAGE4);
        al.add(SAMPLE_IMAGE5);
        al.add(SAMPLE_IMAGE6);
        return al.toArray(new String[]{});
    }

    public void start() {
        exportMyFunction();
        JsArrayString assets = JsArrayString.createArray().cast();
        for (String i:images) {
            assets.push(i);
        }
        if (!assetsLoaded)
            loadAssets(rendererContainerId, assets);
        else
            assetsLoaded();
    }

    private final native void loadAssets(String rendererContainerId, JsArrayString assetsToLoad) /*-{
        var loader = new $wnd.PIXI.AssetLoader(assetsToLoad);
        loader.onComplete = function() {
            $wnd.PixiScrollerTest.onAssetsLoaded();
        };
        loader.load();
    }-*/;

    public static final native void exportMyFunction() /*-{
        $wnd.PixiScrollerTest = {};
        $wnd.PixiScrollerTest.onAssetsLoaded = function() {
            $entry(@ru.finam.canvasui.client.tests.Tests::onAssetsLoaded2()());
        }
    }-*/;

    public static void onAssetsLoaded2() {
        inst.assetsLoaded();
    }

    public void assetsLoaded() {
        assetsLoaded = true;
        RootPanel element = RootPanel.get(rendererContainerId);
        element.getElement().getStyle().setPadding(0, Style.Unit.PX);
        element.clear(true);
        int width = element.getElement().getOffsetWidth();
        int height = element.getElement().getClientHeight();
        PixiScrollerTest.renderer = UpdatableRenderer.addNewAuoDetectRenderer(element, width, height);
        element.getElement().appendChild(PixiScrollerTest.renderer.getView());
        currentTest.newInst().fillStage(width, height, images);
    }

    public void load() {
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new PixiScrollerTest1();
            }
        }, menuContainerId, PixiScrollerTest1.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new PixiScrollerTest2();
            }
        }, menuContainerId, PixiScrollerTest2.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new PixiScrollerTest3();
            }
        }, menuContainerId, PixiScrollerTest3.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new PixiScrollerTest4();
            }
        }, menuContainerId, PixiScrollerTest4.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new PixiScrollerTest6();
            }
        }, menuContainerId, PixiScrollerTest6.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new TimelineTest1();
            }
        }, menuContainerId, TimelineTest1.NAME);

        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new GrowingTableTest();
            }
        }, menuContainerId, GrowingTableTest.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new GrowingTableTest2();
            }
        }, menuContainerId, GrowingTableTest2.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new BigTableTest1();
            }
        }, menuContainerId, BigTableTest1.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new BigTableTest2();
            }
        }, menuContainerId, BigTableTest2.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new BigTableTest3();
            }
        }, menuContainerId, BigTableTest3.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new BigTableTest4();
            }
        }, menuContainerId, BigTableTest4.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new BigTableTest5();
            }
        }, menuContainerId, BigTableTest5.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new BigTableTest6();
            }
        }, menuContainerId, BigTableTest6.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new BigTableTest7();
            }
        }, menuContainerId, BigTableTest7.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new BigTableTest8();
            }
        }, menuContainerId, BigTableTest8.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new TableTest3();
            }
        }, menuContainerId, TableTest3.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new TableTest4();
            }
        }, menuContainerId, TableTest4.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new TableTest5();
            }
        }, menuContainerId, TableTest5.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new TableTest6();
            }
        }, menuContainerId, TableTest6.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new GrowingAndDecreasingTableTest();
            }
        }, menuContainerId, GrowingAndDecreasingTableTest.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new GrowingAndDecreasingTableTest2();
            }
        }, menuContainerId, GrowingAndDecreasingTableTest2.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new GrowingAndDecreasingTableTest3();
            }
        }, menuContainerId, GrowingAndDecreasingTableTest3.NAME);
        addNewTest(rendererContainerId, new TestHolder() {
            @Override
            PixiScrollerTest newInst() {
                return new BorderedImageTest();
            }
        }, menuContainerId, BorderedImageTest.NAME);
        currentTest = tests.get(0);
        start();
    }

    private void addNewTest(final String rendererContainerId, final TestHolder testHolder, String menuContainerId,
                            String testName) {
        final TestHolder test = testHolder;
        addNewTest(rendererContainerId, test, menuContainerId, new ClickHandler() {
            public void onClick(ClickEvent event) {
                currentTest = test;
                start();
            }
        }, testName);
    }

    private static void addNewTest(final String rendererContainerId, final TestHolder pixiScrollerTest, String menuContainerId, ClickHandler clickHandler, String name) {
        tests.add(pixiScrollerTest);
        FlowPanel flowPanel = new FlowPanel();
        flowPanel.getElement().addClassName(MENU_ITEM_HOLDER_CLASSNAME);
        FocusPanel panel = new FocusPanel();
        panel.getElement().setInnerHTML(""+name);
        panel.getElement().addClassName(MENU_ITEM_CLASSNAME);
        panel.addClickHandler(clickHandler);
        flowPanel.add(panel);
        RootPanel.get(menuContainerId).add(flowPanel);
    }

}

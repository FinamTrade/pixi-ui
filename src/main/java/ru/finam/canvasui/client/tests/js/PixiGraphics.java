package ru.finam.canvasui.client.tests.js;

import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.js.impl.PrototypeOfJsType;

/**
 * Created by ikusch on 15.08.14.
 */
@JsType(prototype = "$wnd.Graphics")
public interface PixiGraphics {

    void lineStyle(int lineWidth, int color, double alpha);

    void drawRect(int x, int y, int width, int height);

    void beginFill(int color, double alpha);

    void drawCircle(double x, double y, double radius);

    void drawEllipse(double x, double y, double width, double height);

    @PrototypeOfJsType
    static class Prototype implements PixiGraphics {

        public void lineStyle(int lineWidth, int color, double alpha) {
        }

        public void drawRect(int x, int y, int width, int height) {

        }

        public void beginFill(int color, double alpha) {

        }

        public void drawCircle(double x, double y, double radius) {

        }

        public void drawEllipse(double x, double y, double width, double height) {

        }
    }

}
package ru.finam.canvasui.client.js.pixi.custom.panel;

import ru.finam.canvasui.client.js.gsap.TimelineLite;
import ru.finam.canvasui.client.js.pixi.*;

/**
 * Created by ikusch on 14.10.2014.
 */
public interface CustomComponent<T extends DisplayObject> {

    T getMainComponent();

    TimelineLite timeline();

    void setDragging( boolean b );

    boolean isDragging();

    void setAlpha(double a);

    double getAlpha();

    Rectangle getBounds();

    void setMask(Graphics mask);

    Graphics getMask();

    void setHitArea(Rectangle hitArea);

    Rectangle getHitArea();

    void setPosition(Point position);

    void setAlpha(int alpha);

    Point getPosition();

    double getBoundedWidth();

    double getBoundedHeight();

    Rectangle getLocalBounds();

    DisplayObjectContainer getParent();

    void setInteractive(boolean b);

    void setButtonMode(boolean b);

    boolean getButtonMode();

    boolean getInteractive();

    Rectangle croppedBounds();

}

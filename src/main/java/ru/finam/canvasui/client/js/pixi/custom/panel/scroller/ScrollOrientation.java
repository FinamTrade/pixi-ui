package ru.finam.canvasui.client.js.pixi.custom.panel.scroller;

import ru.finam.canvasui.client.js.pixi.Point;
import ru.finam.canvasui.client.js.pixi.PointFactory;
import ru.finam.canvasui.client.js.pixi.Rectangle;
import ru.finam.canvasui.client.js.pixi.custom.panel.CustomComponent;
import ru.finam.canvasui.client.js.pixi.custom.panel.CustomComponentContainer;

/**
 * Created by ikusch on 02.09.2014.
 */
public enum ScrollOrientation {

    HORIZONTAL {
        @Override
        public double getLength(CustomComponentContainer d) {
            return d.getWidth();
        }

        @Override
        public double getLength(Rectangle d) {
            return d.getWidth();
        }

        @Override
        public double getBoundedLength(CustomComponent d) {
            return d.getBoundedWidth();
        }

        @Override
        public void setOffset(Point position, double value) {
            position.setX(value);
        }

        @Override
        public double getOffset(Point position) {
            return position.getX();
        }

        @Override
        public double getOrtogonalLength(Rectangle rectangle) {
            return rectangle.getHeight();
        }

        @Override
        public Point newPoint(double directOffset, double ortogonalOffset) {
            return PointFactory.newInstance(directOffset, ortogonalOffset);
        }

    }, VERTICAL {
        @Override
        public double getLength(CustomComponentContainer d) {
            return d.getHeight();
        }

        @Override
        public double getLength(Rectangle d) {
            return d.getHeight();
        }

        @Override
        public double getBoundedLength(CustomComponent d) {
            return d.getBoundedHeight();
        }

        @Override
        public void setOffset(Point position, double value) {
            position.setY(value);
        }

        @Override
        public double getOffset(Point position) {
            return position.getY();
        }

        @Override
        public double getOrtogonalLength(Rectangle rectangle) {
            return rectangle.getWidth();
        }

        @Override
        public Point newPoint(double directOffset, double ortogonalOffset) {
            return PointFactory.newInstance(ortogonalOffset, directOffset);
        }

    };

    public abstract double getLength(CustomComponentContainer d);

    public abstract double getLength(Rectangle d);

    public abstract double getBoundedLength(CustomComponent d);

    public abstract void setOffset(Point position, double value);

    public abstract double getOffset(Point position);

    public abstract double getOrtogonalLength(Rectangle rectangle);

    public abstract Point newPoint(double directOffset, double ortogonalOffset);

}

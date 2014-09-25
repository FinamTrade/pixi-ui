package ru.finam.canvasui.client.js.pixi.custom.table;

import ru.finam.canvasui.client.JsConsole;
import ru.finam.canvasui.client.js.pixi.*;
import ru.finam.canvasui.client.js.pixi.custom.CustomComponentContainer;
import ru.finam.canvasui.client.js.pixi.custom.UpdatableComponent;
import ru.finam.canvasui.client.js.pixi.custom.channel.ComponentUpdateEventChannel;
import ru.finam.canvasui.client.js.pixi.custom.event.ComponentUpdateEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ikusch on 27.08.14.
 */
public class PixiTable extends CustomComponentContainer implements UpdatableComponent {

    private static final int DEFAULT_CELL_WIDTH = 70;
    private static final int DEFAULT_CELL_HEIGHT = 30;
    private static final int LINE_COLOR = 0x000000;
    private static final int LINE_WIDTH = 1;
    private static final int DEFAUT_BACKGROUND_COLOR = 0xCCCCCC;
    private static final double MARGIN_Y = 0;
    protected int colsCount;
    private int rowsCount = 0;
    private double cellWidth = DEFAULT_CELL_WIDTH;
    private double cellHeight = DEFAULT_CELL_HEIGHT;
    private Map<Integer, Double> colsWidths = new HashMap<>();
    private Map<Integer, Double> rowsHeights = new HashMap<>();
    private ComponentUpdateEventChannel componentUpdateEventChannel = ComponentUpdateEventChannel.newInstance();
    private ComponentUpdateEvent componentUpdateEvent = new ComponentUpdateEvent();

    public PixiTable(int colsCount, int cellWidth, int cellHeight) {
        this(colsCount);
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
    }

    public PixiTable(int colsCount) {
        this.colsCount = colsCount;
    }

    public void colWidth(int col, double width) {
        colsWidths.put(col, width);
    }

    public void rowHeight(int row, double height) {
        rowsHeights.put(row, height);
    }

    public double rowHeight(int row) {
        Double h = rowsHeights.get(row);
        if (h == null)
            h = (double) DEFAULT_CELL_HEIGHT;
        return h;
    }

    public double colWidth(int col) {
        Double w = colsWidths.get(col);
        if (w == null)
            w = (double) DEFAULT_CELL_WIDTH;
        return w;
    }

    public void addRow(ValuesRow row) {
        Graphics graphics = paintRow(row);
        addChild(graphics);
        graphics.setPosition(justAddedRowPosition());
        setHeight(getHeight());//magic
        rowsCount++;
        componentUpdateEventChannel.fire(componentUpdateEvent);
    }

    private Point justAddedRowPosition() {
        return PointFactory.newInstance(0, rowsCount * ( rowHeight(rowsCount) ));
    }

    private void drawFilledRect(int i, Graphics graphics) {
        graphics.lineStyle(0, LINE_COLOR, 0);
        graphics.beginFill(DEFAUT_BACKGROUND_COLOR, 0.6);
        graphics.drawRect(colWidth(i) * i, 1 + MARGIN_Y, colWidth(i), rowHeight(rowsCount) - 1);
        graphics.endFill();
    }

    private void drawText(ValuesRow row, int i, Graphics graphics) {
        Text text = Text.Factory.construct(row.stringValueOrEmpty(i), TextStyle.newInstance("14px Arial", "#000000", "left", "#000000"));
        text.setAnchor(PointFactory.newInstance(0.5, 0.5));
        graphics.addChild(text);
        double textX = colWidth(i) * (i + 0.5);
        double textY = rowHeight(rowsCount) * (0.5) + MARGIN_Y ;
        text.setPosition(PointFactory.newInstance(textX, textY));
    }

    private Graphics paintRow(ValuesRow row) {
        Graphics graphics = Graphics.Factory.newInstance();
        for (int i = 0; i < colsCount; ++i) {
            drawFilledRect(i, graphics);
            drawText(row, i, graphics);
            graphics.lineStyle(LINE_WIDTH, LINE_COLOR, 1);
            graphics.moveTo(colWidth(i) * i, rowHeight(rowsCount) + MARGIN_Y );
            graphics.lineTo(colWidth(i) * (i + 1) - 1, rowHeight(rowsCount) + MARGIN_Y );
            graphics.lineTo(colWidth(i) * ( i + 1) - 1, MARGIN_Y);
            if (rowsCount == 0) {
                graphics.moveTo(colWidth(i) * i, 0);
                graphics.lineTo(colWidth(i) * (i + 1), 0);
            }
        }
        graphics.lineStyle(LINE_WIDTH, LINE_COLOR, 1);
        graphics.moveTo(0, rowHeight(rowsCount) + MARGIN_Y);
        graphics.lineTo(0, MARGIN_Y);
        return graphics;
    }

    public void removeLastRow() {
        getChildren().pop();
        rowsCount--;
        setHeight(getHeight());//magic
        componentUpdateEventChannel.fire(componentUpdateEvent);
    }

    @Override
    public ComponentUpdateEventChannel componentUpdateEventChannel() {
        return componentUpdateEventChannel;
    }
//
}
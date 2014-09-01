package ru.finam.canvasui.client.js.pixi.custom.table;

import ru.finam.canvasui.client.JsConsole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikusch on 27.08.14.
 */
public class RandomValuesTable extends PixiTable {

    private static final int MAX_RANDOM = 1000;

    public RandomValuesTable(int colsCount, int rowsCount) {
        super(colsCount);
        for (int i = 0; i < rowsCount; ++i) {
            addRow(newRandomRow(colsCount));
        }
    }

    public void addRandomRow() {
        addRow(newRandomRow(colsCount));
    }

    private ValuesRow newRandomRow(int colsCount) {
        List<Object> values = new ArrayList<>();
        for (int i=0; i<colsCount; ++i) {
            Integer val = (int) ( Math.random() * MAX_RANDOM );
            values.add(val);
        }
        return new ValuesRow(values);
    }

}

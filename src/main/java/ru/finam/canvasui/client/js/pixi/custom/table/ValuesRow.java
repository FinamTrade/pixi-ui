package ru.finam.canvasui.client.js.pixi.custom.table;

import java.util.List;

/**
 * Created by ikusch on 27.08.14.
 */
public class ValuesRow {

    private List<Object> values;

    public ValuesRow(List<Object> values) {
        this.values = values;
    }

    public String stringValueOrEmpty(int i) {
        String s = "";
        if (i < values.size())
            s = values.get(i).toString();
        return s;
    }

}

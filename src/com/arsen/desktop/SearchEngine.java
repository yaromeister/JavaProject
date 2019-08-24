package com.arsen.desktop;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.regex.Pattern;

public class SearchEngine {
    public static void filter(JTextField searchField, TableRowSorter sorter){
        RowFilter<TableModel, Object> rowFilter = null;
        //If current expression doesn't parse, don't update.
        if(searchField.getText().length()== 0){
            sorter.setRowFilter(null);
            return;
        }
        else {try {
            String text = Pattern.quote(searchField.getText());
            String regex = String.format("(?i).*%s.*", text);
            rowFilter = RowFilter.regexFilter(regex);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }}

        sorter.setRowFilter(rowFilter);
    }
}

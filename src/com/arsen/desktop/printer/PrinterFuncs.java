package com.arsen.desktop.printer;

import com.arsen.desktop.Table;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;


public class PrinterFuncs {
    public static void addHeader(PdfPTable table){
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
        //adds header
        for(int columnIndex = 0; columnIndex< Table.instance.getTable().getColumnCount(); columnIndex++ ){
            table.addCell(new PdfPCell(new Phrase(Table.instance.getTable().getColumnName(columnIndex), headerFont)));
        }
    }

    public static void addCells(PdfPTable table){
        Font cellFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
        //adds cells
        for(int row = 0; row<Table.instance.getTable().getRowCount(); row++){
            for(int col = 0; col<Table.instance.getTable().getColumnCount(); col++){
                PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(Table.instance.getTable().getValueAt(row,col)),cellFont));
                table.addCell(cell).setMinimumHeight(40);
            }
        }
    }

    public static PdfPTable instantiateTable(int numberOfColumns){
        PdfPTable table = new PdfPTable(numberOfColumns);

        table.setHeaderRows(1);
        table.setWidthPercentage(105);

        return table;
    }


}

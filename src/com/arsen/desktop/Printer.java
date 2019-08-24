package com.arsen.desktop;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class Printer {
    public static void printPDF(String filePath)
    {

        try {
            String fileName = filePath + "\\Report.pdf";

            Document document = new Document(PageSize.A4.rotate());

            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();


            PdfPTable table = new PdfPTable(13);

            table.setHeaderRows(1);
            table.setWidthPercentage(105);

            Font cellFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);

            //adds header
            for(int columnIndex =0; columnIndex<Table.instance.getTable().getColumnCount(); columnIndex++ ){
                table.addCell(new PdfPCell(new Phrase(Table.instance.getTable().getColumnName(columnIndex), headerFont)));
            }

            //adds cells
            for(int row = 0; row<Table.instance.getTable().getRowCount(); row++){
                for(int col = 0; col<Table.instance.getTable().getColumnCount(); col++){
                    PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(Table.instance.getTable().getValueAt(row,col)),cellFont));
                    table.addCell(cell).setMinimumHeight(40);
                }
            }

            document.add(table);


            document.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();

        }
    }

}

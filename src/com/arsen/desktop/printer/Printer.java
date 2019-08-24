package com.arsen.desktop.printer;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
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


            PdfPTable table = PrinterFuncs.instantiateTable(13);

            PrinterFuncs.addHeader(table);

            PrinterFuncs.addCells(table);

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

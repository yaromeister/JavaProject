package com.arsen.desktop;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomMaskFormatter {

    //Used to set restriction on input

    public static void setMaskFormatters(JFormattedTextField[] formattedTextFields)
    {
        //Formatter Factory is the only way to set formatters after creation of JFormattedTextField(they were created automatically)
        ResultSet metaDataSet = null;
        try {
            metaDataSet = MetaDataManager.getMetaData();

            int i = 0;
            //Dynamically reads column data type and set formatters accordingly
            metaDataSet.next(); //Skip ID
            while (metaDataSet.next())
            {
                String type = metaDataSet.getString("TYPE_NAME");
                int columnSize = Integer.parseInt(metaDataSet.getString("COLUMN_SIZE"));
                String maskFormat;

                switch(type){
                    case "INT":
                        maskFormat = "#";
                        formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(CustomMaskFormatter.createMaskFormatter(maskFormat.repeat(columnSize))));
                        formattedTextFields[i].setUI(new HintTextFieldUI("123456789", true, Color.LIGHT_GRAY));

                        break;
                    case "VARCHAR":
                        //phone number is varchar in DB but only numbers should be inputed
                        if(metaDataSet.getString("COLUMN_NAME").toLowerCase().equals("Phone number".toLowerCase()))
                        {
                            maskFormat = "#";
                            formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(CustomMaskFormatter.createMaskFormatter(maskFormat.repeat(columnSize))));
                            formattedTextFields[i].setUI(new HintTextFieldUI("012345678", true, Color.LIGHT_GRAY));

                        }
                        else {
                            maskFormat = "*";
                            formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(CustomMaskFormatter.createMaskFormatter(maskFormat.repeat(columnSize))));
                            formattedTextFields[i].setText(null);
                            formattedTextFields[i].setUI(new HintTextFieldUI("Some text", true, Color.LIGHT_GRAY));
                        }

                        break;

                    case"DATE":
                        formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(CustomMaskFormatter.createMaskFormatter("####*##*##")));
                        formattedTextFields[i].setUI(new HintTextFieldUI("yyyy-MM-dd", true, Color.LIGHT_GRAY));

                        break;
                    case"DOUBLE":
                        //to input '.' symbol you have to include all symbols and than allow only numbers and dot
                        maskFormat = "*";
                        MaskFormatter doubleFormatter = CustomMaskFormatter.createMaskFormatter(maskFormat.repeat(columnSize));
                        doubleFormatter.setValidCharacters("0123456789.");
                        formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(doubleFormatter));
                        formattedTextFields[i].setUI(new HintTextFieldUI("1234.5678", true, Color.LIGHT_GRAY));

                        break;
                    default:
                        System.out.println("Type error");
                }
                i++;
            }
        } catch (SQLException | NullPointerException e)
        {
            e.printStackTrace();
        }
        finally{
            try{metaDataSet.close();}catch (SQLException | NullPointerException e){
                System.out.println("Unable to close");
            }
        }
    }

    private static MaskFormatter createMaskFormatter(String s)
    {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);

        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }

        return formatter;
    }
}

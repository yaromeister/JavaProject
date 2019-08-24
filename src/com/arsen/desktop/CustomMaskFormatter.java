package com.arsen.desktop;

import javax.swing.text.MaskFormatter;

public class CustomMaskFormatter {
    public static MaskFormatter createMaskFormatter(String s)
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

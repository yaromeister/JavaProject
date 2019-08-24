package com.arsen.desktop;

import javax.swing.*;

public class AutoDash {
    public static void putDash(JFormattedTextField textField){
        SwingUtilities.invokeLater(() -> {

            if(textField.getText().replaceAll("\\s+","").length() == 5){
                StringBuilder sb = new StringBuilder(textField.getText().replaceAll("\\s+","")).insert(4,"-");
                String newText = sb.toString() ;
                textField.setText(newText);
            }else if(textField.getText().replaceAll("\\s+","").length() == 8){
                StringBuilder sb = new StringBuilder(textField.getText().replaceAll("\\s+","")).insert(7,"-");
                String newText = sb.toString() ;
                textField.setText(newText);
            }});
    }
}

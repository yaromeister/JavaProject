package com.arsen.desktop;

import com.arsen.desktop.slider.RangeSlider;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoolFilter {
    public static void set(JComboBox comboBox, JPanel boolPanel, JFormattedTextField minVal, JFormattedTextField maxVal){
        RangeSlider rangeSlider = new RangeSlider();
        rangeSlider.setPreferredSize(new Dimension(420, rangeSlider.getPreferredSize().height));
        rangeSlider.setMinimum(0);
        rangeSlider.setMaximum(10);


        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String selectedItem = (String)cb.getSelectedItem();

            }});

        // Add listener to update display.
        rangeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                RangeSlider slider = (RangeSlider) e.getSource();
                setMinMax(minVal, maxVal);
                comboBox.getAccessibleContext();

            }
        });
        comboBox.setSize(120,30);
        comboBox.setModel(new DefaultComboBoxModel(new Object[]{"Date of birth", "Salary", "Working Since", "Room Number"}));

        boolPanel.add(rangeSlider, BorderLayout.EAST);
        boolPanel.repaint();
        boolPanel.repaint();
    }

    public static void setMinMax(JFormattedTextField minVal, JFormattedTextField maxVal){


    }

}

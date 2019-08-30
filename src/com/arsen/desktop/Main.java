package com.arsen.desktop;

import javax.swing.*;

public class Main {
    private static JFrame frame = new JFrame("Application");

    public static JFrame getFrame(){
        return frame;
    }

    public static void changeVisiblePanel(JPanel oldPanel, JPanel newPanel)
    {
        oldPanel.setVisible(false);
        newPanel.setVisible(true);
        frame.setSize(newPanel.getPreferredSize());
        //multiclass change
    }


    public static void main(String[] args) {
        Table.instance.setTable();

        //instance.northPanel.setLayout(new BoxLayout(instance.northPanel, BoxLayout.PAGE_AXIS));

        frame.setContentPane(Table.instance.getParentPanel());
        frame.setLocation(500,50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}

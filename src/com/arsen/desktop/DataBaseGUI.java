package com.arsen.desktop;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class DataBaseGUI {

    //Singleton
    public static DataBaseGUI instance = new DataBaseGUI();


    // region MainMenuVariables
    private JButton addWorkerButton;
    private JPanel parentPanel;
    private JButton viewWorkerButton;
    private JButton editWorkerButton;
    private JButton deleteWorkerButton;
    private JButton printReportButton;
    private JPanel mainPanel;
    private JTextArea chooseAnOptionTextArea;
    //endregion

    private String operationsWorkerID;
    private String question;
    private static JFrame frame = new JFrame("Application");

    private Desktop desktop = Desktop.getDesktop();


    public DataBaseGUI()
    {

        addWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Button to Add Worker to a Data Base
                question = "Do you really want to add Worker?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    openAddWorkerForm();

                }
            }
        });
        viewWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Button to Outputs specified worker's data
                question = "Do you really want to view Worker's info?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    openViewWorkerFormDialog();
                }
            }
        });
        editWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Button to Edit specified worker's data
                question = "Do you really want to edit Worker's info?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    openEditWorkerFormDialog();
                }
            }
        });

        deleteWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Button to Delete specified worker from the Data Base
                question = "Do you really want to delete Worker?";

                int firstDialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(firstDialogResult == JOptionPane.YES_OPTION)
                {
                    deleteWorkerDialog();
                }
            }
        });

        printReportButton.addActionListener(new ActionListener() {          //Button to Prints report in table format(maybe will add PDF option)
            @Override
            public void actionPerformed(ActionEvent e) {
                question = "Do you really want to print a Report?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    printReport();
                }
            }
        });

    }

    private void openAddWorkerForm()
    {

        DataBaseManager.setMaskFormatters(AddWorkerForm.instance.getFormattedTextFields());

        changeVisiblePanel(mainPanel, AddWorkerForm.instance.getPanel());
        frame.setContentPane(AddWorkerForm.instance.getPanel());

    }

    private void openViewWorkerFormDialog()
    {
        operationsWorkerID = JOptionPane.showInputDialog(null,"Input ID of the worker you want to view");

        if(operationsWorkerID != null)
        {
            if(DataBaseManager.checkIfIDExists(operationsWorkerID))
            {
                openViewWorkerForm(operationsWorkerID);
            }else
                {
                    while (!DataBaseManager.checkIfIDExists(operationsWorkerID)) {
                        operationsWorkerID = JOptionPane.showInputDialog(null, "There is no worker with such an ID, please try another one");
                        if(operationsWorkerID == null)
                            return;
                    }
                    openViewWorkerForm(operationsWorkerID);

                }

        }

    }

    private void openEditWorkerFormDialog()
    {
        operationsWorkerID = JOptionPane.showInputDialog(null,"Input ID of the worker you want to edit");

        if(operationsWorkerID != null) {
            if(DataBaseManager.checkIfIDExists(operationsWorkerID))
            {
                openEditWorkerForm(operationsWorkerID);
            }else
            {
                while (!DataBaseManager.checkIfIDExists(operationsWorkerID)) {
                    //Check if there is such ID in database
                    operationsWorkerID = JOptionPane.showInputDialog(null, "There is no worker with such an ID, please try another one");
                    if(operationsWorkerID == null)
                        return;
                }

                openEditWorkerForm(operationsWorkerID);

            }

        }
    }

    private void openEditWorkerForm(String operationsWorkerID)
    {

        DataBaseManager.setMaskFormatters(EditWorkerForm.instance.getFormattedFields());

        DataBaseManager.setFieldValuesFromDB(operationsWorkerID,EditWorkerForm.instance.getFormattedFields());

        changeVisiblePanel(mainPanel, EditWorkerForm.instance.getPanel());
        frame.setContentPane(EditWorkerForm.instance.getPanel());
    }

    private void deleteWorkerDialog()
    {
        operationsWorkerID = JOptionPane.showInputDialog(null,"Which worker do you want to delete?");
        if(operationsWorkerID != null)
        {
            if(DataBaseManager.checkIfIDExists(operationsWorkerID))
            {
                deleteWorker(operationsWorkerID);
            }else {
                while (!DataBaseManager.checkIfIDExists(operationsWorkerID)) {
                    //Check if there is such ID in database
                    operationsWorkerID = JOptionPane.showInputDialog(null, "There is no worker with such an ID, please try another one");
                    if (operationsWorkerID == null)
                        return;
                }

                deleteWorker(operationsWorkerID);

            }

        }

    }

    private void printReport()
    {
        String path;
        //creating file chooser window which allows us to choose directories
        final JFileChooser chooser = new JFileChooser();

        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = chooser.showDialog(mainPanel,"Save here");

        if(returnValue == JFileChooser.APPROVE_OPTION){
            path = chooser.getSelectedFile().getAbsolutePath();
            DataBaseManager.printPDF(path);
            File file = new File(path + "\\Report.pdf");
            try {
                desktop.open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void changeVisiblePanel(JPanel oldPanel, JPanel newPanel)
    {
        oldPanel.setVisible(false);
        newPanel.setVisible(true);
        frame.setSize(newPanel.getPreferredSize());

        //multiclass change
    }

    private void deleteWorker(String deleteID)
    {

        int askToViewWorker = JOptionPane.showConfirmDialog(null, "Do you want to view info about this worker before deleting?");

        if(askToViewWorker == JOptionPane.YES_OPTION)
        {
            openViewWorkerForm(operationsWorkerID);
            ViewWorkerForm.instance.setDeleteButtonVisible(true);
        }
        else if(askToViewWorker == JOptionPane.NO_OPTION){
            DataBaseManager.deleteRowFromTheTable(deleteID);
        }


    }

    private void openViewWorkerForm(String viewID)
    {
        ViewWorkerForm.instance.setDeleteButtonVisible(false);

        DataBaseManager.setFieldValuesFromDB(viewID,ViewWorkerForm.instance.getLabelArray());


        changeVisiblePanel(mainPanel, ViewWorkerForm.instance.getPanel());
        frame.setContentPane(ViewWorkerForm.instance.getPanel());
    }


    public static JFrame getFrame(){
        return frame;
    }


    public JPanel getMainPanel(){
        return mainPanel;
    }

    public String getOperationsWorkerID(){
        return operationsWorkerID;
    }



    public static void main(String[] args) {
        Table.instance.setTable();
        frame.setContentPane(Table.instance.getParentPanel());
        //frame.setContentPane(instance.parentPanel);
        frame.setLocation(500,50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

}

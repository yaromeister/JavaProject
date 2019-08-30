package com.arsen.desktop;

import com.arsen.desktop.printer.Printer;
import com.arsen.desktop.validators.IDValidator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;

public class Table {
    public static Table instance = new Table();

    private JPanel parentPanel;
    private JScrollPane scrollPane;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton printReportButton;
    private JFormattedTextField searchField;
    private JPanel northPanel;
    private JFormattedTextField maxVal;
    private JFormattedTextField minVal;

    private String operationsWorkerID;
    private String question;


    private Desktop desktop = Desktop.getDesktop();

    private JTable table;

    private AllTableModel model = new AllTableModel();
    private TableRowSorter sorter = new TableRowSorter<AllTableModel>(model);


    private Table(){

        searchField.setUI(new HintTextFieldUI("Search", true, Color.LIGHT_GRAY));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                question = "Do you really want to add Worker?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    AddWorkerForm.openAddWorkerForm();

                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question = "Do you really want to edit Worker?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    EditWorkerForm.openEditWorkerFormDialog();

                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question = "Do you really want to delete Worker?";

                int firstDialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(firstDialogResult == JOptionPane.YES_OPTION)
                {
                    deleteWorkerDialog();
                }
            }
        });
        printReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question = "Do you really want to print a Report?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    openFileChooser();
                }
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                SearchEngine.filter(getSearchField(),sorter);

            }

            public void removeUpdate(DocumentEvent e) {
                SearchEngine.filter(getSearchField(),sorter);
            }

            public void insertUpdate(DocumentEvent e) {
                SearchEngine.filter(getSearchField(),sorter);
            }

        });

        //BoolFilter.set(comboBox, boolPanel, minVal, maxVal);

    }

    private void deleteWorkerDialog()
    {
        operationsWorkerID = JOptionPane.showInputDialog(null,"Which worker do you want to delete?");
        if(operationsWorkerID != null)
        {
            if(IDValidator.checkIfIDExists(operationsWorkerID))
            {
                deleteWorker(operationsWorkerID);
            }else {
                while (!IDValidator.checkIfIDExists(operationsWorkerID)) {
                    //Check if there is such ID in database
                    operationsWorkerID = JOptionPane.showInputDialog(null, "There is no worker with such an ID, please try another one");
                    if (operationsWorkerID == null)
                        return;
                }

                deleteWorker(operationsWorkerID);

            }

        }

    }

    private void deleteWorker(String deleteID)
    {
        DataBaseManager.deleteRowFromTheTable(deleteID);
    }

    private void openFileChooser()
    {
        String path;
        //creating file chooser window which allows us to choose directories
        final JFileChooser chooser = new JFileChooser();

        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = chooser.showDialog(parentPanel,"Save here");

        if(returnValue == JFileChooser.APPROVE_OPTION){
            path = chooser.getSelectedFile().getAbsolutePath();
            Printer.printPDF(path);
            File file = new File(path + "\\Report.pdf");
            try {
                desktop.open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public JPanel getParentPanel(){
        return parentPanel;
    }

    public JTable getTable(){
        return table;
    }

    private JFormattedTextField getSearchField(){
        return searchField;
    }

    public void setTable(){

        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(700, 250));
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);


        JScrollPane scrollPane = new JScrollPane(table);
        parentPanel.add(scrollPane);
        parentPanel.revalidate();
        parentPanel.repaint();

    }

}

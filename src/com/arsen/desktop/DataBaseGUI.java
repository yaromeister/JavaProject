package com.arsen.desktop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataBaseGUI {
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

    //region AddWorkerVariables
    private JPanel addWorkerPanel;
    private JTextArea workerIDText;
    private JTextArea lastNameText;
    private JTextArea workerNameText;
    private JTextArea patronumText;
    private JTextArea birthDayText;
    private JTextArea workerJobText;
    private JTextArea placeOfWorkText;
    private JTextArea roomNumberText;
    private JTextArea phoneNumberText;
    private JTextArea emailText;
    private JTextArea salaryText;
    private JTextArea firstDayOnJobText;
    private JTextArea notesText;
    private JTextField workerID;
    private JTextField lastName;
    private JTextField workerName;
    private JTextField patronum;
    private JTextField birthDay;
    private JTextField workerJob;
    private JTextField placeOfWork;
    private JTextField roomNumber;
    private JTextField phoneNumber;
    private JTextField email;
    private JTextField salary;
    private JTextField firstDayOnJob;
    private JTextField notes;
    private JTextArea Description;
    private JButton submitAddButton;
    //endregion

    Worker worker = new Worker();

    JTextArea textAreas[] = {workerIDText, workerNameText, lastNameText, patronumText, birthDayText, workerJobText, placeOfWorkText, roomNumberText, phoneNumberText, emailText, salaryText, firstDayOnJobText, notesText};
    JTextField textFields[] = {workerID, workerName,lastName,patronum,birthDay,workerJob,placeOfWork,roomNumber,phoneNumber, email, salary, firstDayOnJob, notes};

    private String question;
    private static JFrame frame = new JFrame("Application");


    public DataBaseGUI() {
        addWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Button to Add Worker to a Data Base
                question = "Do you really want to add Worker?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    //JOptionPane.showMessageDialog(null,emailText.getText());
                    VisibilityChange(mainPanel, addWorkerPanel);
                    submitAddButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            AddWorker();
                            JOptionPane.showMessageDialog(null, worker.GetMapKeyValue(emailText.getText()));
                        }
                    });


                }
            }
        });
        viewWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Button to Outputs specified worker's data
                question = "Do you really want to view Worker's info?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(null,"Do something");
                    ViewWorker();
                }
            }
        });
        editWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Button to Edit specified worker's data
                question = "Do you really want to edit Worker's info?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(null,"Do something");
                    EditWorker();
                }
            }
        });

        deleteWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Button to Delete specified worker from the Data Base
                question = "Do you really want to delete Worker?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(null,"Do something");
                    DeleteWorker();
                }
            }
        });
        printReportButton.addActionListener(new ActionListener() {          //Button to Prints report in table format(maybe will add PDF option)
            @Override
            public void actionPerformed(ActionEvent e) {
                question = "Do you really want to print a Report?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(null,"Do something");
                    PrintReport();
                }
            }
        });

    }

    private void AddWorker()
    {

        for(int i = 0; i<13; i++){
            worker.SetMapKeyValue(textAreas[i].getText(), textFields[i].getText());
        }


        //Use textArea.getText() for index creation 'cause it returns at least some value that looks like a key
        //Add text fields to list or array
        //Set values using a loop
        /*
        SetAllNewValues(ListOfTextFields)
        {
            for(int i = 0; i<ListOfTextFields.Length && i < HashTable.Lenght; i++)
            {
                SetValue(i(like index of a HashTable),ListOfTextFields[i].getText());
            }
        }
         */

    }

    private void ViewWorker()
    {

    }

    private void EditWorker()
    {

    }

    private void DeleteWorker()
    {

    }

    private void PrintReport()
    {

    }

    private void VisibilityChange(JPanel oldPanel, JPanel newPanel)
    {
        oldPanel.setVisible(false);
        newPanel.setVisible(true);
        frame.setSize(newPanel.getPreferredSize());

    }


    public static void main(String[] args) {
        frame.setContentPane(new DataBaseGUI().parentPanel);
        frame.setLocation(500,50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

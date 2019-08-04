package com.arsen.desktop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    //JTextArea textAreas[] = {workerIDText, workerNameText, lastNameText, patronumText, birthDayText, workerJobText, placeOfWorkText, roomNumberText, phoneNumberText, emailText, salaryText, firstDayOnJobText, notesText};
   // JTextField textFields[] = {workerID, workerName,lastName,patronum,birthDay,workerJob,placeOfWork,roomNumber,phoneNumber, email, salary, firstDayOnJob, notes};

    private String operationsWorkerID;
    private String question;
    private static JFrame frame = new JFrame("Application");


    public DataBaseGUI() {
        addWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Button to Add Worker to a Data Base
                question = "Do you really want to add Worker?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    OpenAddWorkerForm();

                }
            }
        });
        viewWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Button to Outputs specified worker's data
                question = "Do you really want to view Worker's info?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    OpenViewWorkerFormDialog();
                }
            }
        });
        editWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Button to Edit specified worker's data
                question = "Do you really want to edit Worker's info?";
                int dialogResult = JOptionPane.showConfirmDialog(null, question, "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    OpenEditWorkerForm();
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
                    DeleteWorkerDialog();
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

    private void OpenAddWorkerForm()
    {
        ChangeVisibility(mainPanel, AddWorkerForm.instance.GetPanel());
        frame.setContentPane(AddWorkerForm.instance.GetPanel());

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

    private void OpenViewWorkerFormDialog()
    {
        operationsWorkerID = JOptionPane.showInputDialog(null,"Input ID of the worker you want to view");

        if(operationsWorkerID != null)
        {
            //Check if there is such ID in database
            OpenViewWorkerForm(operationsWorkerID);
        }


    }

    private void OpenEditWorkerForm()
    {
        operationsWorkerID = JOptionPane.showInputDialog(null,"Input ID of the worker you want to edit");

        if(operationsWorkerID != null) {
            //Check if there is such an ID
            ChangeVisibility(mainPanel, EditWorkerForm.instance.GetPanel());
            frame.setContentPane(EditWorkerForm.instance.GetPanel());

            EditWorkerForm.instance.setTextFields(operationsWorkerID);
        }
    }

    private void DeleteWorkerDialog()
    {
        operationsWorkerID = JOptionPane.showInputDialog(null,"Which worker do you want to delete?");
        if(operationsWorkerID != null)
        {
            int askToViewWorker = JOptionPane.showConfirmDialog(null, "Do you want to view info about this worker before deleting?");
            if(askToViewWorker == JOptionPane.YES_OPTION)
            {
                OpenViewWorkerForm(operationsWorkerID);
                ViewWorkerForm.instance.setDeleteButtonVisible(true);
            }
            else if(askToViewWorker == JOptionPane.NO_OPTION){
                DeleteWorker(operationsWorkerID);
            }

        }

    }

    private void PrintReport()
    {

    }

    public static void ChangeVisibility(JPanel oldPanel, JPanel newPanel)
    {
        oldPanel.setVisible(false);
        newPanel.setVisible(true);
        frame.setSize(newPanel.getPreferredSize());
        //multiclass change
    }

    public void DeleteWorker(String deleteID){
        //Call SQL command to delete a row with operationsWorkerID
    }

    private void OpenViewWorkerForm(String viewID){
        ViewWorkerForm.instance.setDeleteButtonVisible(false);
        ViewWorkerForm.instance.setTextLabels(viewID);

        ChangeVisibility(mainPanel, ViewWorkerForm.instance.GetPanel());
        frame.setContentPane(ViewWorkerForm.instance.GetPanel());
    }


    public static JFrame GetFrame(){
        return frame;
    }


    public JPanel GetMainPanel(){
        return mainPanel;
    }

    public String getOperationsWorkerID(){
        return operationsWorkerID;
    }



    public static void main(String[] args) {
        frame.setContentPane(instance.parentPanel);
        frame.setLocation(500,50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

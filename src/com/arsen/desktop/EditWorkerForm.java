package com.arsen.desktop;

import com.arsen.desktop.validators.IDValidator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditWorkerForm {

    //Singleton
    public static EditWorkerForm instance = new EditWorkerForm();

    //region Buttons and fields
    private JPanel editWorkerPanel;
    private JTextArea lastNameText;
    private JTextArea workerNameText;
    private JTextArea patronumText;
    private JTextArea birthDayText;
    private JTextArea workerJobText;
    private JTextArea roomNumberText;
    private JTextArea phoneNumberText;
    private JTextArea emailText;
    private JTextArea salaryText;
    private JTextArea workingSinceText;
    private JTextArea notesText;
    private JTextArea placeOfWorkText;
    private JButton editButton;
    private JButton backButton;
    private JPanel parentPanel;
    private JTextArea Description;
    private JFormattedTextField workerID;
    private JFormattedTextField lastName;
    private JFormattedTextField name;
    private JFormattedTextField patronum;
    private JFormattedTextField dateOfBirth;
    private JFormattedTextField job;
    private JFormattedTextField placeOfWork;
    private JFormattedTextField roomNumber;
    private JFormattedTextField phone;
    private JFormattedTextField email;
    private JFormattedTextField salary;
    private JFormattedTextField workingSince;
    private JFormattedTextField notes;
    //endregion

    private static String operationsWorkerID;

    private JFormattedTextField[] formattedTextFields = {lastName,name, patronum, dateOfBirth,
            job,placeOfWork,roomNumber,phone, email, salary, workingSince, notes};


    public EditWorkerForm(){


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                        //Back button
                Main.changeVisiblePanel(editWorkerPanel, Table.instance.getParentPanel());
                Main.getFrame().setContentPane(Table.instance.getParentPanel());
                for(int i = 0; i< formattedTextFields.length; i++)
                {
                    formattedTextFields[i].setText("");
                }

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Alter workers info in data base
                //Take info from textFields, ID from inputID
                DataBaseManager.editRowInTheTable(formattedTextFields,getOperationsWorkerID());
                new AllTableModel().refreshTableData(TableFiller.getColumnDataDB());
            }
        });

        dateOfBirth.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                AutoDash.putDash(dateOfBirth);
            }

            public void removeUpdate(DocumentEvent e) {
                AutoDash.putDash(dateOfBirth);
            }

            public void insertUpdate(DocumentEvent e) {
                AutoDash.putDash(dateOfBirth);
            }

        });

        workingSince.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                AutoDash.putDash(workingSince);
            }

            public void removeUpdate(DocumentEvent e) {
                AutoDash.putDash(workingSince);
            }

            public void insertUpdate(DocumentEvent e) {
                AutoDash.putDash(workingSince);
            }

        });
    }

    private JPanel getParentPanel(){
        return parentPanel;
    }

    private JFormattedTextField[] getFormattedFields(){
        return formattedTextFields;
    }

    private String getOperationsWorkerID(){return operationsWorkerID;}

    public static void openEditWorkerFormDialog()
    {
         operationsWorkerID = JOptionPane.showInputDialog(null,"Input ID of the worker you want to edit");

        if(operationsWorkerID != null) {
            if(IDValidator.checkIfIDExists(operationsWorkerID))
            {

                openEditWorkerForm(operationsWorkerID);
            }else
            {
                while (!IDValidator.checkIfIDExists(operationsWorkerID)) {
                    //Check if there is such ID in database
                    operationsWorkerID = JOptionPane.showInputDialog(null, "There is no worker with such an ID, please try another one");
                    if(operationsWorkerID == null)
                        return;
                }


                openEditWorkerForm(operationsWorkerID);

            }

        }
    }

    private static void openEditWorkerForm(String operationsWorkerID)
    {

        CustomMaskFormatter.setMaskFormatters(instance.getFormattedFields());

        DataBaseManager.setFieldValuesFromDB(operationsWorkerID,instance.getFormattedFields());

        Main.changeVisiblePanel(Table.instance.getParentPanel(), instance.getParentPanel());
        Main.getFrame().setContentPane(instance.getParentPanel());
    }
}

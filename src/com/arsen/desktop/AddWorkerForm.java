package com.arsen.desktop;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.lang.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddWorkerForm {

    //Singleton
    public static AddWorkerForm instance = new AddWorkerForm();

    //region Fields
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
    private JTextArea workingSinceText;
    private JTextArea notesText;

    private JButton submitAddButton;
    private JButton backButton;
    private JPanel parentPanel;
    private JPanel addWorkerPanel;
    private JTextArea Description;
    private JFormattedTextField name;
    private JFormattedTextField dateOfBirth;
    private JFormattedTextField job;
    private JFormattedTextField phone;
    private JFormattedTextField lastName;
    private JFormattedTextField patronum;
    private JFormattedTextField placeOfWork;
    private JFormattedTextField roomNumber;
    private JFormattedTextField email;
    private JFormattedTextField salary;
    private JFormattedTextField workingSince;
    private JFormattedTextField notes;
    //endregion

    private JFormattedTextField[] formattedTextFields = {lastName, name, patronum, dateOfBirth,
            job,placeOfWork,roomNumber,phone, email, salary, workingSince, notes};

    private AddWorkerForm(){

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                        //Back button
                Table.changeVisiblePanel(addWorkerPanel, Table.instance.getParentPanel());
                Table.getFrame().setContentPane(Table.instance.getParentPanel());

            }
        });
        submitAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {                        //Button to add worker
                //SQL command to add a new row to the table
                DataBaseManager.addRowToTable(formattedTextFields);
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

    private JPanel getParentPanel()
    {
        return parentPanel;
    }

    private JFormattedTextField[] getFormattedTextFields(){
        return formattedTextFields;
    }

    public static void openAddWorkerForm()
    {
        CustomMaskFormatter.setMaskFormatters(instance.getFormattedTextFields());

        Table.changeVisiblePanel(Table.instance.getParentPanel(), instance.getParentPanel());
        Table.getFrame().setContentPane(AddWorkerForm.instance.getParentPanel());

    }

}
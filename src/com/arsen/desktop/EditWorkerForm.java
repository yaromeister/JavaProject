package com.arsen.desktop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditWorkerForm {

    //Singleton
    public static EditWorkerForm instance = new EditWorkerForm();

    //region Buttons and fields
    private JPanel editWorkerPanel;
    private JTextArea workerIDText;
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

    private JFormattedTextField[] formattedTextFieldsFields = {workerID, lastName,name, patronum, dateOfBirth,
            job,placeOfWork,roomNumber,phone, email, salary, workingSince, notes};


    public EditWorkerForm(){

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                        //Back button
                DataBaseGUI.changeVisiblePanel(editWorkerPanel, DataBaseGUI.instance.getMainPanel());
                DataBaseGUI.getFrame().setContentPane(DataBaseGUI.instance.getMainPanel());
                for(int i =0; i<formattedTextFieldsFields.length; i++)
                {
                    formattedTextFieldsFields[i].setText("");
                }

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Alter workers info in data base
                //Take info from textFields, ID from inputID
                DataBaseManager.editRowInTheTable(formattedTextFieldsFields,DataBaseGUI.instance.getOperationsWorkerID());
            }
        });
    }

    public JPanel getPanel(){
        return editWorkerPanel;
    }

    public JFormattedTextField[] getFormattedFields(){
        return formattedTextFieldsFields;
    }


}

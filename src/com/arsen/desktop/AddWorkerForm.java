package com.arsen.desktop;

import javax.swing.*;
import java.lang.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddWorkerForm {

    //Singleton
    public static AddWorkerForm instance = new AddWorkerForm();

    //region Buttons and fields
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
    private JTextArea workingSinceText;
    private JTextArea notesText;
    /*
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
    private JTextField workingSincess;
    private JTextField notes;

     */
    private JButton submitAddButton;
    private JButton backButton;
    private JPanel parentPanel;
    private JTextArea Description;
    private JFormattedTextField formattedTextField;
    private JFormattedTextField name;
    private JFormattedTextField dateOfBirth;
    private JFormattedTextField job;
    private JFormattedTextField phone;
    private JFormattedTextField workerID;
    private JFormattedTextField lastName;
    private JFormattedTextField patronum;
    private JFormattedTextField placeOfWork;
    private JFormattedTextField roomNumber;
    private JFormattedTextField email;
    private JFormattedTextField salary;
    private JFormattedTextField workingSince;
    private JFormattedTextField notes;
    //private JFormattedTextField formattedTextField = new JFormattedTextField(maskFormatter("####"))
    //endregion

    private JPanel addWorkerPanel;

    private JFormattedTextField[] formattedTextFields = {workerID, lastName, name, patronum, dateOfBirth,
            job,placeOfWork,roomNumber,phone, email, salary, workingSince, notes};


    public AddWorkerForm(){

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                        //Back button
                DataBaseGUI.changeVisiblePanel(addWorkerPanel, DataBaseGUI.instance.getMainPanel());
                DataBaseGUI.getFrame().setContentPane(DataBaseGUI.instance.getMainPanel());

            }
        });
        submitAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {                        //Button to add worker
                //SQL command to add a new row to the table
                DataBaseManager.addRowToTable(formattedTextFields);
            }
        });
    }

    public JPanel getPanel()
    {
        return addWorkerPanel;
    }

    public JFormattedTextField[] getFormattedTextFields(){
        return formattedTextFields;
    }

}
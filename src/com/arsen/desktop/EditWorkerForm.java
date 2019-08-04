package com.arsen.desktop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditWorkerForm {

    //Singleton
    public static EditWorkerForm instance = new EditWorkerForm();

    private JPanel editWorkerPanel;
    private JTextArea workerIDText;
    private JTextField workerID;
    private JTextField lastName;
    private JTextArea lastNameText;
    private JTextField workerName;
    private JTextArea workerNameText;
    private JTextField patronum;
    private JTextArea patronumText;
    private JTextField birthDay;
    private JTextArea birthDayText;
    private JTextField workerJob;
    private JTextArea workerJobText;
    private JTextField placeOfWork;
    private JTextField roomNumber;
    private JTextArea roomNumberText;
    private JTextField phoneNumber;
    private JTextArea phoneNumberText;
    private JTextField email;
    private JTextArea emailText;
    private JTextField salary;
    private JTextArea salaryText;
    private JTextField workingSince;
    private JTextArea workingSinceText;
    private JTextField notes;
    private JTextArea notesText;
    private JTextArea placeOfWorkText;
    private JButton editButton;
    private JTextArea Description;
    private JButton backButton;

    private JTextArea[] textAreas = {workerIDText, lastNameText, workerNameText, patronumText, birthDayText, workerJobText, placeOfWorkText, roomNumberText, phoneNumberText, emailText, salaryText, workingSinceText, notesText};
    private JTextField[] textFields = {workerID, lastName,workerName, patronum,birthDay,workerJob,placeOfWork,roomNumber,phoneNumber, email, salary, workingSince, notes};


    public EditWorkerForm(){
        textFields[7].setText("Some Text");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                        //Back button
                //DataBaseGUI mainMenu = new DataBaseGUI();
                DataBaseGUI.instance.ChangeVisibility(editWorkerPanel, DataBaseGUI.instance.GetMainPanel());
                DataBaseGUI.instance.GetFrame().setContentPane(DataBaseGUI.instance.GetMainPanel());
                for(int i =0; i<textFields.length; i++)
                {
                    textFields[i].setText("");
                }
                //JOptionPane.showMessageDialog(null, textFields[9].getText());

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Alter workers info in data base
                //Take info from textFields, ID from inputID
            }
        });
    }

    public JPanel GetPanel(){
        return editWorkerPanel;
    }

    public void setTextFields(String dataBaseKey)
    {
        //SQL command for retrieving data
    }
}

package com.arsen.desktop;

import javax.swing.*;
import java.lang.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddWorkerForm {

    public static AddWorkerForm instance = new AddWorkerForm();



    //region Buttons and fields
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
    private JTextArea workingSinceText;
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
    private JTextField workingSince;
    private JTextField notes;
    private JTextArea Description;
    private JButton submitAddButton;
    private JButton backButton;
    //endregion


    private JTextArea[] textAreas = {workerIDText, lastNameText, workerNameText, patronumText, birthDayText, workerJobText, placeOfWorkText, roomNumberText, phoneNumberText, emailText, salaryText, workingSinceText, notesText};
    private JTextField[] textFields = {workerID, lastName,workerName, patronum,birthDay,workerJob,placeOfWork,roomNumber,phoneNumber, email, salary, workingSince, notes};


    public AddWorkerForm(){

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                        //Back button
                //DataBaseGUI mainMenu = new DataBaseGUI();
                DataBaseGUI.instance.ChangeVisibility(addWorkerPanel, DataBaseGUI.instance.GetMainPanel());
                DataBaseGUI.instance.GetFrame().setContentPane(DataBaseGUI.instance.GetMainPanel());

                for(int i =0; i<textFields.length; i++)
                {
                    textFields[i].setText("");
                }
                //JOptionPane.showMessageDialog(null, textFields[9].getText());

            }
        });
        submitAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {                        //Button to add worker
                //SQL command to add a new row to the table
                DataBaseManager.addRowToTable(textFields);
                //ViewWorkerForm.instance.GetLabel().setText(textFields[0].getText());


            }
        });
    }

    private void setPreparedStatement(){

    }

    public JPanel GetPanel()
    {
        return addWorkerPanel;
    }

    public JTextField getTextFieldByIndex(int index){
        return textFields[index];
    }

}

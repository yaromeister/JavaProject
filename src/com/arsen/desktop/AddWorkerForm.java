package com.arsen.desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton button1;
    //endregion


    private JTextArea[] textAreas = {workerIDText, workerNameText, lastNameText, patronumText, birthDayText, workerJobText, placeOfWorkText, roomNumberText, phoneNumberText, emailText, salaryText, firstDayOnJobText, notesText};
    private JTextField[] textFields = {workerID, workerName,lastName,patronum,birthDay,workerJob,placeOfWork,roomNumber,phoneNumber, email, salary, firstDayOnJob, notes};


    public AddWorkerForm(){

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //DataBaseGUI mainMenu = new DataBaseGUI();
                DataBaseGUI.instance.VisibilityChange(addWorkerPanel, DataBaseGUI.instance.GetMainPanel());
                DataBaseGUI.instance.GetFrame().setContentPane(DataBaseGUI.instance.GetMainPanel());
                for(int i =0; i<textFields.length; i++)
                {
                    textFields[i].setText("");
                }
                //JOptionPane.showMessageDialog(null, textFields[9].getText());

                /*
                DataBaseGUI.VisibilityChange(addWorkerPanel, DataBaseGUI.GetMainPanel());
                DataBaseGUI.GetFrame().setContentPane(DataBaseGUI.GetMainPanel());

                 */
            }
        });
    }

    public JPanel GetPanel(){
        return addWorkerPanel;
    }

}

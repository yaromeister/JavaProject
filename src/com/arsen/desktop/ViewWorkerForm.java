package com.arsen.desktop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewWorkerForm {

    public static ViewWorkerForm insctance = new ViewWorkerForm();

    private JPanel viewWorkerPanel;
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
    private JTextArea firstDayOnJobText;
    private JTextArea notesText;
    private JTextArea placeOfWorkText;
    private JTextArea Description;
    private JButton backButton;
    private JLabel workerIDLabel;
    private JLabel lastNameLabel;
    private JLabel nameLabel;
    private JLabel patronumLabel;
    private JLabel dateOfBirthLabel;
    private JLabel jobLabel;
    private JLabel placeOfWorkLabel;
    private JLabel roomNumberLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel salartLabel;
    private JLabel workigngSinceLabel;
    private JLabel notesLabel;

    public ViewWorkerForm(){
        Description.setText(AddWorkerForm.instance.getTextFieldByIndex(1).getText() + " " + AddWorkerForm.instance.getTextFieldByIndex(2).getText() + " info");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                        //Back button
                DataBaseGUI.instance.ChangeVisibility(viewWorkerPanel, DataBaseGUI.instance.GetMainPanel());
                DataBaseGUI.instance.GetFrame().setContentPane(DataBaseGUI.instance.GetMainPanel());

            }
        });

    }

    public JLabel GetLabel(){
        return workerIDLabel;
    }

    public JPanel GetPanel(){
        return viewWorkerPanel;
    }
}



package com.arsen.desktop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewWorkerForm{

    public static ViewWorkerForm instance = new ViewWorkerForm();

    private JPanel viewWorkerPanel;

    //region Button And Fields
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
    private JLabel salaryLabel;
    private JLabel workingSinceLabel;
    private JLabel notesLabel;
    private JButton deleteButton;
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
    //endregion


    private JLabel[] labels = {workerIDLabel, lastNameLabel, nameLabel,
            patronumLabel, dateOfBirthLabel, jobLabel, placeOfWorkLabel,
            roomNumberLabel, phoneLabel, emailLabel, salaryLabel, workingSinceLabel, notesLabel};

    public ViewWorkerForm(){
        //Description.setText(AddWorkerForm.instance.getTextFieldByIndex(1).getText() + " " + AddWorkerForm.instance.getTextFieldByIndex(2).getText() + " info");


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                        //Back button
                DataBaseGUI.instance.ChangeVisibility(viewWorkerPanel, DataBaseGUI.instance.GetMainPanel());
                DataBaseGUI.instance.GetFrame().setContentPane(DataBaseGUI.instance.GetMainPanel());


            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Gocha, mate(:");
                DataBaseGUI.instance.DeleteWorker(DataBaseGUI.instance.getOperationsWorkerID());
            }
        });
    }

    public JLabel[] GetLabelArray(){
        return labels;
    }

    public JPanel GetPanel(){
        return viewWorkerPanel;
    }

    public void setDeleteButtonVisible(Boolean bool){
        deleteButton.setVisible(bool);
    }

    public void setTextLabels(String dataBaseKey){
        //SQL command for retrieving data
    }

    public void setDescription(String name, String lastName){
        Description.setText(name +" " + lastName + " info");
    }
}



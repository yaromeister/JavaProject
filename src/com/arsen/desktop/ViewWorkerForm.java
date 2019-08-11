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

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                        //Back button
                DataBaseGUI.changeVisiblePanel(viewWorkerPanel, DataBaseGUI.instance.getMainPanel());
                DataBaseGUI.getFrame().setContentPane(DataBaseGUI.instance.getMainPanel());


            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Gocha, mate(:");
                DataBaseManager.deleteRowFromTheTable(DataBaseGUI.instance.getOperationsWorkerID());
            }
        });
    }

    public JLabel[] getLabelArray(){
        return labels;
    }

    public JPanel getPanel(){
        return viewWorkerPanel;
    }

    public void setDeleteButtonVisible(Boolean bool){
        deleteButton.setVisible(bool);
    }


    public void setDescription(String name, String lastName){
        Description.setText(name + " " + lastName + " info");
    }
}



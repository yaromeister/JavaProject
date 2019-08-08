package com.arsen.desktop;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.lang.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddWorkerForm {

    public static AddWorkerForm instance = new AddWorkerForm();



    private JPanel addWorkerPanel;
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


    private JTextArea[] textAreas = {workerIDText, lastNameText, workerNameText, patronumText, birthDayText, workerJobText, placeOfWorkText, roomNumberText, phoneNumberText, emailText, salaryText, workingSinceText, notesText};
    private JFormattedTextField[] formattedTextFields = {workerID, lastName, name, patronum, dateOfBirth,
            job,placeOfWork,roomNumber,phone, email, salary, workingSince,
            notes};


    public AddWorkerForm(){

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                        //Back button
                //DataBaseGUI mainMenu = new DataBaseGUI();
                DataBaseGUI.instance.ChangeVisibility(addWorkerPanel, DataBaseGUI.instance.GetMainPanel());
                DataBaseGUI.instance.GetFrame().setContentPane(DataBaseGUI.instance.GetMainPanel());
                /*
                for(int i =0; i<textFields.length; i++)
                {
                    textFields[i].setText("");
                }

                 */
                //JOptionPane.showMessageDialog(null, textFields[9].getText());

            }
        });
        submitAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {                        //Button to add worker
                //SQL command to add a new row to the table
                DataBaseManager.addRowToTable(formattedTextFields);
                //ViewWorkerForm.instance.GetLabel().setText(textFields[0].getText());


            }
        });
    }

    public JPanel GetPanel()
    {
        return addWorkerPanel;
    }

    public JFormattedTextField getTextFieldByIndex(int index){
        return formattedTextFields[index];
    }



    private MaskFormatter maskFormatter(String s){
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
            //formatter.setPlaceholderCharacter('*');
            //formatter.setValidCharacters("012345");
            //formatter.setPlaceholderCharacter('_');
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }


    public void SetMaskFormatters(JFormattedTextField[] formattedTextFields) throws SQLException {

        ResultSet resultSet = DataBaseManager.getColumnTypes();
        int i = 0;
            while (resultSet.next())
            {
                String type = resultSet.getString("TYPE_NAME");
                //System.out.println(type);


                switch(type){
                    case "INT":
                        formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(maskFormatter("####")));
                        formattedTextFields[i].setText("8888");
                        break;
                    case "VARCHAR":
                        MaskFormatter stringFormatter = maskFormatter("****************");
                        stringFormatter.setInvalidCharacters("123456789");
                        formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(stringFormatter));
                        formattedTextFields[i].setText("xxxxxxxxxxxxx");
                        break;

                    case"DATE":
                        formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(maskFormatter("####-##-##")));
                        formattedTextFields[i].setText("1111-11-11");
                        break;
                    case"DOUBLE":
                        formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(maskFormatter("##########")));
                        formattedTextFields[i].setText("8888");
                        break;
                    default:
                        System.out.println("Type error");
                }
                i++;
            }
    }

    public JFormattedTextField[] getFormattedTextFields(){
        return formattedTextFields;
    }


}

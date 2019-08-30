package com.arsen.desktop;

import com.arsen.desktop.exceptions.DateFormatException;
import com.arsen.desktop.exceptions.EmptyFieldException;
import com.arsen.desktop.validators.DateValidator;

import javax.swing.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatementFiller {

    // Day lenght in miliseconds, for fixing ResultSet.getDate() bug
    private static long day = 86300000;

    private static String dateFormat = "yyyy-MM-dd";


    public static void additionSwitch(JTextField[] textFields, ResultSet metaDataSet, PreparedStatement preparedStatement){
        try {
            int index = 0;
            metaDataSet.next();
            while (metaDataSet.next()) {
                if(textFields[index].getText().isBlank()){
                    throw new EmptyFieldException();
                }
                String type = metaDataSet.getString("TYPE_NAME");

                switch (type) {
                    case "INT":
                        preparedStatement.setInt(index + 1, Integer.parseInt(textFields[index].getText().replaceAll("\\s+", "")));
                        break;

                    case "VARCHAR":
                        if (metaDataSet.getString("COLUMN_NAME").toLowerCase().equals("Phone number".toLowerCase())) {
                            preparedStatement.setString(index + 1, textFields[index].getText().replaceAll("\\s+", ""));
                        }
                        preparedStatement.setString(index + 1, textFields[index].getText().trim());

                        break;

                    case "DATE":
                        String dateToCheck = textFields[index].getText();
                        if (DateValidator.isThisDateValid(dateToCheck, dateFormat)) {
                            preparedStatement.setDate(index + 1, Date.valueOf(textFields[index].getText()));
                        } else {
                            throw new DateFormatException("Entered date is wrong, use yyyy-MM-dd format");
                        }
                        break;

                    case "DOUBLE":
                        preparedStatement.setDouble(index + 1, Double.parseDouble(textFields[index].getText()));
                        break;
                    default:
                        System.out.println("Type error");
                }
                index++;
            }
        }catch (EmptyFieldException efe){
            efe.printStackTrace();
            JOptionPane.showMessageDialog(null,"Don't leave empty field");
        }
        catch(NumberFormatException ne){

        }catch (DateFormatException de){
            de.printStackTrace();
            JOptionPane.showMessageDialog(null, de.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setFieldValuesSwitch(ResultSet resultSet, JFormattedTextField[] formattedTextFields){
        try {
            for (int i = 1; i < 13; i++) {
                String columnType = resultSet.getMetaData().getColumnTypeName(i+1);

                switch (columnType) {
                    case "INT":
                        formattedTextFields[i-1].setText(String.valueOf(resultSet.getInt(i+1)));

                        break;
                    case "VARCHAR":
                        formattedTextFields[i-1].setText(resultSet.getString(i+1));
                        break;
                    case "DATE":
                        Date currentDate = resultSet.getDate(i+1);
                        currentDate.setTime(currentDate.getTime() + day);
                        formattedTextFields[i-1].setText(currentDate.toString());
                        break;
                    case "DOUBLE":
                        formattedTextFields[i-1].setText(String.valueOf(resultSet.getDouble(i+1)));
                        break;
                    default:
                        System.out.println("Type error");
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public static void editSwitch(JTextField[] textFields, ResultSet metaDataSet, PreparedStatement preparedStatement, String editID) {
        try {
            int index = 0;
            metaDataSet.next();
            while (metaDataSet.next()) {
                if(textFields[index].getText().isBlank()){
                    throw new EmptyFieldException();
                }
                String type = metaDataSet.getString("TYPE_NAME");

                switch (type) {
                    case "INT":
                        preparedStatement.setInt(index+1, Integer.parseInt(textFields[index].getText().replaceAll("\\s+", "")));
                        break;

                    case "VARCHAR":
                        if (metaDataSet.getString("COLUMN_NAME").toLowerCase().equals("Phone number".toLowerCase())) {
                            preparedStatement.setString(index+1, textFields[index].getText().replaceAll("\\s+", ""));
                        }
                        preparedStatement.setString(index+1, textFields[index].getText().trim());

                        break;

                    case "DATE":
                        String dateToCheck = textFields[index].getText();
                        if (DateValidator.isThisDateValid(dateToCheck, dateFormat)) {
                            preparedStatement.setDate(index+1, Date.valueOf(textFields[index].getText()));
                        } else {
                            throw new DateFormatException("Entered date is wrong, use yyyy-MM-dd format");
                        }
                        break;

                    case "DOUBLE":
                        preparedStatement.setDouble(index+1, Double.parseDouble(textFields[index].getText()));
                        break;
                    default:
                        System.out.println("Type error");
                }
                index++;
            }
            //sets id of a worker witch we will edit
            preparedStatement.setInt(index+1, Integer.parseInt(editID));
        }catch (EmptyFieldException efe){
            efe.printStackTrace();
            JOptionPane.showMessageDialog(null,"Don't leave empty field");
        }catch(NumberFormatException ne){

        }catch (DateFormatException de){
            de.printStackTrace();
            JOptionPane.showMessageDialog(null, de.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

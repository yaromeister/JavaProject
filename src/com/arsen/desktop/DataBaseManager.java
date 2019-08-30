package com.arsen.desktop;

import javax.swing.*;
import java.sql.*;


public class DataBaseManager {


    public static void addRowToTable(JTextField[] textFields)
    {
        Connection conn =  ConnectionManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet metaDataSet = null;
        try {
            metaDataSet = MetaDataManager.getMetaData();

            String sql = "INSERT INTO `WORKERS` VALUES(0,?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);

            //Chooses transformation of type according to column type in DB
            PreparedStatementFiller.additionSwitch(textFields,metaDataSet,preparedStatement);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Successfully added worker");

            refreshTable();

        } catch (NullPointerException | SQLException ex) {
            ex.printStackTrace();
        }finally {
            try{metaDataSet.close();
                preparedStatement.close();
                conn.close();}catch (NullPointerException | SQLException e){
                System.out.println("Unable to close");
            }
        }

    }

    public static void setFieldValuesFromDB(String rowID, JFormattedTextField[] formattedTextFields)
    {
        Connection conn =  ConnectionManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM WORKERS " +
                "WHERE ID = ?";

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,Integer.parseInt(rowID));
            resultSet = preparedStatement.executeQuery();
            //formattedTextFields[0].setEditable(false);

            if(resultSet.next())
            {
                //Dynamically reads column data type and transform data accordingly
                PreparedStatementFiller.setFieldValuesSwitch(resultSet, formattedTextFields);
            }
            } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }finally {
            try{resultSet.close();
                preparedStatement.close();
                conn.close();}catch (NullPointerException | SQLException e){
                System.out.println("Unable to close");}
        }
    }


    public static void editRowInTheTable(JFormattedTextField[] formattedTextFields, String editID)
    {
        Connection conn =  ConnectionManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet metaDataSet = null;
        try {
            metaDataSet = MetaDataManager.getMetaData();

            String sql = "UPDATE `WORKERS` " +
                    "SET `Last Name` = ?, `Name` = ?, `Patronum` = ?, `Date of birth` = ?, `Job` = ?, `Department` = ?, `Room number` = ?, `Phone number` = ?, `Email` = ?, `Salary` = ?, `Working since` = ?, `Notes` = ? WHERE ID = ?";
            preparedStatement = conn.prepareStatement(sql);

            //skips first value because it's ID which we don't edit


            //Dynamically reads column data type, transform and set data accordingly
            PreparedStatementFiller.editSwitch(formattedTextFields, metaDataSet, preparedStatement, editID);

            preparedStatement.executeUpdate();

            refreshTable();

            JOptionPane.showMessageDialog(null, "Successfully edited worker");
        } catch (NullPointerException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }finally {
            try{metaDataSet.close();
                preparedStatement.close();
                conn.close();}catch (NullPointerException | SQLException e){
                System.out.println("Unable to close");}
        }
    }


    public static void deleteRowFromTheTable(String rowsID)
    {
        Connection conn =  ConnectionManager.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM WORKERS " +
                     "WHERE ID = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,Integer.parseInt(rowsID));

            preparedStatement.executeUpdate();

            refreshTable();

            JOptionPane.showMessageDialog(null, "Successfully deleted worker");
        } catch (SQLException e) {
            System.out.println("Delete statement error!");
            e.printStackTrace();
        }finally {
            try{ preparedStatement.close();
                conn.close();}catch (NullPointerException | SQLException e){
                System.out.println("Unable to close");}
        }
    }


    private static void refreshTable(){
        AllTableModel model = (AllTableModel)Table.instance.getTable().getModel();
        model.refreshTableData(TableFiller.getColumnDataDB());
        model.fireTableDataChanged();
    }
}

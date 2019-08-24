package com.arsen.desktop;

import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.desktop.AppHiddenEvent;
import java.io.FileOutputStream;
import java.sql.*;


public class DataBaseManager {
    // JDBC driver name and database URL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/java_project";
    //private static final String DB_URL = "jdbc:mysql://eu-cdbr-west-02.cleardb.net/heroku_7bf74b485ff3cdb?reconnect=true";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "admin";
//    private static final String PASS = "a8546289";
//    private static final String USER = "b14b99260eee75";

    // Day lenght in miliseconds, for fixing ResultSet.getDate() bug
    private static long day = 86300000;

    private static String dateFormat = "yyyy-MM-dd";


    private static Connection getConnection(){
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unable to connect, please check your connection and try again");
            return null;
        }

    }

    private static ResultSet getColumnsMetaData() throws SQLException
    {
        Connection con = getConnection();
        DatabaseMetaData metaData = con.getMetaData();
        ResultSet resultSet = metaData.getColumns(null, null, "workers", null);

        return resultSet;
    }

    public static void addRowToTable(JTextField[] textFields)
    {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet metaDataSet = null;
        try {
            metaDataSet = getColumnsMetaData();

            String sql = "INSERT INTO `WORKERS` VALUES(0,?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);

            //Chooses transformation of type according to column type in DB
            additionSwitch(textFields,metaDataSet,preparedStatement);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Successfully added worker");

            refreshTable();

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try{metaDataSet.close();
                preparedStatement.close();
                conn.close();}catch (Exception e){};
        }

    }

            private static void additionSwitch(JTextField[] textFields, ResultSet metaDataSet, PreparedStatement preparedStatement){
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

    public static void setFieldValuesFromDB(String rowID, JLabel[] labels)
    {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM WORKERS " +
                "WHERE ID = ?";

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,Integer.parseInt(rowID));
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                //Dynamically reads column data type and transform data accordingly
                setFieldValuesSwitch(resultSet,labels);
            }

            ViewWorkerForm.instance.setDescription(labels[2].getText().replaceAll("\\s+",""), labels[1].getText().replaceAll("\\s+", ""));

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{resultSet.close();
                preparedStatement.close();
                conn.close();}catch (Exception e){};
        }

    }

            private static void setFieldValuesSwitch(ResultSet resultSet, JLabel[] labels){
        try {
            for (int i = 0; i < 13; i++) {
                String columnType = resultSet.getMetaData().getColumnTypeName(i + 1);

                switch (columnType) {
                    case "INT":
                        labels[i].setText(String.valueOf(resultSet.getInt(i + 1)));

                        break;
                    case "VARCHAR":
                        labels[i].setText(resultSet.getString(i + 1));
                        break;
                    case "DATE":
                        Date currentDate = resultSet.getDate(i + 1);
                        currentDate.setTime(currentDate.getTime() + day);
                        labels[i].setText(currentDate.toString());
                        break;
                    case "DOUBLE":
                        labels[i].setText(String.valueOf(resultSet.getDouble(i + 1)));
                        break;
                    default:
                        System.out.println("Type error");
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public static void setFieldValuesFromDB(String rowID, JFormattedTextField[] formattedTextFields)
    {
        Connection conn = getConnection();
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
                setFieldValuesSwitch(resultSet, formattedTextFields);
            }
            ViewWorkerForm.instance.setDescription(formattedTextFields[1].getText().replaceAll("\\s+",""), formattedTextFields[2].getText().replaceAll("\\s+", ""));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{resultSet.close();
                preparedStatement.close();
                conn.close();}catch (Exception e){};
        }
    }

            private static void setFieldValuesSwitch(ResultSet resultSet, JFormattedTextField[] formattedTextFields){
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

    public static void editRowInTheTable(JFormattedTextField[] formattedTextFields, String editID)
    {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet metaDataSet = null;
        try {
            metaDataSet = getColumnsMetaData();

            String sql = "UPDATE `WORKERS` " +
                    "SET `Last Name` = ?, `Name` = ?, `Patronum` = ?, `Date of birth` = ?, `Job` = ?, `Department` = ?, `Room number` = ?, `Phone number` = ?, `Email` = ?, `Salary` = ?, `Working since` = ?, `Notes` = ? WHERE ID = ?";
            preparedStatement = conn.prepareStatement(sql);

            //skips first value because it's ID which we don't edit
            metaDataSet.next();

            //Dynamically reads column data type, transform and set data accordingly
            editSwitch(formattedTextFields, metaDataSet, preparedStatement, editID);

            preparedStatement.executeUpdate();

            refreshTable();

            JOptionPane.showMessageDialog(null, "Successfully edited worker");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }finally {
            try{metaDataSet.close();
                preparedStatement.close();
                conn.close();}catch (Exception e){};
        }
    }

            private static void editSwitch(JTextField[] textFields, ResultSet metaDataSet, PreparedStatement preparedStatement, String editID) {
        try {
            int index = 0;
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

    public static void deleteRowFromTheTable(String rowsID)
    {
        Connection conn = getConnection();
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
                conn.close();}catch (Exception e){};
        }
    }

    //Used to set restriction on input

    public static void setMaskFormatters(JFormattedTextField[] formattedTextFields)
    {
        //Formatter Factory is the only way to set formatters after creation of JFormattedTextField(they were created automatically)
        ResultSet metaDataSet = null;
        try {
            metaDataSet = getColumnsMetaData();

            int i = 0;
            //Dynamically reads column data type and set formatters accordingly
            metaDataSet.next(); //Skip ID
            while (metaDataSet.next())
            {
                String type = metaDataSet.getString("TYPE_NAME");
                int columnSize = Integer.parseInt(metaDataSet.getString("COLUMN_SIZE"));
                String maskFormat;

                switch(type){
                    case "INT":
                        maskFormat = "#";
                        formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(CustomMaskFormatter.createMaskFormatter(maskFormat.repeat(columnSize))));
                        formattedTextFields[i].setUI(new HintTextFieldUI("123456789", true, Color.LIGHT_GRAY));

                        break;
                    case "VARCHAR":
                        //phone number is varchar in DB but only numbers should be inputed
                        if(metaDataSet.getString("COLUMN_NAME").toLowerCase().equals("Phone number".toLowerCase()))
                        {
                            maskFormat = "#";
                            formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(CustomMaskFormatter.createMaskFormatter(maskFormat.repeat(columnSize))));
                            formattedTextFields[i].setUI(new HintTextFieldUI("012345678", true, Color.LIGHT_GRAY));

                        }
                        else {
                            maskFormat = "*";
                            formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(CustomMaskFormatter.createMaskFormatter(maskFormat.repeat(columnSize))));
                            formattedTextFields[i].setText(null);
                            formattedTextFields[i].setUI(new HintTextFieldUI("Some text", true, Color.LIGHT_GRAY));
                        }

                        break;

                    case"DATE":
                        formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(CustomMaskFormatter.createMaskFormatter("####*##*##")));
                        //formattedTextFields[i].setText("1111-11-11");
                        formattedTextFields[i].setUI(new HintTextFieldUI("yyyy-MM-dd", true, Color.LIGHT_GRAY));

                        break;
                    case"DOUBLE":
                        //to input '.' symbol you have to include all symbols and than allow only numbers and dot
                        maskFormat = "*";
                        MaskFormatter doubleFormatter = CustomMaskFormatter.createMaskFormatter(maskFormat.repeat(columnSize));
                        doubleFormatter.setValidCharacters("0123456789.");
                        formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(doubleFormatter));
                        formattedTextFields[i].setUI(new HintTextFieldUI("1234.5678", true, Color.LIGHT_GRAY));

                        break;
                    default:
                        System.out.println("Type error");
                }
                i++;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally{
            try{metaDataSet.close();}catch (Exception e){};
        }
    }

    public static void printPDF(String filePath)
    {
//        Connection conn = getConnection();
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
        try {
            String fileName = filePath + "\\Report.pdf";

//            String sql = "SELECT * FROM WORKERS ORDER BY ID";
//            preparedStatement = conn.prepareStatement(sql);
//            resultSet = preparedStatement.executeQuery();

            Document document = new Document(PageSize.A4.rotate());

            PdfWriter.getInstance(document, new FileOutputStream(fileName));
           // ResultSet metaData = getColumnsMetaData();

            document.open();


            PdfPTable table = new PdfPTable(13);

            table.setHeaderRows(1);
            table.setWidthPercentage(105);

            Font cellFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);

            //adds header
//            while(metaData.next())
//            {
            for(int columnIndex =0; columnIndex<Table.instance.getTable().getColumnCount(); columnIndex++ ){
                table.addCell(new PdfPCell(new Phrase(Table.instance.getTable().getColumnName(columnIndex), headerFont)));
                //table.addCell(new PdfPCell(new Phrase(metaData.getString("COLUMN_NAME"), headerFont)));

            }
                // }


            //adds cells
            for(int row = 0; row<Table.instance.getTable().getRowCount(); row++){
                for(int col = 0; col<Table.instance.getTable().getColumnCount(); col++){
                    PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(Table.instance.getTable().getValueAt(row,col)),cellFont));
                    table.addCell(cell).setMinimumHeight(40);
                    //System.out.println(Table.instance.getTable().getValueAt(row,));
                }
            }
            /*
            while(resultSet.next()){
                for(int i = 0; i<13; i++){
                    PdfPCell cell = new PdfPCell(new Phrase(resultSet.getString(i+1),cellFont));
                    table.addCell(cell).setMinimumHeight(40);
                }
            }
        */
            document.add(table);


            document.close();
//            try{resultSet.close();
//                preparedStatement.close();
//                conn.close();}catch (Exception e){};
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();

       }
//        finally {
//            try{resultSet.close();
//                preparedStatement.close();
//                conn.close();}catch (Exception e){};
//        }

    }

    public static boolean checkIfIDExists(String idToCheck)
    {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {

        String sql = "SELECT `ID` FROM `WORKERS`";
        preparedStatement = conn.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();

        if(idToCheck == null){
            return false;
        }
        //Checks if entered value is the same as any value in ID column(first column)
        while(resultSet.next()){
            if(resultSet.getInt(1) == Integer.parseInt(idToCheck)){
                System.out.println("ID found");
                return true;
            }
        }
            System.out.println("ID check finished");
            try{resultSet.close();
                preparedStatement.close();
                conn.close();}catch (Exception e){};
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{resultSet.close();
                preparedStatement.close();
                conn.close();}catch (Exception e){};
        }
        return false;
    }

    public static String[] getColumnNamesDB(){
        Connection conn = getConnection();
        ResultSet metaData = null;
        String[] columnNames = new String[13];
        try{
            metaData = getColumnsMetaData();
            int index = 0;
            while (metaData.next()){
                columnNames[index] = metaData.getString("COLUMN_NAME");
                index++;
            }
            return columnNames;

        }catch (Exception ex){
            try{metaData.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            ex.printStackTrace();
            return null;
        }

    }

    public static Object[][] getColumnDataDB(){
        Connection conn = getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            String sql = "SELECT * FROM WORKERS ORDER BY ID";
            preparedStatement = conn.prepareStatement(sql);

            ResultSet numberOfRowsSet = preparedStatement.executeQuery();
            int numberOfRows =0;
            while (numberOfRowsSet.next()){
                numberOfRows++;
            }
            Object[][] data = new Object[numberOfRows][13];

            resultSet = preparedStatement.executeQuery();
            int index = 0;
            while(resultSet.next()){
                for(int i = 0; i<13; i++){
                    data[index][i] = resultSet.getString(i+1);
                }
                index++;
            }
            System.out.println("Data changed");
            return data;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static void refreshTable(){
        AllTableModel model = (AllTableModel)Table.instance.getTable().getModel();
        model.refreshTableData(getColumnDataDB());
        model.fireTableDataChanged();
    }
}

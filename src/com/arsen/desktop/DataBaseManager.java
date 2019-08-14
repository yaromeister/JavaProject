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
import java.io.FileOutputStream;
import java.sql.*;


public class DataBaseManager {
    // JDBC driver name and database URL
    //private static final String DB_URL = "jdbc:mysql://localhost:3306/java_project";
    private static final String DB_URL = "jdbc:mysql://eu-cdbr-west-02.cleardb.net/heroku_7bf74b485ff3cdb?reconnect=true";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    //  Database credentials
    //private static final String USER = "root";
    //private static final String PASS = "admin";
    private static final String PASS = "a8546289";
    private static final String USER = "b14b99260eee75";

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

            String sql = "INSERT INTO `WORKERS` VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);

            String id = textFields[0].getText().replaceAll("\\s+","");

            if(checkIfIDExists(id)) {
                JOptionPane.showMessageDialog(null,
                                              "Worker with this ID already exists");
                return;
            }

            int i = 0;
            //Chooses transformation of type according to column type in DB
            while (metaDataSet.next()){
                String type = metaDataSet.getString("TYPE_NAME");

                switch(type){
                    case "INT":
                            preparedStatement.setInt(i + 1, Integer.parseInt(textFields[i].getText().replaceAll("\\s+", "")));
                    break;

                    case "VARCHAR":
                        if(metaDataSet.getString("COLUMN_NAME").toLowerCase().equals("Phone number".toLowerCase())) {
                            preparedStatement.setString(i + 1, textFields[i].getText().replaceAll("\\s+", ""));
                        }
                        preparedStatement.setString(i+1, textFields[i].getText().trim());

                    break;

                     case"DATE":
                         String dateToCheck = textFields[i].getText();
                         if(DateValidator.isThisDateValid(dateToCheck,dateFormat))
                         {
                             preparedStatement.setDate(i + 1, Date.valueOf(textFields[i].getText()));
                         }else{
                             throw new Exception("Entered date is wrong, use yyyy-MM-dd format");
                         }
                     break;

                    case"DOUBLE":
                        preparedStatement.setDouble(i+1, Double.parseDouble(textFields[i].getText()));
                    break;
                    default:
                    System.out.println("Type error");
                }
                i++;
            }

            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Successfully added worker");

        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(null, ex.getMessage());
        }finally {
            try{metaDataSet.close();
                preparedStatement.close();
                conn.close();}catch (Exception e){};
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
                for(int i =0; i<13; i++)
                {
                    String columnType = resultSet.getMetaData().getColumnTypeName(i + 1);

                    switch (columnType)
                    {
                        case "INT":
                            labels[i].setText(String.valueOf(resultSet.getInt(i + 1)));

                            break;
                        case "VARCHAR":
                            labels[i].setText(resultSet.getString(i + 1));
                            break;
                        case "DATE":
                            Date currentDate = resultSet.getDate(i + 1);
                            currentDate.setTime(currentDate.getTime()+day);
                            labels[i].setText(currentDate.toString());
                            break;
                        case "DOUBLE":
                            labels[i].setText(String.valueOf(resultSet.getDouble(i + 1)));
                            break;
                        default:
                            System.out.println("Type error");
                    }
                }
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
            formattedTextFields[0].setEditable(false);

            if(resultSet.next())
            {
                //Dynamically reads column data type and transform data accordingly
                for(int i =0; i<13; i++)
                {
                    String columnType = resultSet.getMetaData().getColumnTypeName(i + 1);

                    switch (columnType)
                    {
                        case "INT":
                            formattedTextFields[i].setText(String.valueOf(resultSet.getInt(i + 1)));
                            break;
                        case "VARCHAR":
                            formattedTextFields[i].setText(resultSet.getString(i + 1).trim());
                            break;
                        case "DATE":
                            Date currentDate = resultSet.getDate(i + 1);
                            currentDate.setTime(currentDate.getTime()+day);
                            formattedTextFields[i].setText(currentDate.toString());
                            break;
                        case "DOUBLE":
                            formattedTextFields[i].setText(String.valueOf(resultSet.getDouble(i + 1)));
                            break;
                        default:
                            System.out.println("Type error");
                    }
                }
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

            int i = 1;

            //skips first value because it's ID which we don't edit
            metaDataSet.next();

            //Dynamically reads column data type and transform data accordingly
            while (metaDataSet.next()){
                String type = metaDataSet.getString("TYPE_NAME");

                switch(type){
                    case "INT":
                        preparedStatement.setInt(i, Integer.parseInt(formattedTextFields[i].getText().replaceAll("\\s+","")));
                        break;

                    case "VARCHAR":
                        preparedStatement.setString(i, formattedTextFields[i].getText().trim());
                        break;

                    case"DATE":
                        String dateToCheck = formattedTextFields[i].getText();
                        if(DateValidator.isThisDateValid(dateToCheck,dateFormat))
                        {
                            preparedStatement.setDate(i, Date.valueOf(formattedTextFields[i].getText()));
                        }else{
                            throw new Exception("Entered date is wrong, use yyyy-MM-dd format");
                        }
                        break;

                    case"DOUBLE":
                        preparedStatement.setDouble(i, Double.parseDouble(formattedTextFields[i].getText()));
                        break;
                    default:
                        System.out.println("Type error");
                }
                i++;
            }

            //sets id of a worker witch we will edit
            preparedStatement.setInt(i, Integer.parseInt(editID));

            preparedStatement.executeUpdate();

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

            JOptionPane.showMessageDialog(null, "Successfully deleted worker");
        } catch (SQLException e) {
            System.out.println("Delete statement error!");
            e.printStackTrace();
        }finally {
            try{ preparedStatement.close();
                conn.close();}catch (Exception e){};
        }
    }

    //Generate MaskFormatter using given template
    private static MaskFormatter maskFormatter(String s)
    {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);

        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }

        return formatter;
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
            while (metaDataSet.next())
            {
                String type = metaDataSet.getString("TYPE_NAME");
                int columnSize = Integer.parseInt(metaDataSet.getString("COLUMN_SIZE"));
                String maskFormat;

                switch(type){
                    case "INT":
                        maskFormat = "#";
                        formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(maskFormatter(maskFormat.repeat(columnSize))));
                        formattedTextFields[i].setText("8888");
                        break;
                    case "VARCHAR":
                        //phone number is varchar in DB but only numbers should be inputed
                        if(metaDataSet.getString("COLUMN_NAME").toLowerCase().equals("Phone number".toLowerCase()))
                        {
                            maskFormat = "#";
                            formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(maskFormatter(maskFormat.repeat(columnSize))));
                            formattedTextFields[i].setText("0123456");
                        }
                        else {
                            maskFormat = "*";
                            formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(maskFormatter(maskFormat.repeat(columnSize))));
                            formattedTextFields[i].setText("xxxxxxxxxxxxx");
                        }

                        break;

                    case"DATE":
                        formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(maskFormatter("####-##-##")));
                        formattedTextFields[i].setText("1111-11-11");
                        break;
                    case"DOUBLE":
                        //to input '.' symbol you have to include all symbols and than allow only numbers and dot
                        maskFormat = "*";
                        MaskFormatter doubleFormatter = maskFormatter(maskFormat.repeat(columnSize));
                        doubleFormatter.setValidCharacters("0123456789.");
                        formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(doubleFormatter));
                        formattedTextFields[i].setText("8888.88");
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
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String fileName = filePath + "\\Report.pdf";

            String sql = "SELECT * FROM WORKERS ORDER BY ID";
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            Document document = new Document(PageSize.A4.rotate());

            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            ResultSet metaData = getColumnsMetaData();

            document.open();


            PdfPTable table = new PdfPTable(13);

            table.setHeaderRows(1);
            table.setWidthPercentage(105);

            Font cellFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);

            //adds header
            while(metaData.next())
            {
                table.addCell(new PdfPCell(new Phrase(metaData.getString("COLUMN_NAME"), headerFont)));
            }

            //adds cells
            while(resultSet.next()){
                for(int i = 0; i<13; i++){
                    PdfPCell cell = new PdfPCell(new Phrase(resultSet.getString(i+1),cellFont));
                    table.addCell(cell).setMinimumHeight(40);
                }
            }

            document.add(table);


            document.close();
            try{resultSet.close();
                preparedStatement.close();
                conn.close();}catch (Exception e){};
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();

        }finally {
            try{resultSet.close();
                preparedStatement.close();
                conn.close();}catch (Exception e){};
        }

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
}

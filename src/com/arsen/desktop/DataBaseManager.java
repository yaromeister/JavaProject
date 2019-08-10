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
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/java_project";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "admin";

    // Day lenght in miliseconds, for fixing ResultSet.getDate() bug
    private static long day = 86400000;


    private static Connection getConnection(){
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public static void addRowToTable(JTextField[] textFields)
    {
        String dateFormat = "yyyy-MM-dd";
        Connection con = getConnection();
        try {
            DatabaseMetaData metaData = con.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, "workers", null);

            String sql = "INSERT INTO `WORKERS` VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            String id = textFields[0].getText().replaceAll("\\s+","");

            if(checkIfIDExists(id)) {
                JOptionPane.showMessageDialog(null, "Worker with this ID already exists");
                return;
            }


            int i = 0;
            while (resultSet.next()){
                String type = resultSet.getString("TYPE_NAME");
                //System.out.println(resultSet.getString("COLUMN_NAME"));

                switch(type){
                    case "INT":
                            preparedStatement.setInt(i + 1, Integer.parseInt(textFields[i].getText().replaceAll("\\s+", "")));
                    break;

                    case "VARCHAR":
                        if(resultSet.getString("COLUMN_NAME").equals("Phone number")) {
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


        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public static void deleteRowFromTheTable(String rowsID)
    {
        Connection conn = getConnection();
        String sql = "DELETE FROM WORKERS " +
                     "WHERE ID = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,Integer.parseInt(rowsID));

            preparedStatement.executeUpdate();

            System.out.println("Successfully deleted");
        } catch (SQLException e) {
            System.out.println("Delete statement error!");
            e.printStackTrace();
        }
    }

    public static void viewRowFromTheTable(String rowID, JLabel[] labels)
    {
        Connection conn = getConnection();
        String sql = "SELECT * FROM WORKERS " +
                     "WHERE ID = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,Integer.parseInt(rowID));
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
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
        }

    }

    public static void viewRowFromTheTable(String rowID, JFormattedTextField[] formattedTextFields)
    {
        Connection conn = getConnection();
        String sql = "SELECT * FROM WORKERS " +
                     "WHERE ID = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,Integer.parseInt(rowID));
            ResultSet resultSet = preparedStatement.executeQuery();
            formattedTextFields[0].setEditable(false);

            if(resultSet.next())
            {
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
                            //System.out.println(String.valueOf(resultSet.getDouble(i + 1)));
                            break;
                        default:
                            System.out.println("Type error");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editRowInTheTable(JFormattedTextField[] formattedTextFields, String editID){
        Connection con = getConnection();
        try {
            DatabaseMetaData metaData = con.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, "workers", null);

            String sql = "UPDATE `WORKERS` " +
                         "SET `Last Name` = ?, `Name` = ?, `Patronum` = ?, `Date of birth` = ?, `Job` = ?, `Department` = ?, `Room number` = ?, `Phone number` = ?, `Email` = ?, `Salary` = ?, `Working since` = ?, `Notes` = ? WHERE ID = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            int i = 1;
            resultSet.next();
            while (resultSet.next()){
                String type = resultSet.getString("TYPE_NAME");
               // preparedStatement.setString(j-1, "`"+resultSet.getString("COLUMN_NAME")+"`");
               System.out.println(i + " " + "`"+resultSet.getString("COLUMN_NAME")+"`");

                switch(type){
                    case "INT":
                        preparedStatement.setInt(i, Integer.parseInt(formattedTextFields[i].getText().replaceAll("\\s+","")));
                        break;

                    case "VARCHAR":
                        preparedStatement.setString(i, formattedTextFields[i].getText().trim());
                        break;

                    case"DATE":
                        preparedStatement.setDate(i, Date.valueOf(formattedTextFields[i].getText()));

                        break;

                    case"DOUBLE":
                        preparedStatement.setDouble(i, Double.parseDouble(formattedTextFields[i].getText()));
                        break;
                    default:
                        System.out.println("Type error");
                }
                i++;
                //System.out.println(j);
            }
            preparedStatement.setInt(i, Integer.parseInt(editID));
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Input data correctly");
            System.out.println("Input data correctly");
            ex.printStackTrace();
        }
    }

    public static ResultSet getColumnsMetaData() throws SQLException
    {
        Connection con = getConnection();
        DatabaseMetaData metaData = con.getMetaData();
        ResultSet resultSet = metaData.getColumns(null, null, "workers", null);

        return resultSet;


    }


    private static MaskFormatter maskFormatter(String s){
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);

        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }

        return formatter;
    }


    public static void SetMaskFormatters(JFormattedTextField[] formattedTextFields) throws SQLException {
        //String.repeat(int number of repeats of String)        Done
        ResultSet resultSet = getColumnsMetaData();
        int i = 0;
        while (resultSet.next())
        {
            String type = resultSet.getString("TYPE_NAME");
            int columnSize = Integer.parseInt(resultSet.getString("COLUMN_SIZE"));
            //System.out.println(columnSize);
            String maskFormat;

            switch(type){
                case "INT":
                    maskFormat = "#";
                    formattedTextFields[i].setFormatterFactory(new DefaultFormatterFactory(maskFormatter(maskFormat.repeat(columnSize))));
                    formattedTextFields[i].setText("8888");
                    break;
                case "VARCHAR":
                    if(resultSet.getString("COLUMN_NAME").equals("Phone number"))
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
    }

    public static void printPDF(String filePath) {
        try {
            String fileName = filePath + "\\Report.pdf";

            Connection conn = getConnection();
            String sql = "SELECT * FROM WORKERS";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            Document document = new Document(PageSize.A4.rotate());

            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            ResultSet metaData = getColumnsMetaData();

            document.open();


            PdfPTable table = new PdfPTable(13);

            table.setHeaderRows(1);
            table.setWidthPercentage(105);

            Font cellFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);

            while(metaData.next())
            {
                table.addCell(new PdfPCell(new Phrase(metaData.getString("COLUMN_NAME"), headerFont)));
            }


            while(resultSet.next()){
                for(int i = 0; i<13; i++){
                    PdfPCell cell = new PdfPCell(new Phrase(resultSet.getString(i+1),cellFont));
                    table.addCell(cell).setMinimumHeight(40);
                }
            }

            document.add(table);


            document.close();

        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    public static boolean checkIfIDExists(String idToCheck){
        try {
        Connection conn = getConnection();
        String sql = "SELECT `ID` FROM `WORKERS`";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(idToCheck == null){
            return false;
        }

        while(resultSet.next()){
            if(resultSet.getInt(1) == Integer.parseInt(idToCheck)){
                System.out.println("Id found");
                return true;
            }
        }
            System.out.println("finished");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

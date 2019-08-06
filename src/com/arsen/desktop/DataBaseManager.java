package com.arsen.desktop;

import javax.swing.*;
import java.sql.*;


public class DataBaseManager {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/java_project";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "admin";

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
        Connection con = getConnection();
        try {
            DatabaseMetaData metaData = con.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, "workers", null);

            String sql = "INSERT INTO `WORKERS` VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            int i = 0;
            while (resultSet.next()){
                String type = resultSet.getString("TYPE_NAME");
                //System.out.println(type);

                switch(type){
                    case "INT":
                        preparedStatement.setInt(i+1, Integer.parseInt(textFields[i].getText()));
                    break;

                    case "VARCHAR":
                         preparedStatement.setString(i+1, textFields[i].getText());
                    break;

                     case"DATE":
                        preparedStatement.setDate(i+1, Date.valueOf(textFields[i].getText()));
                    break;

                    case"DOUBLE":
                        preparedStatement.setDouble(i+1, Double.parseDouble(textFields[i].getText()));
                    break;
                    default:
                    System.out.println("Type error");
                }
                i++;
            }

            /*
            preparedStatement.setInt(1, Integer.parseInt(textFields[0].getText()));
            preparedStatement.setString(2, textFields[1].getText());
            preparedStatement.setString(3, textFields[2].getText());
            preparedStatement.setString(4, textFields[3].getText());
            preparedStatement.setDate(5, Date.valueOf(textFields[4].getText()));
            preparedStatement.setString(6, textFields[5].getText());
            preparedStatement.setString(7, textFields[6].getText());
            preparedStatement.setInt(8, Integer.parseInt(textFields[7].getText()));
            preparedStatement.setInt(9, Integer.parseInt(textFields[8].getText()));
            preparedStatement.setString(10, textFields[9].getText());
            preparedStatement.setDouble(11, Double.parseDouble(textFields[10].getText()));
            preparedStatement.setDate(12, null);//Date.valueOf(textFields[11].getText()));
            preparedStatement.setString(13, textFields[12].getText());
            */
            preparedStatement.executeUpdate();


        } catch (SQLException ex) {
            ex.printStackTrace();
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
            e.printStackTrace();
            System.out.println("Delete statement error!");
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
                                labels[i].setText(resultSet.getDate(i + 1).toString());
                                break;

                            case "DOUBLE":
                                labels[i].setText(String.valueOf(resultSet.getDouble(i + 1)));
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
}

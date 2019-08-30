package com.arsen.desktop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MinMaxFieldValueDB {
    private static Connection con = ConnectionManager.getConnection();

    public static void max(String columnName){
        String sql = "SELECT MIN(?) from workers";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, columnName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                //resultSet.ge
            };

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

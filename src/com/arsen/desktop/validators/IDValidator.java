package com.arsen.desktop.validators;

import com.arsen.desktop.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IDValidator {
    public static boolean checkIfIDExists(String idToCheck)
    {
        Connection conn =  ConnectionManager.getConnection();
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
                conn.close();}catch (Exception e){}
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }finally {
            try{resultSet.close();
                preparedStatement.close();
                conn.close();}catch (SQLException | NullPointerException e){
                System.out.println("Unable to close");
            }
        }
        return false;
    }
}

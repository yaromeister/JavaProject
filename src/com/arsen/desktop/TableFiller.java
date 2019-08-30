package com.arsen.desktop;

import com.mysql.cj.xdevapi.SqlDataResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableFiller {
    public static String[] getColumnNamesDB(){
        Connection conn =  ConnectionManager.getConnection();
        ResultSet metaData = null;
        String[] columnNames = new String[13];
        try{
            metaData = MetaDataManager.getMetaData();
            int index = 0;
            while (metaData.next()){
                columnNames[index] = metaData.getString("COLUMN_NAME");
                index++;
            }
            return columnNames;

        }catch (NullPointerException | SQLException ex){
            try{metaData.close();
                conn.close();
            }catch (NullPointerException | SQLException e){
                e.printStackTrace();
                System.out.println("Unable to close");
            }
            ex.printStackTrace();
            return null;
        }

    }

    public static Object[][] getColumnDataDB(){
        Connection conn =  ConnectionManager.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            String sql = "SELECT Id,`Last Name`, Name, Patronum, `Date of birth`, Job, Department, `Room number`, `Phone number`, Email, Salary, `Working Since`, Notes FROM WORKERS ORDER BY ID";
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
                    if(i == 0){
                        data[index][i] = Integer.parseInt(resultSet.getString(i+1));
                    }else if(i == 7){
                        data[index][i] = Integer.parseInt(resultSet.getString(i+1));
                    }else if(i == 10){
                        data[index][i] = Double.valueOf(resultSet.getString(i+1));
                    }else {
                        data[index][i] = resultSet.getString(i+1);
                    }
                    }
                index++;
            }
            System.out.println("Data changed");
            return data;
        }catch (NullPointerException | SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

}

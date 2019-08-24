package com.arsen.desktop;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MetaDataManager {

    private static ResultSet resultSet = null;

    public static ResultSet getMetaData() {
        if (resultSet == null) {
            try {
                Connection con = ConnectionManager.getConnection();
                DatabaseMetaData metaData = con.getMetaData();
                ResultSet resultSet = metaData.getColumns(null, null, "workers", null);

                return resultSet;
            } catch (NullPointerException | SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return resultSet;
        }
    }
}

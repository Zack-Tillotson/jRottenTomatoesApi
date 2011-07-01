package com.criticcomrade.api.main;

import java.sql.*;

public class RtActivityDao {
    
    public static void addApiCallToLog(String link) {
	String sql = "insert into rt_activity (link) values (?)";
	PreparedStatement statement;
	try {
	    statement = DaoUtility.getConnection().prepareStatement(sql);
	    statement.setString(1, link);
	    statement.executeUpdate();
	    statement.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
}

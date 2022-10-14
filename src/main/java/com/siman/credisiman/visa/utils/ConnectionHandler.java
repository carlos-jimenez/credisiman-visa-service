package com.siman.credisiman.visa.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionHandler {
	
	  private Connection getConnection(String jndi)  {
		  
		    
		    Connection connection = null;
		    
		    try {
		      InitialContext context = new InitialContext();
		      DataSource dataSource = (DataSource) context.lookup(jndi); //(DataSource) context.lookup("jdbc/DataSource");
		      connection = dataSource.getConnection();
		    } catch (NamingException e) {
		      e.printStackTrace();
		    } catch (SQLException e) {
		      e.printStackTrace();
		    }
		    return connection;
		  }
}

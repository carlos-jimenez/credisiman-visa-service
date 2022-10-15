package com.siman.credisiman.visa.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionHandler {
	
	  public Connection getConnection(String jndi)  {
		  
		    
		    Connection connection = null;
		    
		    //para pruebas
		    Hashtable ht = new Hashtable();
      	  	ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
      	  	ht.put(Context.PROVIDER_URL, "t3://localhost:7001/");
      	  	     
      	  Hashtable props = new Hashtable();
          props.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
          props.put(Context.PROVIDER_URL, "t3://192.168.0.148:7101");
          props.put(Context.SECURITY_PRINCIPAL, "weblogic");
          props.put(Context.SECURITY_CREDENTIALS, "weblogic1");
       //   props.put("weblogic.jndi.createIntermediateContexts", "true");
          
          
		    try {
		      InitialContext context = new InitialContext(props);
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

package com.siman.credisiman.visa.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


@Slf4j
public class ConnectionHandler {

    public Connection getConnection(String jndi) {
        Connection connection = null;

        // unicamente para prueba
        Hashtable props = new Hashtable();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        //   props.put(Context.PROVIDER_URL, "t3://localhost:7001");

        try {
            InitialContext context = new InitialContext(props);
            DataSource dataSource = (DataSource) context.lookup(jndi);
            connection = dataSource.getConnection();
            log.info("conexion status: " + connection.isValid(5));
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

package com.example.crud_transacciones.modelo;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.io.IOException;

public class Conexion {
    private static Connection con;
    private static BasicDataSource dataSource;

    private Conexion(){}

    public static DataSource getDataSource() {
        if (dataSource == null) {
            try {
                Properties props = new Properties();
                InputStream is;

                String environment = System.getProperty("production.env");
                if(environment != null) {
                    props.load(new FileInputStream(environment));
                } else {
                    props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("secret.properties"));
                }
                props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
                String URL = props.getProperty("db.url");
                String username = props.getProperty("db.username");
                String password = props.getProperty("db.password");

                Class.forName("com.mysql.cj.jdbc.Driver");
                dataSource = new BasicDataSource();
                dataSource.setUrl(URL);
                dataSource.setUsername(username);
                dataSource.setPassword(password);
                dataSource.setInitialSize(5);

            } catch (IOException ex) {
                throw new RuntimeException("Error al cargar el archivo de propiedades", ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException("Faltan driver", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al conectar con la BD", ex);
            }
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public static void close() throws SQLException {
        con.close();
    }

}


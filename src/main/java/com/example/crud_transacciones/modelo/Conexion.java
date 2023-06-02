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

/*                String productionEnvPath = "/etc/secrets/production.env";
                InputStream secrets = Thread.currentThread().getContextClassLoader().getResourceAsStream(productionEnvPath);*/

/*                if (secrets == null) {
                    props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("secret.properties"));
                } else {
                    props.load(secrets);
                }*/

/*                props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
                String URL = props.getProperty("db.url");
                String username = props.getProperty("db.username");
                String password = props.getProperty("db.password");*/

                props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));

                // Obtener las variables de entorno de RENDER
                String url = System.getenv("db.url");
                String username = System.getenv("db.username");
                String password = System.getenv("db.password");

                // Si las variables de entorno de RENDER están presentes, usarlas en lugar de las del archivo de propiedade
                if( url == null || username == null || password == null ) {
                    props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("secret.properties"));
                    url = props.getProperty("db.url");
                    username = props.getProperty("db.username");
                    password = props.getProperty("db.password");
                }

                Class.forName("com.mysql.cj.jdbc.Driver");
                dataSource = new BasicDataSource();
                dataSource.setUrl(url);
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


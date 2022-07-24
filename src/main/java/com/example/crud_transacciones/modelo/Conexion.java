package com.example.crud_transacciones.modelo;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Conexion {
    private static Connection con;
    private static BasicDataSource dataSource;

    private Conexion(){}

    // JDBC
    // SINGLETON
    // POOL DE CONEXIONES
    public static DataSource getDataSource() {
        if (dataSource == null) {
            try {
                String URL = "jdbc:mysql://user:pass@localhost:3306/bd_accounts?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
                Class.forName("com.mysql.cj.jdbc.Driver");
                dataSource = new BasicDataSource();
                dataSource.setUrl(URL);
                // limita el numero de conexiones que se pueden abrir al mismo tiempo
                dataSource.setInitialSize(50);
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


}
package com.example.crud_transacciones.modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ClosureConnection {

    void evaluatePS(PreparedStatement aStatement) throws SQLException;

}

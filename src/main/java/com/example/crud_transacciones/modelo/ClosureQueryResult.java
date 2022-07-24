package com.example.crud_transacciones.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ClosureQueryResult {

    void evaluateRS(ResultSet resultSet) throws SQLException;
}

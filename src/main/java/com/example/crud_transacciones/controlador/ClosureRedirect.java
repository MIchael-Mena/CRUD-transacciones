package com.example.crud_transacciones.controlador;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ClosureRedirect {

    void execute( ) throws ServletException, IOException;
}

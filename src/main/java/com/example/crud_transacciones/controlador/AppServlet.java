package com.example.crud_transacciones.controlador;

import com.example.crud_transacciones.modelo.*;
import com.example.crud_transacciones.modelo.account.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet(name = "AppServlet", urlPatterns = {"/app"})
public class AppServlet extends HttpServlet {

    private Modelo model;
/*    private final String URI_VISTACUENTA = "WEB-INF/userTransaction/vistaCuenta.jsp";
    private final String URI_SIMULADOR = "WEB-INF/userTransaction/simulador.jsp";*/
    private final String URI_VISTACUENTA = "vistaCuenta.jsp";
    private final String URI_SIMULADOR = "simulador.jsp";


    @Override
    public void init() throws ServletException {
        this.model = getModelo();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        accion = accion == null ? "" : accion;
        int id;
        request.getSession().setAttribute("mostrarModal", false);
        switch (accion) {
            case "show":
                id = Integer.parseInt(request.getParameter("id"));
                this.redirectToVistaCuenta(request, response, id);
                break;
            case "remove":
                id = Integer.parseInt(request.getParameter("id"));
                model.removeAccount(id);
                this.redirectToSimulador(request, response);
                break;
            default:
                this.redirectToSimulador(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        accion = accion == null ? "" : accion;
        ClosureRedirect redirectToSimulador = () -> redirectToSimulador(request, response);
        switch (accion) {
            case "create":
                model.createAccount(new Customer(request.getParameter("name")));
                redirectToSimulador.execute();
                break;
            case "withdraw":
                ClosureTransaction withdraw = (anAmount, anIdAccount)-> model.withdraw(anAmount, anIdAccount);
                performTransaction(request, withdraw, redirectToSimulador);
                break;
            case "deposit":
                ClosureTransaction deposit = (anAmount, anIdAccount)-> model.deposit(anAmount, anIdAccount);
                performTransaction(request, deposit, redirectToSimulador);
                break;
            case "transfer":
                int idAccountDestiny = Integer.parseInt(request.getParameter("idDestiny"));

                ClosureRedirect redirectToInfo = () -> this.redirectToVistaCuenta(request, response, Integer.parseInt(request.getParameter("id")));
                ClosureTransaction transfer = (anAmount, anAccount) -> model.transfer(anAmount, anAccount, idAccountDestiny);

                performTransaction(request, transfer, redirectToInfo);
                break;
        }
    }

    private void performTransaction(HttpServletRequest request, ClosureTransaction aTransaction, ClosureRedirect aRedirect)
            throws ServletException, IOException {
        int idAccount = Integer.parseInt(request.getParameter("id"));
        String stringAmount = request.getParameter("amount");
        HttpSession session = request.getSession();
        try {
            int amount = Integer.parseInt(stringAmount);
            aTransaction.execute(amount, idAccount);
            session.setAttribute("transaccionAprobada", true);
        } catch (Exception e) {
            session.setAttribute("transaccionAprobada", false);
            if(e instanceof NumberFormatException) {
                session.setAttribute("mensajeDeError", "El monto debe ser un número");
            } else {
                session.setAttribute("mensajeDeError", e.getMessage());
            }
        } finally {
            session.setAttribute("mostrarModal", true);
            aRedirect.execute();
        }
    }

    private void redirectToSimulador(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("listCustomers", model.getAllCustomers());
/*        request.setAttribute("listAccounts", model.getAccounts());
        request.getRequestDispatcher(URI_SIMULADOR).forward(request, response);*/
        response.sendRedirect(URI_SIMULADOR);
    }

    private void redirectToVistaCuenta(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("account", model.getSingleAccount(id));
        session.setAttribute("otherCustomers", model.getOtherCustomers(id));
/*        request.setAttribute("account", model.getSingleAccount(id));
        request.setAttribute("otherAccounts", model.getOtherAccounts(id));
        request.getRequestDispatcher(URI_VISTACUENTA).forward(request, response);*/
        response.sendRedirect(URI_VISTACUENTA);
    }

    private Modelo getModelo() throws ServletException {
        Modelo m = null;
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties")) {
            Properties props = new Properties();
            props.load(is);
            // Cambiar entre modelo HC y modelo SQL.
            String tipoModelo = props.getProperty("HC");
            m = ModeloFactory.getInstance().crearModelo(tipoModelo);
        } catch (IOException ex) {
            throw new ServletException("Error de E/S al al leer 'config' para iniciar el Servlet", ex);
        }
        return m;
    }
}

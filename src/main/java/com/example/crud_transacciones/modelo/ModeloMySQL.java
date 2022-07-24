package com.example.crud_transacciones.modelo;

import com.example.crud_transacciones.modelo.account.Customer;
import com.example.crud_transacciones.modelo.account.SingleAccount;
import com.example.crud_transacciones.modelo.transactions.Deposit;
import com.example.crud_transacciones.modelo.transactions.Transfer;
import com.example.crud_transacciones.modelo.transactions.Withdraw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// Falta implementar el ordenamiento de las transacciones de una cuenta
// para eso hay que hay agregarle el dato dateTime a las transacciones y ordenarlo por ese dato


public class ModeloMySQL implements Modelo {

    //bd_accounts
    private static final String GET_CUSTOMERS_EXCEPT = "SELECT * FROM customers WHERE id_customer != ?";
    private static final String GET_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE id_customer = ?";
    private static final String CREATE_CUSTOMER = "INSERT INTO customers (name) VALUES (?)";
    private static final String DELETE_CUSTOMER = "DELETE FROM customers WHERE id_customer = ?";
    private static final String REGISTER_DEPOSIT = "INSERT INTO deposits (amount, fk_d_id_customer) VALUES (?, ?);";
    private static final String REGISTER_WITHDRAW = "INSERT INTO withdrawals (amount, fk_w_id_customer) VALUES (?, ?);";
    private static final String REGISTER_TRANSFER = "INSERT INTO transfers (amount, fk_origin_id, fk_destiny_id) VALUES (?, ?, ?)";
    private static final String GET_DEPOSIT = "SELECT amount, date FROM deposits INNER JOIN customers ON deposits.fk_d_id_customer = customers.id_customer " +
            "AND customers.id_customer = ?";
    private static final String GET_WITHDRAW = "SELECT amount, date FROM withdrawals INNER JOIN customers ON withdrawals.fk_w_id_customer = customers.id_customer " +
            "AND customers.id_customer = ?";
    private static final String GET_TRANSFER_WITHDRAW = "SELECT amount , date, fk_origin_id as idOrigin, fk_destiny_id as idDestiny \n" +
            "FROM transfers WHERE fk_origin_id = ?";
    private static final String GET_TRANSFER_DEPOSIT = "SELECT amount , date, fk_origin_id as idOrigin, fk_destiny_id as idDestiny \n" +
            "FROM transfers WHERE fk_destiny_id = ?";


    public Customer getCustomerById(int id) {
        Customer[] customer = {null};
        this.tryConnect(GET_CUSTOMER_BY_ID, "Error al obtener el cliente",
                ps -> this.tryExecuteQuery(ps, id, rs -> {
                    if (rs.next()) {
                        customer[0] = this.rsToCustomer(rs);
                    }
                })
        );
        if (customer[0] == null) {
            customer[0] = new Customer("Cliente no disponible");
        }
        return customer[0];
    }

    @Override
    public List<Customer> getOtherCustomers(int id) {
        return this.getCustomerExcept(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.getCustomerExcept(0);
    }

    @Override
    public SingleAccount getSingleAccount(int id) {
        SingleAccount account = new SingleAccount(this.getCustomerById(id));
        this.getDepositFor(account);
        this.getTransferDepositFor(account);
        this.getWithdrawFor(account);
        this.getTransferWithdrawFor(account);
        // TODO: Implementar el ordenamiento de las transacciones de una cuenta
        // account.sortTransactionsByDateTime();
        return account;
    }

    @Override
    public int createAccount(Customer customer) {
        AtomicInteger registersModified = new AtomicInteger();
        this.tryConnect(CREATE_CUSTOMER, "Error al crear la cuenta para cliente con ID " + customer.getId(),
                ps -> {
                    this.fillPreparedStatement(ps, customer);
                    registersModified.set(ps.executeUpdate());
                });
        return registersModified.get();
    }

    @Override
    public int removeAccount(int id) {
        // La BD pone null a la fk de la cuenta que se va a eliminar.
        AtomicInteger registersModified = new AtomicInteger();
        this.tryConnect(DELETE_CUSTOMER, "Error al eliminar al cliente con ID " + id,
                ps -> {
                    ps.setInt(1, id);
                    registersModified.set(ps.executeUpdate());
                });
        return registersModified.get();
    }

    @Override
    public int withdraw(int amount, int idAccount) {
        // Primero se verifica que la cuenta exista, que la cantidad a retirar sea mayor a 0 y que haya saldo suficiente.
        Withdraw.registerOn(amount, this.getSingleAccount(idAccount));
        return performTransaction(amount, idAccount, 0, REGISTER_WITHDRAW);
    }

    @Override
    public int deposit(int amount, int idAccount) {
        Deposit.registerOn(amount, this.getSingleAccount(idAccount));
        return performTransaction(amount, idAccount, 0, REGISTER_DEPOSIT);
    }

    @Override
    public int transfer(int amount, int idOriginAccount, int idDestinationAccount) {
        Transfer.amountFromOriginToDestination(amount, this.getSingleAccount(idOriginAccount), this.getSingleAccount(idDestinationAccount));
        return performTransaction(amount, idOriginAccount, idDestinationAccount, REGISTER_TRANSFER);
    }

    private int performTransaction(int amount, int idAccount, int anotherIdAccount, String transaction) {
        AtomicInteger registersModified = new AtomicInteger();
        this.tryConnect(transaction, "Error al intentar registrar transacción para cliente con ID " + idAccount,
                ps -> {
                    ps.setInt(1, amount);
                    ps.setInt(2, idAccount);
                    if (anotherIdAccount != 0) {
                        ps.setInt(3, anotherIdAccount);
                    }
                    registersModified.set(ps.executeUpdate());
                });
        return registersModified.get();
    }

    private List<Customer> getCustomerExcept(int idToExclude) {
        List<Customer> customers = new ArrayList<>();
        this.tryConnect(GET_CUSTOMERS_EXCEPT, "Error al obtener los clientes",
                ps -> this.tryExecuteQuery(ps, idToExclude, rs -> {
                    while (rs.next()) {
                        customers.add(this.rsToCustomer(rs));
                    }
                }));
        return customers;
    }

    private void fillPreparedStatement(PreparedStatement ps, Customer customer) throws SQLException {
        ps.setString(1, customer.getName());
    }

    private void getTransferDepositFor(SingleAccount account) {
        this.getTransactionFor(GET_TRANSFER_DEPOSIT, account,
                rs -> Transfer.registerAnDepositFor(rs.getInt("amount"), account, this.getCustomerById(rs.getInt("idOrigin")))
        );
    }

    private void getTransferWithdrawFor(SingleAccount account) {
        this.getTransactionFor(GET_TRANSFER_WITHDRAW, account,
                rs -> Transfer.registerAnWithdrawFor(rs.getInt("amount"), account, this.getCustomerById(rs.getInt("idDestiny")))
        );
    }

    private void getWithdrawFor(SingleAccount account) {
        this.getTransactionFor(GET_WITHDRAW, account, rs -> Withdraw.registerOn(rs.getInt("amount"), account));
    }

    private void getDepositFor(SingleAccount account) {
        this.getTransactionFor(GET_DEPOSIT, account, rs -> Deposit.registerOn(rs.getInt("amount"), account));
    }

    private void getTransactionFor(String queryGetTransaction, SingleAccount account, ClosureQueryResult aClosure) {
        this.tryConnect(queryGetTransaction, "Error al obtener la transacción",
                ps -> this.tryExecuteQuery(ps, account.getId(), rs -> {
                    while (rs.next()) {
                        aClosure.evaluateRS(rs);
                    }
                }));
    }

    private void tryExecuteQuery(PreparedStatement ps, int idParameter, ClosureQueryResult aClosure) throws SQLException {
        ps.setInt(1, idParameter);
        try (ResultSet rs = ps.executeQuery()) {
            aClosure.evaluateRS(rs);
        }
    }

    private Customer rsToCustomer(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_customer");
        String name = rs.getString("name");
//        String surname = rs.getString("surname");
//        String dateBirth = rs.getString("date_birth");
//        String mail = rs.getString("mail");
//        int balance = rs.getInt("balance");
        return new Customer(id, name);
    }

    private void tryConnect(String query, String messageError, ClosureConnection aClosure) {
        try (Connection connection = Conexion.getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {
            aClosure.evaluatePS(ps);
        } catch (SQLException ex) {
            throw new RuntimeException("Error de SQL", ex);
        } catch (Exception ex) {
            throw new RuntimeException(messageError, ex);
        }
    }

}

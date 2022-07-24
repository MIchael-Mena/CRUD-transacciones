package com.example.crud_transacciones.modelo;

import com.example.crud_transacciones.modelo.account.Customer;
import com.example.crud_transacciones.modelo.account.SingleAccount;

import java.util.List;

public interface Modelo {

    List<Customer> getAllCustomers();

    List<Customer> getOtherCustomers(int id);

    SingleAccount getSingleAccount(int id);

    int createAccount(Customer customer);

    int removeAccount(int id);

    int withdraw(int amount, int idAccount);

    int deposit(int amount, int idAccount);

    int transfer(int amount, int idOriginAccount, int idDestinationAccount);

}
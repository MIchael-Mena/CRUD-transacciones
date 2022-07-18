package com.example.crud_transacciones.modelo;

import java.util.List;

public interface Modelo {

    List<SingleAccount> getAccounts();

    List<SingleAccount> getOtherAccounts(int id);

    SingleAccount getSingleAccount(int id);

    int createAccount(String aName);

    int removeAccount(int id);

    void withdraw(int amount, int idAccount);

    void deposit(int amount, int idAccount);

    void transfer(int amount, int idOriginAccount, int idDestinationAccount);

}
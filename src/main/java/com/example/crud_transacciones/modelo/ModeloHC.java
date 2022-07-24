package com.example.crud_transacciones.modelo;

import com.example.crud_transacciones.modelo.account.Customer;
import com.example.crud_transacciones.modelo.account.SingleAccount;
import com.example.crud_transacciones.modelo.transactions.Deposit;
import com.example.crud_transacciones.modelo.transactions.Transfer;
import com.example.crud_transacciones.modelo.transactions.Withdraw;

import java.util.ArrayList;
import java.util.List;

public class ModeloHC implements Modelo {

    private List<SingleAccount> accounts;
    private int lastId;

    public ModeloHC() {
        this.accounts = new ArrayList<>();
        this.lastId = 0;
        createAccountsFake();
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (SingleAccount account : accounts) {
            customers.add(account.getCustomer());
        }
        return customers;
    }

    @Override
    public List<Customer> getOtherCustomers(int id) {
        List<Customer> customers = new ArrayList<>();
        for (SingleAccount account : accounts) {
            if (account.getId() != id) {
                customers.add(account.getCustomer());
            }
        }
        return customers;
    }

    /*
    public List<SingleAccount> getAccounts() {
        return new ArrayList(accounts);
    }


    public List<SingleAccount> getOtherAccounts(int id) {
        List<SingleAccount> otherAccounts = new ArrayList<>();
        for (SingleAccount account : accounts) {
            if (account.getId() != id) {
                otherAccounts.add(account);
            }
        }
        return otherAccounts;
    }*/

    @Override
    public SingleAccount getSingleAccount(int id) {
        int i = 0;
        SingleAccount encontrado = null;
        while (i < this.accounts.size() && encontrado == null) {
            SingleAccount a = this.accounts.get(i);
            if (a.getId() == id) {
                encontrado = a;
            } else {
                i++;
            }
        }
        if (encontrado == null) {
            throw new RuntimeException("No se encontró cuenta con ID " + id);
        }
        return encontrado;
    }

    @Override
    public int createAccount(Customer customer) {
        customer.setId(this.lastId + 1);
        this.accounts.add(new SingleAccount(customer));
        this.lastId++;
        return 0;
    }

    @Override
    public int removeAccount(int id) {
        SingleAccount target = getSingleAccount(id);
        this.accounts.remove(target);
        this.lastId--;
        return 0;
    }

    @Override
    public int withdraw(int amount, int idAccount) {
        SingleAccount account = this.getSingleAccount(idAccount);
        Withdraw.registerOn(amount, account);
        return 1;
    }

    @Override
    public int deposit(int amount, int idAccount) {
        SingleAccount account = this.getSingleAccount(idAccount);
        Deposit.registerOn(amount, account);
        return 1;
    }

    @Override
    public int transfer(int amount, int idOriginAccount, int idDestinationAccount) {
        SingleAccount originAccount = this.getSingleAccount(idOriginAccount);
        SingleAccount destinationAccount = this.getSingleAccount(idDestinationAccount);
        Transfer.amountFromOriginToDestination(amount, originAccount, destinationAccount);
        return 1;
    }

    private void createAccountsFake() {
        this.accounts.add(new SingleAccount(new Customer(1, "Cuenta A") ) );
        this.accounts.add(new SingleAccount(new Customer(2, "Cuenta B") ) );
        this.accounts.add(new SingleAccount(new Customer(3, "Cuenta C") ) );
        this.lastId = 3;

        Deposit.registerOn(50, this.accounts.get(0));
        Deposit.registerOn(75, this.accounts.get(1));
        Deposit.registerOn(100, this.accounts.get(2));

        Transfer.amountFromOriginToDestination(25, this.accounts.get(0), this.accounts.get(1));
    }


}

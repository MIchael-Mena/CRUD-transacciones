package com.example.crud_transacciones.modelo;

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
    }
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

    public int createAccount(String accountName) {
        this.accounts.add(new SingleAccount(lastId + 1, accountName));
        this.lastId++;
        return 0;
    }

    public int removeAccount(int id) {
        SingleAccount target = getSingleAccount(id);
        this.accounts.remove(target);
        this.lastId--;
        return 0;
    }

    public void withdraw(int amount, int idAccount) {
        SingleAccount account = this.getSingleAccount(idAccount);
        Withdraw.registerOn(amount, account);
    }

    public void deposit(int amount, int idAccount) {
        SingleAccount account = this.getSingleAccount(idAccount);
        Deposit.registerOn(amount, account);
    }

    public void transfer(int amount, int idOriginAccount, int idDestinationAccount) {
        SingleAccount originAccount = this.getSingleAccount(idOriginAccount);
        SingleAccount destinationAccount = this.getSingleAccount(idDestinationAccount);
        Transfer.amountFromOriginToDestination(amount, originAccount, destinationAccount);
    }

    private void createAccountsFake() {
        this.accounts.add(new SingleAccount(1, "Cuenta A"));
        this.accounts.add(new SingleAccount(2, "Cuenta B"));
        this.accounts.add(new SingleAccount(3, "Cuenta C"));
        this.lastId = 3;

        Deposit.registerOn(50, this.accounts.get(0));
        Deposit.registerOn(75, this.accounts.get(1));
        Deposit.registerOn(100, this.accounts.get(2));

        Transfer.amountFromOriginToDestination(25, this.accounts.get(0), this.accounts.get(1));
    }


}

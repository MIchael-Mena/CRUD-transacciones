package com.example.crud_transacciones.modelo;

import java.util.ArrayList;
import java.util.List;

public class SingleAccount {

    private final String name;
    private final int id;
    private List<AccountTransaction> transactions = new ArrayList<AccountTransaction>();

    public SingleAccount(int id, String aName) {
        this.id = id;
        this.name = aName;
    }

    public int getBalance() {
/*        int balance = 0;
        for (AccountTransaction accountTransaction : accountTransactions) {
            balance = accountTransaction.affectBalance(balance);
        }
        return balance;*/
//        return accountTransactions.stream().mapToInt((aTransaction)-> aTransaction.affectBalance(0)).sum();
        return this.transactions.stream().reduce(0, (aBalance, aTransaction) -> aTransaction.affectBalance(aBalance), Integer::sum);
    }

    public void register(AccountTransaction aTransaction) {
        transactions.add(aTransaction);
        if (this.getBalance() < 0) {
            transactions.remove(aTransaction);
            throw new IllegalStateException("Fondos insuficientes para realizar la transacción");
        }
    }

    public boolean hasResgitered(AccountTransaction aTransaction) {
        return this.transactions.contains(aTransaction);
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public List<AccountTransaction> getTransactions() {
        return new ArrayList(this.transactions) ;
    }
}

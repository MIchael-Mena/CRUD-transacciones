package com.example.crud_transacciones.modelo.account;

import com.example.crud_transacciones.modelo.transactions.AccountTransaction;

import java.util.ArrayList;
import java.util.List;

public class SingleAccount {

/*    private final String name;
    private final int id;*/
    private final Customer dataCustomer;
    private List<AccountTransaction> transactions = new ArrayList<AccountTransaction>();

/*    public SingleAccount(int id, String aName) {
        this.id = id;
        this.name = aName;
    }*/

    public SingleAccount(Customer customer){
        this.dataCustomer = customer;
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

    public Customer getCustomer() {
        return this.dataCustomer;
    }

    public int getId() {
        return this.dataCustomer.getId();
    }

    public List<AccountTransaction> getTransactions() {
        return new ArrayList(this.transactions);
    }
}

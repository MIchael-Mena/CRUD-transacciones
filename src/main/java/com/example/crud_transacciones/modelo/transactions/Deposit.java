package com.example.crud_transacciones.modelo.transactions;

import com.example.crud_transacciones.modelo.account.SingleAccount;

public class Deposit implements AccountTransaction {

    private final int amount;

    public Deposit(int amount) {
        this.validateAmount(amount);
        this.amount = amount;
    }

    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Una transacción debe tener un valor mayor a cero");
        }
    }

    public static Deposit registerOn(int anAmount, SingleAccount anAccount) {
        Deposit deposit = new Deposit(anAmount);
        anAccount.register(deposit);
        return deposit;
    }

    @Override
    public int affectBalance(int aBalance){
        return aBalance + amount;
    }

    @Override
    public int getValue(){
        return amount;
    }

    @Override
    public String getName(){
        return "Deposito";
    }

    @Override
    public Boolean isPositive() {
        return true;
    }

    @Override
    public AccountTransaction contrapartLeg() {
        return this;
    }

    @Override
    public String getAssociatedDetail() {
        return "Cajero";
    }

}

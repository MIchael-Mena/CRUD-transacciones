package com.example.crud_transacciones.modelo.transactions;

import com.example.crud_transacciones.modelo.account.SingleAccount;

public class Withdraw implements AccountTransaction {

    private final int amount;
    public Withdraw(int amount){
        this.validateAmount(amount);
        this.amount = amount;
    }

    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Una transacción debe tener un valor mayor a cero");
        }
    }

    public static Withdraw registerOn(int anAmount, SingleAccount anAccount) {
        Withdraw withdraw = new Withdraw(anAmount);
        anAccount.register(withdraw);
        return withdraw;
    }

    @Override
    public int getValue() {
        return amount;
    }

    @Override
    public int affectBalance(int aBalance){
        return aBalance - amount;
    }

    @Override
    public String getName(){
        return "Retiro";
    }

    @Override
    public Boolean isPositive() {
        return false;
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

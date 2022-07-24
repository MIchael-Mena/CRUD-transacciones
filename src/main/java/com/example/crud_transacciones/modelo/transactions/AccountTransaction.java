package com.example.crud_transacciones.modelo.transactions;

public interface AccountTransaction {


    int affectBalance(int aBalance);

    int getValue();

    String getName();

    Boolean isPositive();

    AccountTransaction contrapartLeg();

    String getAssociatedDetail();

}

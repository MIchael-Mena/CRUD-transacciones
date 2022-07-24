package com.example.crud_transacciones.modelo.transactions;

import com.example.crud_transacciones.modelo.account.Customer;
import com.example.crud_transacciones.modelo.account.SingleAccount;

abstract public class TransferLeg implements AccountTransaction {
    protected final Transfer transfer;
    protected final Customer customerOrigin;

    public TransferLeg(Transfer aTransfer, SingleAccount anAccount) {
        this.customerOrigin = anAccount.getCustomer();
        this.transfer = aTransfer;
        anAccount.register(this);
    }

    public TransferLeg(Transfer aTransfer, Customer anCustomer) {
        // válido por sobrecarga de constructor
        this.customerOrigin = anCustomer;
        this.transfer = aTransfer;
    }


    @Override
    public int getValue() {
        return this.transfer.value();
    }


    public String getAssociatedAccount() {
        return this.customerOrigin.getName();
    }
}

package com.example.crud_transacciones.modelo;

abstract public class TransferLeg implements AccountTransaction {
    protected final Transfer transfer;
    protected final String accountOrigin;

    public TransferLeg(Transfer aTransfer, SingleAccount anAccount) {
        this.accountOrigin = anAccount.getName();
        this.transfer = aTransfer;
        anAccount.register(this);
    }

    @Override
    public int getValue() {
        return this.transfer.value();
    }


    public String getAssociatedAccount() {
        return this.accountOrigin;
    }
}

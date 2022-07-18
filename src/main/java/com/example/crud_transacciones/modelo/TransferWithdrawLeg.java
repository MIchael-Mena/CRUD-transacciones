package com.example.crud_transacciones.modelo;

public class TransferWithdrawLeg extends TransferLeg {

    public TransferWithdrawLeg(Transfer aTransfer, SingleAccount anAccount) {
        super(aTransfer, anAccount);
    }

    @Override
    public int affectBalance(int aBalance) {
        return aBalance - this.getValue();
    }

    @Override
    public String getName() {
        return "Retiro por transferencia";
    }

    @Override
    public Boolean isPositive() {
        return false;
    }

    @Override
    public TransferDepositLeg contrapartLeg() {
        return transfer.depositLeg();
    }

    @Override
    public String getAssociatedDetail() {
        return "Saliente hacia cuenta: " + this.transfer.depositLeg().getAssociatedAccount();
    }

}

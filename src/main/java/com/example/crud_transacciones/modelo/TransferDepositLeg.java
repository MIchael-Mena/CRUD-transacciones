package com.example.crud_transacciones.modelo;

public class TransferDepositLeg extends TransferLeg {

    public TransferDepositLeg(Transfer aTransfer, SingleAccount anAccount) {
        super(aTransfer, anAccount);
    }

    @Override
    public int affectBalance(int aBalance) {
        return aBalance + this.getValue();
    }

/*    public TransferWithdrawLeg withdrawLeg() {
        return transfer.withdrawLeg();
    }*/

    @Override
    public String getName() {
        return "Deposito por transferencia";
    }

    @Override
    public Boolean isPositive() {
        return true;
    }

    @Override
    public TransferWithdrawLeg contrapartLeg() {
        return this.transfer.withdrawLeg();
    }

    @Override
    public String getAssociatedDetail() {
        return "Entrante desde cuenta: " + this.transfer.withdrawLeg().getAssociatedAccount();
    }

}

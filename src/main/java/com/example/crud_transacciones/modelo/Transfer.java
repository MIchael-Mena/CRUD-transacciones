package com.example.crud_transacciones.modelo;

public class Transfer {

    private int value;
    private TransferWithdrawLeg withdrawLeg;
    private TransferDepositLeg depositLeg;
    private Transfer(int anAmount){
        // private constructor to avoid instantiation
        this.value = anAmount;
    }
    public static Transfer amountFromOriginToDestination(int anAmount, SingleAccount originAccount, SingleAccount destinationAccount) {
        Transfer.assertCanBeTransferedTo(anAmount, originAccount, destinationAccount);
        Transfer transfer = new Transfer(anAmount);
        TransferWithdrawLeg transferWithdrawLeg = new TransferWithdrawLeg(transfer, originAccount);
        TransferDepositLeg transferDepositLeg = new TransferDepositLeg(transfer, destinationAccount);
        return transfer.with(transferWithdrawLeg, transferDepositLeg);
    }

    public static void assertCanBeTransferedTo(int anAmount, SingleAccount originAccount, SingleAccount destinationAccount) {
        Transfer.assertCanTransferTo(originAccount, destinationAccount);
        Transfer.assertCanTransfer(anAmount);
    }

    public static void assertCanTransferTo(SingleAccount originAccount, SingleAccount destinationAccount) {
        if (originAccount == destinationAccount) {
            throw new IllegalArgumentException("La cuenta de origen y destino no pueden ser la misma");
        }
    }

    public static void assertCanTransfer(int anAmount) {
        if (anAmount <= 0) {
            throw new IllegalArgumentException("Una transacción debe tener un valor mayor a cero");
        }
    }

    private Transfer with(TransferWithdrawLeg aTransferWithdrawLeg, TransferDepositLeg aTransferDepositLeg) {
        withdrawLeg = aTransferWithdrawLeg;
        depositLeg = aTransferDepositLeg;
        return this;
    }

    public int value() {
        return value;
    }

    public TransferDepositLeg depositLeg() {
        return depositLeg;
    }

    public TransferWithdrawLeg withdrawLeg() {
        return withdrawLeg;
    }
}

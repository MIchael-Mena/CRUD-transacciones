package com.example.crud_transacciones.modelo.transactions;

import com.example.crud_transacciones.modelo.account.Customer;
import com.example.crud_transacciones.modelo.account.SingleAccount;

public class Transfer {

    private int value;
    private TransferWithdrawLeg withdrawLeg;
    private TransferDepositLeg depositLeg;

    // OrginAccount is the account from where the transfer is made 'withdrawLeg'.
    // DestinationAccount is the account to where the transfer is made 'depositLeg'.

    private Transfer(int anAmount){
        // private constructor to avoid instantiation
        this.value = anAmount;
    }
    public static Transfer amountFromOriginToDestination(int anAmount, SingleAccount originAccount, SingleAccount destinationAccount) {
        Transfer.assertCanBeTransferedTo(anAmount, originAccount.getId(), destinationAccount.getId());
        Transfer transfer = new Transfer(anAmount);
        TransferWithdrawLeg transferWithdrawLeg = new TransferWithdrawLeg(transfer, originAccount);
        TransferDepositLeg transferDepositLeg = new TransferDepositLeg(transfer, destinationAccount);
        return transfer.with(transferWithdrawLeg, transferDepositLeg);
    }

    public static Transfer registerAnWithdrawFor(int anAmount, SingleAccount originAccount, Customer destinyCustomer) {
        // Usar para registrar una transferWithdraLeg en una cuenta de origen con fondos suficientes
        Transfer.assertCanBeTransferedTo(anAmount, originAccount.getId(), destinyCustomer.getId());
        Transfer transfer = new Transfer(anAmount);
        TransferWithdrawLeg transferWithdrawLeg = new TransferWithdrawLeg(transfer, originAccount);
        TransferDepositLeg transferDepositLeg = new TransferDepositLeg(transfer, destinyCustomer);
        return transfer.with(transferWithdrawLeg, transferDepositLeg);
    }

    public static Transfer registerAnDepositFor(int anAmount, SingleAccount destinationAccount, Customer originCustomer) {
        // Usar para registrar una transferDepositLeg en una cuenta de destino asumiendo que la
        // transferencia fue creada validadamente con una cuenta de origen con fondos suficientes
        Transfer.assertCanBeTransferedTo(anAmount, originCustomer.getId(), destinationAccount.getId());
        Transfer transfer = new Transfer(anAmount);
        TransferWithdrawLeg transferWithdrawLeg = new TransferWithdrawLeg(transfer, originCustomer);
        TransferDepositLeg transferDepositLeg = new TransferDepositLeg(transfer, destinationAccount);
        return transfer.with(transferWithdrawLeg, transferDepositLeg);
    }

    public static void assertCanBeTransferedTo(int anAmount, int idOriginAccount, int idDestinationAccount) {
        Transfer.assertCanTransferTo(idOriginAccount, idDestinationAccount);
        Transfer.assertCanTransfer(anAmount);
    }

    public static void assertCanTransferTo(int idOriginAccount, int idDestinationAccount) {
        if (idOriginAccount == idDestinationAccount) {
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

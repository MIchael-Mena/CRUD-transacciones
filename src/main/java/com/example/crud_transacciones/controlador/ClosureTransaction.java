package com.example.crud_transacciones.controlador;

import com.example.crud_transacciones.modelo.SingleAccount;

public interface ClosureTransaction {

    void execute(int amount, int idAccount);

}

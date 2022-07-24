import com.example.crud_transacciones.modelo.account.Customer;
import com.example.crud_transacciones.modelo.account.SingleAccount;
import com.example.crud_transacciones.modelo.transactions.Deposit;
import com.example.crud_transacciones.modelo.transactions.Transfer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferTest {

    private SingleAccount originAccount;
    private SingleAccount destinationAccount;
    private Customer originCustomer;
    private Customer destinyCustomer;

    @BeforeEach
    void setUp() {
        originCustomer = new Customer(1,"origen");
        destinyCustomer = new Customer(2,"destino");

        originAccount = new SingleAccount(originCustomer);
        destinationAccount = new SingleAccount(destinyCustomer);

        Deposit.registerOn(10, originAccount);
        Deposit.registerOn(10, destinationAccount);
    }

    @Test
    void unaTransferenciaDecreceElBalanceDeLaCuentaOrigenYLaIncrementaEnLaCuentaDestino(){
        Transfer.amountFromOriginToDestination(2, originAccount, destinationAccount);

        Assertions.assertEquals(8, originAccount.getBalance());
        Assertions.assertEquals(12, destinationAccount.getBalance());
    }

    @Test
    void unaTransferenciaConoceSuValor(){
        Transfer transfer = Transfer.amountFromOriginToDestination(2, originAccount, destinationAccount);

        Assertions.assertEquals(2, transfer.value() );
    }

    @Test
    void laPataDepositoConoceSuContraparteDeRetiro(){
        Transfer transfer = Transfer.amountFromOriginToDestination(2, originAccount, destinationAccount);

        Assertions.assertEquals( transfer.withdrawLeg(), transfer.depositLeg().contrapartLeg() );
    }

    @Test
    void laPataRetiroConoceSuContraparteDeDeposito(){
        Transfer transfer = Transfer.amountFromOriginToDestination(2, originAccount, destinationAccount);

        Assertions.assertEquals( transfer.depositLeg(), transfer.withdrawLeg().contrapartLeg() );
    }

/*    @Test
    void cadaPataDeLaTransferenciaConoceLaCuentaALaQuePertenece(){
        Transfer transfer = Transfer.amountFromOriginToDestination(2, originAccount, destinationAccount);

        Assertions.assertEquals( transfer.withdrawLeg().getAssociatedDetail(), originAccount.getName() );
        Assertions.assertEquals( transfer.depositLeg().getAssociatedDetail(), destinationAccount.getName() );
    }

    @Test
    void cadaPataDeLaTransferenciaConoceLaContraparteDeLaCuentaALaQuePertenece(){
        Transfer transfer = Transfer.amountFromOriginToDestination(2, originAccount, destinationAccount);

        Assertions.assertEquals( transfer.withdrawLeg().contrapartLeg().getAssociatedDetail(), destinationAccount.getName() );
        Assertions.assertEquals( transfer.depositLeg().contrapartLeg().getAssociatedDetail(), originAccount.getName() );
    }*/

    @Test
    void laCuentaDeOrigenYDestinoNoPuedenSerLaMisma(){
        Assertions.assertThrows(IllegalArgumentException.class,
                ()->Transfer.amountFromOriginToDestination(2, originAccount, originAccount),
                "La cuenta de origen y destino no pueden ser la misma");
    }

    @Test
    void elMontoDeUnaTransferenciaDebeSerMayorACero(){
        Assertions.assertThrows(IllegalArgumentException.class,
                ()->Transfer.amountFromOriginToDestination(-10, originAccount, destinationAccount),
                "Una transferencia debe tener un valor mayor a cero");
    }

    @Test
    void unaCuentaNoPuedeTenerBalanceNegativo(){
        Assertions.assertThrows(IllegalStateException.class,
                ()->Transfer.amountFromOriginToDestination(20, originAccount, destinationAccount),
                "Fondos insuficientes para realizar la transacción");
        Assertions.assertEquals(10, originAccount.getBalance());
        Assertions.assertEquals(10, destinationAccount.getBalance());
    }

    @Test
    void sePuedeRegistrarUnaTransferenciaDeRetiroParaUnaCuenta(){
        Transfer.registerAnWithdrawFor(2, originAccount, destinyCustomer);
        Assertions.assertEquals(8, originAccount.getBalance());
    }

    @Test
    void sePuedeRegistrarUnaTransferenciaDeDepositoParaUnaCuenta(){
        Transfer.registerAnDepositFor(2, destinationAccount, originCustomer);
        Assertions.assertEquals(12, destinationAccount.getBalance());
    }
}

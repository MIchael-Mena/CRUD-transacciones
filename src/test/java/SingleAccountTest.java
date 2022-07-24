import com.example.crud_transacciones.modelo.account.Customer;
import com.example.crud_transacciones.modelo.transactions.Deposit;
import com.example.crud_transacciones.modelo.account.SingleAccount;
import com.example.crud_transacciones.modelo.transactions.Withdraw;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SingleAccountTest {

    private SingleAccount account;

    @BeforeEach
    void setUp() {
        account = new SingleAccount(new Customer(1,"juan"));
    }

    @Test
    void unaCuentaTieneBalanceCeroCuándoEsCreada() {
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    void unDepositoIncrementaElBalancePorElValorDeTransaccion() {
        Deposit.registerOn(100, account);

        Assertions.assertEquals(100, account.getBalance());
    }

    @Test
    void unaCuentaNoPuedeTenerBalanceNegativo(){
        /*        try {
            Withdraw.registerOn(100, account);
        } catch (IllegalStateException e) {
            Assertions.assertEquals("No se puede realizar la transacción", e.getMessage());
            Assertions.assertEquals(0, account.getBalance());
        }*/
        Assertions.assertThrows(IllegalStateException.class,
                () -> Withdraw.registerOn(100, account),
                "Fondos insuficientes para realizar la transacción");
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    void unRetiroReduceElBalancePorElValorDeTransaccion() {
        Deposit.registerOn(150, account);
        Withdraw.registerOn(100, account);

        Assertions.assertEquals(50, account.getBalance());
    }

    @Test
    void elValorDeUnRetiroODepositoDebeSerPositivo(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Withdraw.registerOn(-100, account),
                "Una transacción debe tener un valor mayor a cero");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Deposit.registerOn(-100, account),
                "Una transacción debe tener un valor mayor a cero");

        Assertions.assertEquals(0, account.getBalance());
    }


    @Test
    void unaCuentaConoceLasTransaccionesRegistradas() {
        Deposit deposit = Deposit.registerOn(100, account);
        Withdraw withdraw = Withdraw.registerOn(50, account);

        Assertions.assertTrue( account.hasResgitered(deposit) );
        Assertions.assertTrue( account.hasResgitered(withdraw) );
    }

    @Test
    void unaCuentaNoConoceLasTransaccionesQueNoHaRealizado() {
        Deposit deposit = new Deposit(100);
        Withdraw withdraw = new Withdraw(50);

        Assertions.assertFalse( account.hasResgitered(deposit) );
        Assertions.assertFalse( account.hasResgitered(withdraw) );
    }

}

package se.kth.iv1350.retailstore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CashRegisterTest {
    private CashRegister cashRegister;

    @BeforeEach
    public void setUp() {
        cashRegister = new CashRegister();
    }

    @Test
    public void testInitialAmountIsZero() {
        assertEquals(0.0, cashRegister.getAmountInRegister(),
                "New cash register should start with zero amount");
    }

    @Test
    public void testAddPayment() {
        double initialAmount = cashRegister.getAmountInRegister();
        double paymentAmount = 100.50;
        CashPayment payment = new CashPayment(paymentAmount, 0);

        cashRegister.addPayment(payment);

        double expectedAmount = initialAmount + paymentAmount;
        assertEquals(expectedAmount, cashRegister.getAmountInRegister(),
                "Cash register should correctly add payment amount");
    }

    @Test
    public void testAddMultiplePayments() {
        double payment1Amount = 50.25;
        double payment2Amount = 75.75;
        CashPayment payment1 = new CashPayment(payment1Amount, 0);
        CashPayment payment2 = new CashPayment(payment2Amount, 0);

        cashRegister.addPayment(payment1);
        cashRegister.addPayment(payment2);

        double expectedAmount = payment1Amount + payment2Amount;
        assertEquals(expectedAmount, cashRegister.getAmountInRegister(),
                "Cash register should accumulate multiple payments correctly");
    }

    @Test
    public void testAddZeroPayment() {
        double initialAmount = cashRegister.getAmountInRegister();
        CashPayment zeroPayment = new CashPayment(0, 0);

        cashRegister.addPayment(zeroPayment);

        assertEquals(initialAmount, cashRegister.getAmountInRegister(),
                "Adding zero payment should not change cash register amount");
    }

    @Test
    public void testAddNegativePayment() {
        double initialAmount = cashRegister.getAmountInRegister();
        double negativeAmount = -50.0;
        CashPayment negativePayment = new CashPayment(negativeAmount, 0);

        cashRegister.addPayment(negativePayment);

        double expectedAmount = initialAmount + negativeAmount;
        assertEquals(expectedAmount, cashRegister.getAmountInRegister(),
                "Cash register can handle negative payments");
    }

    @Test
    public void testGetAmountInRegister() {
        double paymentAmount = 123.45;
        CashPayment payment = new CashPayment(paymentAmount, 0);
        cashRegister.addPayment(payment);

        assertEquals(paymentAmount, cashRegister.getAmountInRegister(),
                "getAmountInRegister should return the current amount");
    }

    @Test
    public void testPrecisionHandling() {
        double payment1 = 0.1;
        double payment2 = 0.2;
        CashPayment paymentObj1 = new CashPayment(payment1, 0);
        CashPayment paymentObj2 = new CashPayment(payment2, 0);

        cashRegister.addPayment(paymentObj1);
        cashRegister.addPayment(paymentObj2);

        // 0.1 + 0.2 should be 0.3, but with floating point it might not be exact
        assertEquals(0.3, cashRegister.getAmountInRegister(), 0.0001,
                "Cash register should handle floating point precision correctly");
    }
}
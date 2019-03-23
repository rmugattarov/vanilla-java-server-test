package rmugattarov.vanilla.java.server.test.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import rmugattarov.vanilla.java.server.test.constants.WorkItemType;
import rmugattarov.vanilla.java.server.test.model.WorkItem;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Tests {@link TransactionExecutor}
 */
public class TransactionExecutorTest {
    /**
     * Tests {@link TransactionExecutor#addWorkItem(WorkItem)}
     */
    @Test
    public void test_addWorkItem_deposit() {
        TransactionExecutor transactionExecutor = new TransactionExecutor();

        String acc1 = RandomStringUtils.random(10);

        Optional<BigInteger> balance = transactionExecutor.getBalance(acc1);
        Assert.assertFalse(balance.isPresent());

        WorkItem workItem = new WorkItem(WorkItemType.DEPOSIT, null, acc1, 100);
        transactionExecutor.addWorkItem(workItem);
        balance = transactionExecutor.getBalance(acc1);

        Assert.assertTrue(balance.isPresent());
        Assert.assertEquals(100, balance.get().intValue());
    }

    /**
     * Tests {@link TransactionExecutor#addWorkItem(WorkItem)}
     */
    @Test
    public void test_addWorkItem_withdraw() {
        TransactionExecutor transactionExecutor = new TransactionExecutor();

        String acc1 = RandomStringUtils.random(10);

        Optional<BigInteger> balance = transactionExecutor.getBalance(acc1);
        Assert.assertFalse(balance.isPresent());

        WorkItem workItem = new WorkItem(WorkItemType.WITHDRAW, acc1, null, 100);
        transactionExecutor.addWorkItem(workItem);
        balance = transactionExecutor.getBalance(acc1);

        Assert.assertTrue(balance.isPresent());
        Assert.assertEquals(-100, balance.get().intValue());
    }

    /**
     * Tests {@link TransactionExecutor#addWorkItem(WorkItem)}
     */
    @Test
    public void test_addWorkItem_transfer() {
        TransactionExecutor transactionExecutor = new TransactionExecutor();

        String acc1 = RandomStringUtils.random(10);
        String acc2 = RandomStringUtils.random(10);

        Optional<BigInteger> balance1 = transactionExecutor.getBalance(acc1);
        Optional<BigInteger> balance2 = transactionExecutor.getBalance(acc2);
        Assert.assertFalse(balance1.isPresent());
        Assert.assertFalse(balance2.isPresent());

        WorkItem workItem = new WorkItem(WorkItemType.TRANSFER, acc1, acc2, 100);
        transactionExecutor.addWorkItem(workItem);
        balance1 = transactionExecutor.getBalance(acc1);
        balance2 = transactionExecutor.getBalance(acc2);

        Assert.assertTrue(balance1.isPresent());
        Assert.assertTrue(balance2.isPresent());
        Assert.assertEquals(-100, balance1.get().intValue());
        Assert.assertEquals(100, balance2.get().intValue());
    }
}

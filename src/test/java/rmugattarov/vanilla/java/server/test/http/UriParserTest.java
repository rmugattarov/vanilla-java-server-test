package rmugattarov.vanilla.java.server.test.http;

import org.junit.Assert;
import org.junit.Test;
import rmugattarov.vanilla.java.server.test.constants.WorkItemType;
import rmugattarov.vanilla.java.server.test.model.WorkItem;

import java.net.URI;
import java.util.Optional;

/**
 * Test for {@link UriParser}
 */
public class UriParserTest {
    /**
     * Tests {@link UriParser#parseUriIntoWorkItem(URI)}
     */
    @Test
    public void test_parseUriIntoWorkItem_deposit() {
        System.out.println("!!!!!!!!");
        URI uri = URI.create("http://localhost:8888/deposit?to=robert&sum=100");
        Optional<WorkItem> workItemOpt = UriParser.parseUriIntoWorkItem(uri);

        Assert.assertTrue(workItemOpt.isPresent());
        WorkItem workItem = workItemOpt.get();

        Assert.assertEquals(WorkItemType.DEPOSIT, workItem.getWorkItemType());
        Assert.assertEquals("robert", workItem.getTo());
        Assert.assertEquals(Integer.valueOf("100"), workItem.getSum());
        Assert.assertNull(workItem.getFrom());
    }

    /**
     * Tests {@link UriParser#parseUriIntoWorkItem(URI)}
     */
    @Test
    public void test_parseUriIntoWorkItem_withdraw() {
        URI uri = URI.create("http://localhost:8888/withdraw?from=robert&sum=100");
        Optional<WorkItem> workItemOpt = UriParser.parseUriIntoWorkItem(uri);

        Assert.assertTrue(workItemOpt.isPresent());
        WorkItem workItem = workItemOpt.get();

        Assert.assertEquals(WorkItemType.WITHDRAW, workItem.getWorkItemType());
        Assert.assertEquals("robert", workItem.getFrom());
        Assert.assertEquals(Integer.valueOf("100"), workItem.getSum());
        Assert.assertNull(workItem.getTo());
    }

    /**
     * Tests {@link UriParser#parseUriIntoWorkItem(URI)}
     */
    @Test
    public void test_parseUriIntoWorkItem_transfer() {
        URI uri = URI.create("http://localhost:8888/transfer?from=vladimir&to=donald&sum=100");
        Optional<WorkItem> workItemOpt = UriParser.parseUriIntoWorkItem(uri);

        Assert.assertTrue(workItemOpt.isPresent());
        WorkItem workItem = workItemOpt.get();

        Assert.assertEquals(WorkItemType.TRANSFER, workItem.getWorkItemType());
        Assert.assertEquals("vladimir", workItem.getFrom());
        Assert.assertEquals("donald", workItem.getTo());
        Assert.assertEquals(Integer.valueOf("100"), workItem.getSum());
    }
}

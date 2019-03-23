package rmugattarov.vanilla.java.server.test.model;

import lombok.Value;
import rmugattarov.vanilla.java.server.test.constants.WorkItemType;

@Value
public class WorkItem {
    WorkItemType workItemType;
    String from;
    String to;
    Integer sum;
}

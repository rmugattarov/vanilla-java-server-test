package rmugattarov.vanilla.java.server.test.constants;

import lombok.Getter;

@Getter
public enum WorkItemType {
    DEPOSIT("/deposit"), WITHDRAW("/withdraw"), TRANSFER("/transfer");

    private String path;

    WorkItemType(String path) {
        this.path = path;
    }

    public static WorkItemType fromPath(String path) {
        for (WorkItemType t: values()) {
            if(t.getPath().equals(path)) return t;
        }
        return null;
    }
}

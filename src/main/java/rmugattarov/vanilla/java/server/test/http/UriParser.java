package rmugattarov.vanilla.java.server.test.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import rmugattarov.vanilla.java.server.test.constants.WorkItemType;
import rmugattarov.vanilla.java.server.test.model.WorkItem;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

public final class UriParser {
    private final static Pattern intPattern = Pattern.compile("\\d+");

    /**
     * @param uri Request {@link URI}
     * @return A valid {@link WorkItem}
     */
    public static Optional<WorkItem> parseUriIntoWorkItem(URI uri) {
        return Optional.ofNullable(uri)
                .map(URI::getPath)
                .map(WorkItemType::fromPath)
                .map(type -> getWorkItem(uri, type));
    }

    private static WorkItem getWorkItem(URI uri, WorkItemType type) {
        Map<String, String> params = parseParams(uri);
        switch (type) {
            case DEPOSIT:
                if (isValidDeposit(params))
                    return new WorkItem(WorkItemType.DEPOSIT, null, params.get("to"), Integer.valueOf(params.get("sum")));
                break;
            case WITHDRAW:
                if (isValidWithdraw(params))
                    return new WorkItem(WorkItemType.WITHDRAW, params.get("from"), null, Integer.valueOf(params.get("sum")));
                break;
            case TRANSFER:
                if (isValidTransfer(params))
                    return new WorkItem(WorkItemType.TRANSFER, params.get("from"), params.get("to"), Integer.valueOf(params.get("sum")));
                break;
        }
        return null;
    }

    private static Map<String, String> parseParams(URI uri) {
        return URLEncodedUtils.parse(uri, Charset.forName("UTF-8")).stream()
                .filter(p -> !isBlank(p.getName()) && !isBlank(p.getValue()))
                .collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));
    }

    private static boolean isValidDeposit(Map<String, String> params) {
        return params.containsKey("to") && intPattern.matcher(params.get("sum")).matches();
    }

    private static boolean isValidWithdraw(Map<String, String> params) {
        return params.containsKey("from") && intPattern.matcher(params.get("sum")).matches();
    }

    private static boolean isValidTransfer(Map<String, String> params) {
        return params.containsKey("from") && params.containsKey("to") && intPattern.matcher(params.get("sum")).matches();
    }
}

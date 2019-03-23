package rmugattarov.vanilla.java.server.test.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.slf4j.Slf4j;
import rmugattarov.vanilla.java.server.test.service.TransactionExecutor;

import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class MyHttpHandler implements HttpHandler {

    private final TransactionExecutor transactionExecutor;

    public MyHttpHandler(TransactionExecutor transactionExecutor) {
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream responseBody = null;
        try {
            String accounts = UriParser.parseUriIntoWorkItem(exchange.getRequestURI())
                    .map(transactionExecutor::addWorkItem)
                    .orElse(transactionExecutor.toString());

            String response = "ACCOUNTS\n\n============\n\n" + accounts +
                    "\n\n============\n\nExamples:\n\n" +
                    "1) Check accounts: http://localhost:8888/\n" +
                    "2) Deposit: http://localhost:8888/deposit?to=ivan&sum=100\n" +
                    "3) Withdraw: http://localhost:8888/withdraw?from=ivan&sum=100\n" +
                    "4) Transfer: http://localhost:8888/transfer?from=ivan&sum=100&to=natasha";

            exchange.getResponseHeaders().add("Cache-Control", "max-age=0");
            exchange.sendResponseHeaders(200, response.length());
            responseBody = exchange.getResponseBody();
            responseBody.write(response.getBytes());
        } catch (Exception e) {
            log.error("HTTP handler error", e);
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }
        }
    }
}

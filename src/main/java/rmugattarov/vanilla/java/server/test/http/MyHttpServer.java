package rmugattarov.vanilla.java.server.test.http;

import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import rmugattarov.vanilla.java.server.test.service.TransactionExecutor;

import java.io.IOException;
import java.net.InetSocketAddress;

@Slf4j
public class MyHttpServer {
    private HttpServer server;

    public void start() {
        try {
            server = HttpServer.create(new InetSocketAddress(8888), 0);
        } catch (IOException e) {
            log.error("Failed to create HTTP server", e);
        }
        if (server == null) throw new RuntimeException("Failed to create HTTP server");

        server.createContext("/", new MyHttpHandler(new TransactionExecutor()));
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }
}

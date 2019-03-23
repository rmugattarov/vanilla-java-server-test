package rmugattarov.vanilla.java.server.test;

import lombok.extern.slf4j.Slf4j;
import rmugattarov.vanilla.java.server.test.http.MyHttpServer;

@Slf4j
public class App {
    public static void main(String[] args) {
        MyHttpServer server = null;
        try {
            server = new MyHttpServer();
            server.start();
        } catch (Throwable t){
            if (server != null) {
                server.stop();
            }
        }
    }
}

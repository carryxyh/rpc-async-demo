package carryxyh.org.clientasync;

import carryxyh.org.api.EchoService;
import carryxyh.org.pkg.Process;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws Exception {

        // waiting server env setup.
        CountDownLatch c = new CountDownLatch(1);

        // server env.
        new Process(() -> {
            new Server().start();
            c.countDown();
        }).start();

        // client env.
        new Process(() -> {
            try {
                c.await();
            } catch (Exception ignore) {
            }
            EchoService echoService = new Proxy();
            while (true) {

                CompletableFuture<String> f = echoService.asyncEcho(EchoService.PARAM);
                f.whenComplete((s, throwable) -> {
                    if (throwable != null) {
                        System.out.println(throwable);
                    } else {
                        System.out.println(s);
                    }
                });

                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception ignore) {
                }
            }
        }).start();

        System.in.read();
    }
}

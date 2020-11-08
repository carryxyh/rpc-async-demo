package carryxyh.org.basic;

import carryxyh.org.api.EchoService;
import carryxyh.org.pkg.Process;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

                System.out.println(echoService.echo(EchoService.PARAM));

                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception ignore) {
                }
            }
        }).start();

        System.in.read();
    }
}

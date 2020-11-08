package carryxyh.org.api.impl;

import carryxyh.org.api.EchoService;
import carryxyh.org.serverasync.AsyncContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * EchoServiceImpl
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-11-08
 */
public class EchoServiceImpl implements EchoService {

    @Override
    public String echo(String saySth) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception ignore) {
            // mock business time-consuming...
        }
        return "hi, " + saySth;
    }

    @Override
    public CompletableFuture<String> asyncEcho(String saySth) {
        return CompletableFuture.completedFuture(echo(saySth));
    }

    @Override
    public String serverAsync(String saySth) {
        final AsyncContext ac = AsyncContext.startAsync();
        new Thread(() -> ac.write(echo(saySth))).start();
        return null;
    }
}

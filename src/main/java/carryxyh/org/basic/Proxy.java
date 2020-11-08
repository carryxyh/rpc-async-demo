package carryxyh.org.basic;

import carryxyh.org.api.EchoService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Proxy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-11-08
 */
public class Proxy implements EchoService {

    private final EchoInvoker echoInvoker = new EchoInvoker();

    private final RequestId requestId = new RequestId();

    @Override
    public String echo(String saySth) {
        Request req = new Request();
        req.id = requestId.getId();
        req.saySth = saySth;
        req.method = "echo";
        Response resp = echoInvoker.invoke(req);
        return resp.echoSth;
    }

    @Override
    public CompletableFuture<String> asyncEcho(String saySth) {
        return null;
    }

    @Override
    public String serverAsync(String saySth) {
        return null;
    }

    static class RequestId extends AtomicLong {

        private static final long serialVersionUID = 1414727825516079061L;

        long getId() {
            return this.getAndIncrement();
        }
    }
}

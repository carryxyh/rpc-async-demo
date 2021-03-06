package carryxyh.org.clientasync;

import carryxyh.org.api.EchoService;
import carryxyh.org.api.impl.EchoServiceImpl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * EchoImplInvoker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-11-08
 */
public class EchoImplInvoker extends InvokerAdapter {

    /**
     * it is equivalent to the server thread pool in the rpc framework.
     */
    private static final Executor EXECUTOR = Executors.newFixedThreadPool(5);

    private final EchoService impl = new EchoServiceImpl();

    public EchoImplInvoker() {
        MockTcpNetworking.getInstance().server = this;
    }

    @Override
    public Response invoke(Request request) {
        Response r = new Response();
        r.requestId = request.id;
        if ("echo".equals(request.method)) {
            r.echoSth = impl.echo(request.saySth);
        } else if ("asyncEcho".equals(request.method)) {
            CompletableFuture<String> f = impl.asyncEcho(request.saySth);
            try {
                r.echoSth = f.get();
            } catch (Exception ignore) {
            }
        }
        return r;
    }

    @Override
    public void receive(Object event) {
        assert event instanceof Request;
        EXECUTOR.execute(() -> {
            Response r = invoke((Request) event);
            MockTcpNetworking.getInstance().response(r);
        });
    }
}

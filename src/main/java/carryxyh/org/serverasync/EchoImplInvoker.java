package carryxyh.org.serverasync;

import carryxyh.org.api.EchoService;
import carryxyh.org.api.impl.EchoServiceImpl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/**
 * EchoImplInvoker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-11-08
 */
public class EchoImplInvoker implements Invoker {

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
    public CompletableFuture<Response> asyncInvoke(Request request) {
        // meaningless result, will always be null for correct way.
        String s = impl.serverAsync(request.saySth);
        assert s == null;
        AsyncContext ac = AsyncContext.getAsyncContext();
        CompletableFuture<Object> inter = ac.getInternalFuture();
        CompletableFuture<Response> rf = new CompletableFuture<>();
        inter.whenComplete((o, throwable) -> {
            if (throwable != null) {
                // ignore...
            } else {
                // for simplicity..
                assert o instanceof String;
                Response r = new Response();
                r.requestId = request.id;
                r.echoSth = (String) o;
                rf.complete(r);
            }
        });
        return rf;
    }

    @Override
    public void receive(Object event) {
        assert event instanceof Request;
        EXECUTOR.execute(() -> {
            if ("serverAsync".equals(((Request) event).method)) {
                CompletableFuture<Response> f = asyncInvoke((Request) event);
                f.whenComplete((response, throwable) -> {
                    if (throwable != null) {
                        // ignore...
                    } else {
                        MockTcpNetworking.getInstance().response(response);
                    }
                });
            } else {
                Response r = invoke((Request) event);
                MockTcpNetworking.getInstance().response(r);
            }
        });
    }
}

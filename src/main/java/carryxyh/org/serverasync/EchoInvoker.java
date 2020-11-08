package carryxyh.org.serverasync;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * EchoInvoker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-11-08
 */
public class EchoInvoker implements Invoker {

    private static final Map<Long, CompletableFuture<Response>> FUTURE_MAP = new HashMap<>();

    public EchoInvoker() {
        MockTcpNetworking.getInstance().client = this;
    }

    @Override
    public Response invoke(Request request) {
        CompletableFuture<Response> f = new CompletableFuture<>();
        FUTURE_MAP.put(request.id, f);
        MockTcpNetworking.getInstance().request(request);
        try {
            // let it wait here forever, for simplicity.
            return f.get();
        } catch (Exception ignore) {
            return null;
        }
    }

    @Override
    public CompletableFuture<Response> asyncInvoke(Request request) {
        CompletableFuture<Response> f = new CompletableFuture<>();
        FUTURE_MAP.put(request.id, f);
        MockTcpNetworking.getInstance().request(request);
        return f;
    }

    @Override
    public void receive(Object event) {
        assert event instanceof Response;
        CompletableFuture<Response> remove = FUTURE_MAP.remove(((Response) event).requestId);
        assert remove != null;
        remove.complete(((Response) event));
    }
}

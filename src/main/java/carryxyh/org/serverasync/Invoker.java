package carryxyh.org.serverasync;

import java.util.concurrent.CompletableFuture;

/**
 * Invoker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-11-08
 */
public interface Invoker extends MockTcpNetworking.EventListener {

    Response invoke(Request request);

    CompletableFuture<Response> asyncInvoke(Request request);
}

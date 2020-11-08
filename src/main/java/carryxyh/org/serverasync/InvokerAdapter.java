package carryxyh.org.serverasync;

import java.util.concurrent.CompletableFuture;

/**
 * InvokerAdapter
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-11-08
 */
public abstract class InvokerAdapter implements Invoker {

    @Override
    public CompletableFuture<Response> asyncInvoke(Request request) {
        return null;
    }
}

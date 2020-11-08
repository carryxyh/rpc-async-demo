package carryxyh.org.serverasync;

import java.util.concurrent.CompletableFuture;

/**
 * AsyncContext
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-11-08
 */
public class AsyncContext {

    private static final ThreadLocal<AsyncContext> STORE = new ThreadLocal<>();

    private CompletableFuture<Object> internalFuture = new CompletableFuture<>();

    public CompletableFuture<Object> getInternalFuture() {
        return internalFuture;
    }

    public static AsyncContext startAsync() {
        AsyncContext ac = new AsyncContext();
        STORE.set(ac);
        return ac;
    }

    public static AsyncContext getAsyncContext() {
        return STORE.get();
    }

    public void write(Object result) {
        if (result instanceof Throwable) {
            internalFuture.completeExceptionally((Throwable) result);
        } else {
            internalFuture.complete(result);
        }
    }
}

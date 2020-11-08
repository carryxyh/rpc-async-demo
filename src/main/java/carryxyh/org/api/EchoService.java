package carryxyh.org.api;

import java.util.concurrent.CompletableFuture;

/**
 * EchoService
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-11-08
 */
public interface EchoService {

    String PARAM = "carryxyh@apache.org";

    String echo(String saySth);

    CompletableFuture<String> asyncEcho(String saySth);

    String serverAsync(String saySth);
}

package carryxyh.org.clientasync;

/**
 * Server
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-11-08
 */
public class Server {

    private EchoImplInvoker echoImplInvoker;

    public void start() {
        echoImplInvoker = new EchoImplInvoker();
    }

    public void destroy() {
        echoImplInvoker = null;
    }
}

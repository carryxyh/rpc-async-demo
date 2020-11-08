package carryxyh.org.clientasync;

/**
 * MockTcpNetworking
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-11-08
 */
public class MockTcpNetworking {

    private static final MockTcpNetworking NETWORKING = new MockTcpNetworking();

    private MockTcpNetworking() {
    }

    public static MockTcpNetworking getInstance() {
        return NETWORKING;
    }

    EventListener client;

    EventListener server;

    /**
     * the tcp network environment is duplex.
     * This means that the network transmission is asynchronous and will not have any return value.
     */
    void request(Request request) {
        new Thread(() -> server.receive(request)).start();
    }

    void response(Response response) {
        new Thread(() -> client.receive(response)).start();
    }

    interface EventListener {

        void receive(Object event);
    }
}

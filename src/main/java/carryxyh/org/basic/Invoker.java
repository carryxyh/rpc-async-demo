package carryxyh.org.basic;

/**
 * Invoker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-11-08
 */
public interface Invoker extends MockTcpNetworking.EventListener {

    Response invoke(Request request);
}

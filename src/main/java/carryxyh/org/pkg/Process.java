package carryxyh.org.pkg;

/**
 * Process
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-11-08
 */
public class Process extends Thread {

    public Process(Runnable runnable) {
        super(runnable);
        super.setDaemon(false);
    }
}

package nhnis.fw.tcf.timeout;

/**
 * H2 {@code CREATE ALIAS} 용. {@link Thread#sleep(long)} 는 Duration 오버로드와 충돌한다.
 */
public final class H2SleepAlias {

    private H2SleepAlias() {
    }

    public static void sleepMs(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }
}

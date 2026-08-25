package nhnis.mg.co.a.demo;

/**
 * H2 {@code CREATE ALIAS SLEEP} 대상. JDK {@link Thread#sleep} 오버로드 충돌을 피한다.
 * Statement.cancel() E2E 데모 전용.
 */
public final class H2SleepAlias {

    private H2SleepAlias() {
    }

    public static void sleepMs(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }
}

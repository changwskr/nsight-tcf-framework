package nhnis.eos.co.a.batch;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("eos.batch")
public class EosBatchProperties {

    /** 스케줄 활성화. local 기본 true. */
    private boolean enabled = false;

    /** 애플리케이션 기동 직후 1회 실행. */
    private boolean runOnStartup = false;

    private String statusRecalcCron = "0 0 2 * * *";
    private String exceptionExpireCron = "0 15 2 * * *";
    private String actionOverdueCron = "0 30 2 * * *";
    private String monthlyCheckMissCron = "0 0 3 1 * *";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isRunOnStartup() {
        return runOnStartup;
    }

    public void setRunOnStartup(boolean runOnStartup) {
        this.runOnStartup = runOnStartup;
    }

    public String getStatusRecalcCron() {
        return statusRecalcCron;
    }

    public void setStatusRecalcCron(String statusRecalcCron) {
        this.statusRecalcCron = statusRecalcCron;
    }

    public String getExceptionExpireCron() {
        return exceptionExpireCron;
    }

    public void setExceptionExpireCron(String exceptionExpireCron) {
        this.exceptionExpireCron = exceptionExpireCron;
    }

    public String getActionOverdueCron() {
        return actionOverdueCron;
    }

    public void setActionOverdueCron(String actionOverdueCron) {
        this.actionOverdueCron = actionOverdueCron;
    }

    public String getMonthlyCheckMissCron() {
        return monthlyCheckMissCron;
    }

    public void setMonthlyCheckMissCron(String monthlyCheckMissCron) {
        this.monthlyCheckMissCron = monthlyCheckMissCron;
    }
}

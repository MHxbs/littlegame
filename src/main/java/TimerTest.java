import java.util.TimerTask;

public class TimerTest extends TimerTask {

    private String jobName = "";

    public TimerTest(String jobName) {
        super();
        this.jobName = jobName;
    }

    @Override
    public void run() {
        System.out.println("execute " + jobName);
    }
}

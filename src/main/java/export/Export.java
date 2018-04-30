package export;

import java.util.Timer;

public class Export {

	public static void main(String[] args) {
		Timer t = new Timer();
		CronJob mTask = new CronJob();
		// This task is scheduled to run every 10 seconds

		t.scheduleAtFixedRate(mTask, 0, 10000);
	}

}

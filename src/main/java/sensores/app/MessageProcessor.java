package sensores.app;

import org.bson.Document;
import org.json.JSONObject;

import java.util.Properties;
import java.util.TimerTask;
import java.util.Timer;

public class MessageProcessor {
    private MongoConnection mongoConnection;
    private TimerTask timerTask;
    private Properties prop;
    private boolean isFirstRun = true;

    private int counter = 0;

    MessageProcessor(Properties prop) {
        mongoConnection = new MongoConnection();
        this.prop = prop;

    }

    public void runVerification(String info) {
        JSONObject object = new JSONObject(info);
        Document document = Translators.translateToMongo(object);
        if (document == null)
            System.out.println("Invalid Payload!");
        else if (isFirstRun) {
            startTimer();
            if (isValid()) {
                mongoConnection.save(document);
                isFirstRun = false;
            }
        }
        else if (isTimeToSave()) {
                if (isValid()) {
                    mongoConnection.save(document);
                    startTimer();
                }
            }
        }

    //TODO: Add method to identify anomalies and save it on a buffer to compare on next

    private void startTimer() {
        if (timerTask != null) {
            timerTask.cancel();
        	counter = 0;
        }

        timerTask = new TimerTask() {

            @Override
            public void run() {
                System.out.println("TimerTask executing counter is: " + counter);
                counter++;//increments the counter
            }
        };

        Timer timer = new Timer("MyTimer");//create a new Timer

        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private boolean isTimeToSave() {
        System.out.println("TIME " + Integer.parseInt(prop.getProperty("time_to_save_info")));
        return counter >= Integer.parseInt(prop.getProperty("time_to_save_info"));
    }

    private boolean isValid() {
        return true;
    }
}

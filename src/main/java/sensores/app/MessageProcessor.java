package sensores.app;

import org.bson.Document;
import org.json.JSONObject;
import java.util.TimerTask;
import java.util.Timer;

public class MessageProcessor {
    private MongoConnection mongoConnection;
    private  TimerTask timerTask;

    static int counter = 0;

    MessageProcessor(MongoConnection mongoConnection){
        this.mongoConnection = mongoConnection;
    }

    public void runVerification(String info){
        JSONObject object = new JSONObject(info);
        Document document = Translators.translateToMongo(object);
        if (document==null)
            System.out.println("Invalid Payload!");
        else{
            mongoConnection.save(document);
            startTimer();
        }
    }

    private void startTimer(){
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

    private void restartTimer(){
        timerTask.cancel();
        startTimer();
    }

    private boolean isTimeToSave(){
        return counter >= 10;
    }
}

package export;

import org.bson.Document;
import org.json.JSONObject;
import sensores.app.MongoConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;
import java.util.Timer;

public class CronJob {
    private TimerTask timerTask;
    private static int counter = 0;
    private int time_to_export;
    private MongoConnection mongoConnection;

    CronJob() {
        try {
            Properties prop = new Properties();
            InputStream input = new FileInputStream("config.properties");
            // load a properties file
            prop.load(input);

            // get the property value and print it out
            time_to_export = Integer.parseInt(prop.getProperty("time_to_export_info"));

            mongoConnection = new MongoConnection();

            //TODO: Connect to Sybase

            startExport();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startExport() {
        timerTask = new TimerTask() {

            // TODO: Implement Transact
            @Override
            public void run() {
                List<Document> documents = null;
                try {
                    documents = mongoConnection.findAll();
                    if (!documents.isEmpty()) {
                        for (Document document : documents) {
                            System.out.println(document);
                        }
                        //TODO: Save on Sybase
                        mongoConnection.saveBackup(documents);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        Timer timer = new Timer("MyTimer");//create a new Timer

        timer.scheduleAtFixedRate(timerTask, 0, 10000);
    }

}

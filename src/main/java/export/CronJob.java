package export;

import org.bson.Document;
import sensores.app.MongoConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class CronJob {
    private TimerTask timerTask;
    private static int counter = 0;
    private int time_to_export;
    private MongoConnection mongoConnection;

    Connection conn = null;
    CallableStatement stmt = null;

    CronJob() {


        try {
            Properties prop = new Properties();
            InputStream input = new FileInputStream("config.properties");
            // load a properties file
            prop.load(input);

            // get the property value and print it out
            time_to_export = Integer.parseInt(prop.getProperty("time_to_export_info"));

            mongoConnection = new MongoConnection();


            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
        //    Class.forName("sybase.jdbc4.sqlanywhere.IDriver");
        //    conn = DriverManager.getConnection("jdbc4:sqlanywhere:uid=DBA;pwd=sql;");

            startExport();
        } catch (IOException e) {
            e.printStackTrace();
     //   } catch (SQLException e) {
//            e.printStackTrace();
  //      } catch (ClassNotFoundException e) {
    //        e.printStackTrace();
        }

    }

    private void startExport() {
        timerTask = new TimerTask() {

            // TODO: Implement Transact
            @Override
            public void run() {
                List<Document> documents = null;
                try {
                    //STEP 4: Execute a query
                    System.out.println("Creating statement...");
                    String call = "{call InsertSensorAlert(?,?,?,?)}";

                    // https://stackoverflow.com/a/33061599
                    documents = mongoConnection.findAll();
                    if (!documents.isEmpty()) {
                        for (Document document : documents) {
                        	
                        	Double temperatura = Double.parseDouble(document.getString("temperatura"));
                        	Double humidade = Double.parseDouble(document.getString("humidade"));
                        	System.out.println("temperatura : "+ temperatura);
                            System.out.println("humidade: " + humidade);
                            
                        	String stringDate = document.getString("datahora").split(" ")[0];
                        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        	java.sql.Date dataMedicao = (java.sql.Date) dateFormat.parse(stringDate);
                        			
                        	String stringTime = document.getString("datahora").split(" ")[1];
                        	  
                     
                            System.out.println("dataMedicao :" + dataMedicao);
                        	System.out.println("horaMedicao :" + stringTime);
                        	
                            
            /*                stmt = conn.prepareCall(call);
                            stmt.setDouble(1, 23.3);
                            stmt.setDouble(2,55);
                            stmt.setDate(3, new Date(1999-12-02));
                            stmt.setTime(4, new Time(2352));
                            stmt.execute();*/
                        }

                        //  (IN _hum decimal(8,2), IN _temp decimal(8,2), IN _date date, IN _time time)

                        mongoConnection.saveBackup(documents);
                    }
                } catch (
                        Exception e)

                {
                    e.printStackTrace();
                }

            }
        }

        ;

        Timer timer = new Timer("MyTimer");//create a new Timer

        timer.scheduleAtFixedRate(timerTask, 0, 10000);
    }

}

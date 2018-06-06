package export;

import org.bson.Document;
import sensores.app.MongoConnection;
import sensores.app.Translators;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

//import com.sybase.jdbc4.jdbc.*;

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

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:sqlanywhere:uid=DBA;pwd=sql;eng=Java2Android");

			startExport();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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
					// https://stackoverflow.com/a/33061599
					documents = mongoConnection.findAll();

					if (!documents.isEmpty()) {

						System.out.println("Creating statement...");
						String call = "{call InsertSensorAlert(?,?,?,?)}";

						int i = 0;
						Document document = null;
						boolean executed = false;

						while (i != documents.size()) {

							try {
								document = documents.get(i);

								Document translatorObject = Translators.translateToSQL(document);

								Double temperatura = (Double) translatorObject.get("temperatura");
								Double humidade = (Double) translatorObject.get("humidade");

								java.sql.Date javaSqlDate = (java.sql.Date) translatorObject.get("dataMedicao");
								long time = (long) translatorObject.get("horaMedicao");

								stmt = conn.prepareCall(call);
								stmt.setDouble(1, humidade);
								stmt.setDouble(2, temperatura);
								stmt.setDate(3, javaSqlDate);
								stmt.setTime(4, new Time(time));
								stmt.execute();
								executed = true;

								mongoConnection.Delete(document);
								System.out.println("SAVED ON SYBASE: " + document.get("_id"));
								i++;

							} catch (Exception e) {
								e.printStackTrace();
								if (executed) {
									mongoConnection.Delete(document);
									i++;
								}
							}
						}

						mongoConnection.saveBackup(documents);
					}
				} catch (Exception e)

				{
					e.printStackTrace();
				}

			}
		};

		Timer timer = new Timer("MyTimer");// create a new Timer

		timer.scheduleAtFixedRate(timerTask, 0, 10000);
	}

}

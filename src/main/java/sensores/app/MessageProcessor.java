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

	private Double lastValueTemperature = null;
	private Double lastValueTemperatureInserted = null;
	private Double lastValueHumidity = null;
	private Double lastValueHumidityInserted = null;

	MessageProcessor(Properties prop) {
		mongoConnection = new MongoConnection();
		this.prop = prop;

	}

	public void runVerification(String info) {
		try {
			JSONObject object = new JSONObject(info);
			Document document = Translators.translateToMongo(object);
			if (document == null)
				System.out.println("Document cannot be null!");
			else if (isFirstRun) {
				startTimer();
				System.out.println("isValid(document):" + isValid(document));
				if (isValid(document)) {
					mongoConnection.save(document);
					lastValueHumidityInserted = (Double) document.get("humidade");
					lastValueTemperatureInserted = (Double) document.get("temperatura");
					isFirstRun = false;
				}
				
				lastValueHumidity = (Double) document.get("humidade");
				lastValueTemperature = (Double) document.get("temperatura");
			} else if (isTimeToSave()) {
				if (isValid(document)) {
					mongoConnection.save(document);
					lastValueHumidityInserted = (Double) document.get("humidade");
					lastValueTemperatureInserted = (Double) document.get("temperatura");
					startTimer();
				}
				lastValueHumidity = (Double) document.get("humidade");
				lastValueTemperature = (Double) document.get("temperatura");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println();
		}
	}

	private boolean isValid(Document document) {
		return validHumidity(document) && validTemperature(document);
	}

	private boolean validTemperature(Document document) {
		if (lastValueTemperature == null)
			return true;

		if (1.3 * lastValueTemperature >= Double.parseDouble(document.getString("temperature"))
				|| 0.7 * lastValueTemperature <= Double.parseDouble(document.getString("temperature"))) {

			if (lastValueTemperature != lastValueTemperatureInserted) {
				if (1.3 * lastValueTemperatureInserted <= Double.parseDouble(document.getString("temperature"))
						&& 0.7 * lastValueTemperatureInserted >= Double.parseDouble(document.getString("temperature")))
					return true;
			}

			return false;
		}

		return true;
	}

	private boolean validHumidity(Document document) {
		if (lastValueHumidity == null)
			return true;

		if (1.3 * lastValueHumidity >= Double.parseDouble(document.getString("humidity"))
				|| 0.7 * lastValueHumidity <= Double.parseDouble(document.getString("humidity"))) {

			if (lastValueHumidity != lastValueHumidityInserted) {
				if (1.3 * lastValueHumidityInserted <= Double.parseDouble(document.getString("humidity"))
						&& 0.7 * lastValueHumidityInserted >= Double.parseDouble(document.getString("humidity")))
					return true;
			}

			return false;
		}
		return true;
	}

	private void startTimer() {
		if (timerTask != null) {
			timerTask.cancel();
			counter = 0;
		}

		timerTask = new TimerTask() {

			@Override
			public void run() {
				System.out.println("TimerTask executing counter is: " + counter);
				counter++;// increments the counter
			}
		};

		Timer timer = new Timer("MyTimer");// create a new Timer

		timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}

	private boolean isTimeToSave() {
		System.out.println("TIME " + Integer.parseInt(prop.getProperty("time_to_save_info")));
		return counter >= Integer.parseInt(prop.getProperty("time_to_save_info"));
	}
}

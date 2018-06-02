package sensores.app;

import org.bson.Document;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Translators {

	public static Document translateToMongo(JSONObject info) {
		System.out.println(info);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Document document = null;
		try {
			Date parsedDate = dateFormat.parse(info.getString("date") + " " + info.getString("time"));
			Double temperature = Double.parseDouble(info.getString("temperature"));
			Double humidity = Double.parseDouble(info.getString("humidity"));
			document = new Document("datahora", parsedDate)
					.append("temperatura", temperature)
					.append("humidade",humidity);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			document = null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			document = null;
		}
		return document;
	}
}

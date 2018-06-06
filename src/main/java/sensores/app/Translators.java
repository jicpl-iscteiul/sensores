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
			document = new Document("datahora", parsedDate).append("temperatura", temperature).append("humidade",
					humidity);
			System.out.println("document translator :" + document);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			document = null;
		} catch (Exception e) {
			e.printStackTrace();
			document = null;
		}
		return document;
	}

	public static Document translateToSQL(Document document) {
		
		Document doc = null;
		try {

			Double temperatura = (Double) document.get("temperatura");
			Double humidade = (Double) document.get("humidade");

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(dateFormat.format((Date) document.get("datahora")));
			String reportDate = dateFormat.format(date);
			java.sql.Date javaSqlDate = java.sql.Date.valueOf(reportDate);
			
			SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
	        String time = localDateFormat.format((Date) document.get("datahora"));
	        Date answeredTime = localDateFormat.parse(time);
	        long asSecondsSince1970 = answeredTime.getTime();
	        
			doc = new Document("temperatura", temperatura)
					.append("humidade", humidade)
					.append("dataMedicao", javaSqlDate)
					.append("horaMedicao", asSecondsSince1970);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return doc;
	}
}

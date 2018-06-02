package sensores.app;

import org.bson.Document;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Translators {

    public static Document translateToMongo(JSONObject info) {
        // TODO: Add verification to all fields
        System.out.println(info);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Document document = null;
        try {
            Date parsedDate = dateFormat.parse(info.getString("date") + " " + info.getString("time") );

           document = new Document("datahora",parsedDate);
                    /*.append("contact", new Document("phone", "228-555-0149")
                            .append("email", "cafeconleche@example.com")
                            .append("location", Arrays.asList(-73.92502, 40.8279556)))
                    .append("stars", 3)
                    .append("categories", Arrays.asList("Bakery", "Coffee", "Pastries"));*/
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        } catch (Exception e ) {
            System.out.println(e.getMessage());
            document = null;
        }
        return document;
    }
}

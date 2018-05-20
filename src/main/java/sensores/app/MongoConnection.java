package sensores.app;

import org.json.JSONObject;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;

import sensores.app.Translators.*;

public class MongoConnection {

    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection_sensores;


    public MongoConnection() {
        try {
            mongoClient = MongoClients.create("mongodb://appSensores:super_password@ds129560.mlab.com:29560/sensoresdb");
            database = mongoClient.getDatabase("sensoresdb");
            collection_sensores = database.getCollection("sensores_info");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void save(Document document) {
        if (document==null){
            throw new NullPointerException("Document cannot be null");
        }
                collection_sensores.insertOne(document);
        }

}

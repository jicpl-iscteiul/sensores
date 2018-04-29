package sensores.app;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

public class MongoConnection {

	MongoClient mongoClient;
	DB database;
	DBCollection collection;

	public MongoConnection() {
		try {
			mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
			database = mongoClient.getDB("sensores");
			collection = database.getCollection("info");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void save(String json) {
		
		DBObject dbObject = (DBObject)JSON.parse(json);
		
		collection.insert(dbObject);
		
		System.out.println("Inserted: " + json );

	}
}

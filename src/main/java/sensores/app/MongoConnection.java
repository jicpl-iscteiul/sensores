package sensores.app;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoConnection {

	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection_sensores;
	private MongoCollection<Document> collection_backup;

	public MongoConnection() {
		try {
			mongoClient = MongoClients
					.create("mongodb://appSensores:super_password@ds129560.mlab.com:29560/sensoresdb");
			database = mongoClient.getDatabase("sensoresdb");
			collection_sensores = database.getCollection("sensores_info");
			collection_backup = database.getCollection("info_backup");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void save(Document document) {
		if (document == null) {
			throw new NullPointerException("Document cannot be null");
		}
		collection_sensores.insertOne(document);
	}

	public List<Document> findAll() throws Exception {
		List<Document> documents = null;
		try {
			documents = collection_sensores.find().into(new ArrayList<Document>());
		} catch (Exception e) {
			e.getMessage();
			throw new Exception(e.getMessage());
		}
		return documents;
	}

	public void Delete(Document document) throws Exception {
		try {
			collection_sensores.deleteOne(document);

		} catch (Exception e) {
			e.getMessage();
			throw new Exception(e.getMessage());
		}
	}

	public void saveBackup(List<Document> documents) throws Exception {
		try {
			collection_backup.insertMany(documents);
		} catch (Exception e) {
			e.getMessage();
			throw new Exception(e.getMessage());
		}
	}

}

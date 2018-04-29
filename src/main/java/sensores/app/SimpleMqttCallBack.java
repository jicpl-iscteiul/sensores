package sensores.app;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class SimpleMqttCallBack implements MqttCallback {
	MongoConnection mongoConnection;
	
	public SimpleMqttCallBack(MongoConnection mongoConnection) {
		this.mongoConnection = mongoConnection;
	}

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("Message received:\n\t"+ new String(mqttMessage.getPayload()) );
        String json = new String(mqttMessage.getPayload());
        mongoConnection.save(json);
        System.out.println("json: " + json);
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // not used in this example
    }
}
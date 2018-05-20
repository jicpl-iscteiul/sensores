package sensores.app;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class SimpleMqttCallBack implements MqttCallback {
	private MessageProcessor processor;
	
	public SimpleMqttCallBack(MongoConnection mongoConnection) {
		this.processor = new MessageProcessor(mongoConnection);
	}

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) {
        System.out.println("Message received:\n\t"+ new String(mqttMessage.getPayload()) );
        String json = new String(mqttMessage.getPayload());
        processor.runVerification(json);

//        System.out.println("json: " + json);
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // not used in this example
    }
}
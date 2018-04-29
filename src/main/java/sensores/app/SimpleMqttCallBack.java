package sensores.app;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class SimpleMqttCallBack implements MqttCallback {

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("Message received:\n\t"+ new String(mqttMessage.getPayload()) );
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // not used in this example
        String topic        = "iscte_sid_2016_S1";
        //  String content      = "Message from MqttPublishSample";
        //  int qos             = 0;
        String broker       = "tcp://iot.eclipse.org:443";
        String clientId     = "js-utility-Tat9y";
        MemoryPersistence persistence = new MemoryPersistence();
    }
}
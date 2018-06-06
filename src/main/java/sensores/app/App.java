package sensores.app;

import java.io.FileNotFoundException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class App {
	public static void main(String[] args) {
		String topic;
		String broker;
		String clientId;

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");
			// load a properties file
			prop.load(input);

			// get the property value and print it out
			topic = prop.getProperty("topic");
			broker = prop.getProperty("broker");
			clientId = prop.getProperty("clientId");

			MqttClient client = new MqttClient(broker, clientId);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			client.connect();
			client.subscribe(topic);
			boolean connected = client.isConnected();
			System.out.println("Connected: " + connected);
			connOpts.setAutomaticReconnect(true);
			client.setCallback(new SimpleMqttCallBack(prop));
			connected = client.isConnected();

		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
